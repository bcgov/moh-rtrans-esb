package ca.bc.gov.moh.esb.util.filedrop;

import java.util.Properties;


/**
 *
 * @author joshua.burton
 */
public class FileDropConfiguration {

    private final String TRANSACTION_TYPE_ALL = "ALL";
    private final String MESSAGE_CONTEXT_ALL = "ALL";

    boolean isMessageToBeSaved(String transactionType, String messageContext, Properties filedropProperties) {

        Boolean isMessageToBeSaved = Boolean.FALSE;

        if (isEmpty(transactionType)) {
            transactionType = TRANSACTION_TYPE_ALL;
        }

        if (isEmpty(messageContext)) {
            messageContext = MESSAGE_CONTEXT_ALL;
        }

        String propertyKey = "filedrop." + transactionType + "." + messageContext;
        String propertyValue = (String) filedropProperties.get(propertyKey);

        if (!isEmpty(propertyValue)) {
            isMessageToBeSaved = Boolean.valueOf(propertyValue);
            return isMessageToBeSaved;
        }

        // Check the general case for the transaction type
        propertyKey = "filedrop." + transactionType + "." + MESSAGE_CONTEXT_ALL;
        propertyValue = (String) filedropProperties.get(propertyKey);
        if (!isEmpty(propertyValue)) {
            isMessageToBeSaved = Boolean.valueOf(propertyValue);
            return isMessageToBeSaved;
        }

        // Check the general case for the message context
        propertyKey = "filedrop." + TRANSACTION_TYPE_ALL + "." + messageContext;
        propertyValue = (String) filedropProperties.get(propertyKey);
        if (!isEmpty(propertyValue)) {
            isMessageToBeSaved = Boolean.valueOf(propertyValue);
            return isMessageToBeSaved;
        }

        // Check the catch-all case
        propertyKey = "filedrop." + TRANSACTION_TYPE_ALL + "." + MESSAGE_CONTEXT_ALL;
        propertyValue = (String) filedropProperties.get(propertyKey);
        if (!isEmpty(propertyValue)) {
            isMessageToBeSaved = Boolean.valueOf(propertyValue);
            return isMessageToBeSaved;
        }

        return isMessageToBeSaved;
    }

    private static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }
}
