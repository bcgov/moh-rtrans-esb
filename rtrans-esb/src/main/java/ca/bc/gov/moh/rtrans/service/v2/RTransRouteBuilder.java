package ca.bc.gov.moh.rtrans.service.v2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Registry;
import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;
import org.slf4j.LoggerFactory;

import ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessor;
import ca.bc.gov.moh.rtrans.service.audit.RTransFileDropProcessor;

/**
 *
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public abstract class RTransRouteBuilder extends RouteBuilder {

    public static final String R09 = "R09";
    public static final String R07 = "R07";
    public static final String R03 = "R03";
    public static final String RECEIVE = "RECEIVE";
    public static final String RESPOND = "RESPOND";
    public static final String START = "START";
    public static final String ESB_IN = "ESB_IN";
    public static final String ESB_OUT = "ESB_OUT";
    public static final String HCIM_IN = "HCIM_IN";
    public static final String HCIM_OUT = "HCIM_OUT";
    public static final String PROCESS_RESPONSE = "PROCESS_RESPONSE";
    public static final String COMPLETE = "COMPLETE";
    public static final String ERROR = "ERROR";
    public static final String INFO = "INFO";

    // Processor used to audit messages.
    protected static Processor AUDIT_PROCESSOR = new RTransAuditProcessor();

    // Processor used by routes to filedrop messages.
    protected static Processor FILEDROP_RECEIVE = new RTransFileDropProcessor(RECEIVE);
    protected static Processor FILEDROP_HCIM_IN = new RTransFileDropProcessor(HCIM_IN);
    protected static Processor FILEDROP_HCIM_OUT = new RTransFileDropProcessor(HCIM_OUT);
    protected static Processor FILEDROP_ESB_IN = new RTransFileDropProcessor(ESB_IN);
    protected static Processor FILEDROP_ESB_OUT = new RTransFileDropProcessor(ESB_OUT);
    protected static Processor FILEDROP_RESPOND = new RTransFileDropProcessor(RESPOND);
    protected static Processor FILEDROP_ERROR = new RTransFileDropProcessor(ERROR);

    // Application properties
    protected static Properties appProperties;

    @PropertyInject("cert.keystore")
    private String keystore;
    @PropertyInject("cert.keystorepass")
    private String keystorepass;
    @PropertyInject("cert.keystoremanagerpass")
    private String keystoremanagerpass;
    @PropertyInject("cert.truststore")
    private String truststore;
    @PropertyInject("cert.truststorepass")
    private String truststorepass;
    
    private static final String KEY_STORE_TYPE_PKCS12 = "PKCS12";
    private static final String TRUST_STORE_TYPE_JKS = "jks";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RTransRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        setupSSLContext(getContext());
        
        onException(Throwable.class)
                .logStackTrace(true)
                .logHandled(true)
                .handled(true)
                .to("direct:errorResponse");
    }

    private void setupSSLContext(CamelContext camelContext) throws CertificateException, FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException {
        
        // Setup key
        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setResource(keystore);
        ksp.setPassword(keystorepass);
        ksp.setType(KEY_STORE_TYPE_PKCS12);

        KeyManagersParameters kmp = new KeyManagersParameters();
        kmp.setKeyStore(ksp);
        kmp.setKeyPassword(keystoremanagerpass);
        
        // Setup trust
        KeyStoreParameters tsp = new KeyStoreParameters();
        tsp.setResource(truststore);
        tsp.setPassword(truststorepass);
        tsp.setType(TRUST_STORE_TYPE_JKS);

        KeyStore trustStore;
        logger.info("Starting to load trust store from file");

        // Read from classpath for local environment
        InputStream trustjks = this.getClass().getClassLoader().getResourceAsStream(truststore);
        // Read from external location for servers
        if(trustjks==null){
            trustjks = new FileInputStream(new File(truststore));
        }   
        logger.info("Size of input stream: "+trustjks.available());
        
        trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        logger.info("Got the instance and going to load with pass: "+truststorepass);
        trustStore.load(trustjks, truststorepass.toCharArray());
        logger.info("Going to init manager factory");
        
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        
        TrustManager trustManager = tmf.getTrustManagers()[0];
        TrustManagersParameters tmp = new TrustManagersParameters();
        tmp.setKeyStore(tsp);
        tmp.setTrustManager(trustManager);
        logger.info("Setting trust manager complete");
        
        // Assign trust and key to sslContext
        SSLContextParameters sslContextParameters = new SSLContextParameters();
        sslContextParameters.setKeyManagers(kmp);
        sslContextParameters.setTrustManagers(tmp);

        Registry registry = camelContext.getRegistry();
        registry.bind("sslParameters", sslContextParameters);
    }

    /**
     * Sets the application properties. Used by InboundRouteBuilderTest to
     * inject dummy properties.
     *
     * @param properties Properties
     */
    public static void setAppProperties(Properties properties) {
        appProperties = properties;
    }
}
