package ca.bc.gov.moh.rtrans.entity;

import ca.bc.gov.moh.esb.util.audit.AuditableResponseMessage;
import java.io.Serializable;

/**
 * Information about a system responding to a request. This may contain
 * exception information.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class SystemResponse implements Serializable, AuditableResponseMessage {

    private static final long serialVersionUID = 7526472295622776147L;

    /**
     * system response code value, ie BCHCIM.RP.111111
     */
    private String code;
    private String codeText;
    private String text;
    /**
     * type of system response.
     */
    private ResponseTypes type;

    public SystemResponse() {

    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the codeText
     */
    public String getCodeText() {
        return codeText;
    }

    /**
     * @param codeText the codeText to set
     */
    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the type
     */
    public ResponseTypes getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ResponseTypes type) {
        this.type = type;
    }

    @Override
    public String getResponseCode() {
        return getCode();
    }

    @Override
    public String getResponseText() {
        return getCodeText();
    }
}//end SystemResponse
