
package org.hl7.v3;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MCCI_AR200002_Service", targetNamespace = "urn:hl7-org:v3", wsdlLocation = "file:/C:/Apps/HSA/HSA-batch/src/main/resources/ca/gov/moh/hsa/esb/service/batch/MCCI_AR200002.wsdl")
public class MCCIAR200002Service
    extends Service
{

    private final static URL MCCIAR200002SERVICE_WSDL_LOCATION;
    private final static WebServiceException MCCIAR200002SERVICE_EXCEPTION;
    private final static QName MCCIAR200002SERVICE_QNAME = new QName("urn:hl7-org:v3", "MCCI_AR200002_Service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Apps/HSA/HSA-batch/src/main/resources/ca/gov/moh/hsa/esb/service/batch/MCCI_AR200002.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MCCIAR200002SERVICE_WSDL_LOCATION = url;
        MCCIAR200002SERVICE_EXCEPTION = e;
    }

    public MCCIAR200002Service() {
        super(__getWsdlLocation(), MCCIAR200002SERVICE_QNAME);
    }

    public MCCIAR200002Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), MCCIAR200002SERVICE_QNAME, features);
    }

    public MCCIAR200002Service(URL wsdlLocation) {
        super(wsdlLocation, MCCIAR200002SERVICE_QNAME);
    }

    public MCCIAR200002Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MCCIAR200002SERVICE_QNAME, features);
    }

    public MCCIAR200002Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MCCIAR200002Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MCCIAR200002PortType
     */
    @WebEndpoint(name = "MCCI_AR200002_Port")
    public MCCIAR200002PortType getMCCIAR200002Port() {
        return super.getPort(new QName("urn:hl7-org:v3", "MCCI_AR200002_Port"), MCCIAR200002PortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MCCIAR200002PortType
     */
    @WebEndpoint(name = "MCCI_AR200002_Port")
    public MCCIAR200002PortType getMCCIAR200002Port(WebServiceFeature... features) {
        return super.getPort(new QName("urn:hl7-org:v3", "MCCI_AR200002_Port"), MCCIAR200002PortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MCCIAR200002SERVICE_EXCEPTION!= null) {
            throw MCCIAR200002SERVICE_EXCEPTION;
        }
        return MCCIAR200002SERVICE_WSDL_LOCATION;
    }

}
