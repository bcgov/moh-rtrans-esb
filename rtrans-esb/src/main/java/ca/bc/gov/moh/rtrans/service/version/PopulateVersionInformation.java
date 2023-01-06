package ca.bc.gov.moh.rtrans.service.version;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.bc.gov.moh.rtrans.RTransMainMethod;
import net.minidev.json.JSONObject;

public class PopulateVersionInformation implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(PopulateVersionInformation.class);
	private static final String IMPLEMENTATION_VERSION_KEY = "Implementation-Version";
	private static final String VERSION_INFORMATION = getVersionInformation().toJSONString();

	public static JSONObject getVersionInformation() {
		Package pck = RTransMainMethod.class.getPackage();
		final String methodName = getMethodName();

		// init a JSON object
		JSONObject v2JsonObj = new JSONObject();
		v2JsonObj.put(IMPLEMENTATION_VERSION_KEY, pck.getImplementationVersion());
		String jsonString = v2JsonObj.toJSONString();
		logger.debug("{} - The JSON Message is: {}", methodName, jsonString);
		// return the JSON object
		return v2JsonObj;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(VERSION_INFORMATION);
	}

	private static String getMethodName() {
		StackWalker walker = StackWalker.getInstance();
		Optional<String> methodName = walker.walk(frames -> frames.limit(2).skip(1) // to get name of caller
				.findFirst().map(StackWalker.StackFrame::getMethodName));
		return methodName.orElse("Method name Unknnown");
	}

}
