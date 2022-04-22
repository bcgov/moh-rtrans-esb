/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidates;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.QueryEntityToJaxbTypeConverter;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.camel.Converter;
import org.hl7.v3.AD;
import org.hl7.v3.CV;
import org.hl7.v3.HCIMINFindCandidates;
import org.hl7.v3.PN;
import org.hl7.v3.QUPAMT101103BCPersonAddr;
import org.hl7.v3.QUPAMT101103BCPersonAdministrativeGender;
import org.hl7.v3.QUPAMT101103BCPersonBirthTime;
import org.hl7.v3.QUPAMT101103BCPersonDeceasedTime;
import org.hl7.v3.QUPAMT101103BCPersonName;
import org.hl7.v3.QUPAMT101103BCQueryByParameterPayload;
import org.hl7.v3.TS;

@Converter
public class FindCandidatesEntityToJaxbTypeConverter extends QueryEntityToJaxbTypeConverter {

    @Converter
    public static HCIMINFindCandidates convert(FindCandidates request) {

        HCIMINFindCandidates jaxb = new HCIMINFindCandidates();

        convertCommonFields(jaxb, request);

        setGender(request, jaxb);
        setBirthDate(request, jaxb);
        setDeathDate(request, jaxb);
        setName(request, jaxb);
        setAddress(request, jaxb);

        return jaxb;
    }

    private static void setGender(FindCandidates request, HCIMINFindCandidates jaxb) {

        if (request.getPerson().getGender() == null) {
            jaxb.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload().setPersonAdministrativeGender(null);
            return;
        }

        QUPAMT101103BCPersonAdministrativeGender personAdministrativeGender = objectFactory.createQUPAMT101103BCPersonAdministrativeGender();

        CV gender = convertGenderAttributeToCV(request.getPerson().getGender());
        personAdministrativeGender.setValue(gender);

        JAXBElement<QUPAMT101103BCPersonAdministrativeGender> personAdministrativeGenderElement = objectFactory.createQUPAMT101103BCQueryByParameterPayloadPersonAdministrativeGender(personAdministrativeGender);

        jaxb.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload().setPersonAdministrativeGender(personAdministrativeGenderElement);
    }

    private static void setBirthDate(FindCandidates request, HCIMINFindCandidates jaxb) {
        if (request == null || request.getPerson() == null || request.getPerson().getBirthDate() == null) {
            return;
        }

        TS ts = convertDateAttributeToTS(request.getPerson().getBirthDate());

        if (ts != null) {
            final QUPAMT101103BCQueryByParameterPayload queryByParameterPayload = jaxb.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload();
            QUPAMT101103BCPersonBirthTime personBirthTime = objectFactory.createQUPAMT101103BCPersonBirthTime();
            JAXBElement<QUPAMT101103BCPersonBirthTime> personBirthTimeElement = objectFactory.createQUPAMT101103BCQueryByParameterPayloadPersonBirthTime(personBirthTime);
            queryByParameterPayload.setPersonBirthTime(personBirthTimeElement);
            personBirthTime.setValue(ts);
        }
    }

    private static void setDeathDate(FindCandidates request, HCIMINFindCandidates jaxb) {
        if (request == null || request.getPerson() == null || request.getPerson().getDeathDate() == null) {
            return;
        }

        TS ts = convertDateAttributeToTS(request.getPerson().getDeathDate());

        if (ts != null) {
            final QUPAMT101103BCQueryByParameterPayload queryByParameterPayload = jaxb.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload();
            QUPAMT101103BCPersonDeceasedTime personDeathTime = objectFactory.createQUPAMT101103BCPersonDeceasedTime();
            JAXBElement<QUPAMT101103BCPersonDeceasedTime> personDeathTimeElement = objectFactory.createQUPAMT101103BCQueryByParameterPayloadPersonDeceasedTime(personDeathTime);
            queryByParameterPayload.setPersonDeceasedTime(personDeathTimeElement);
            personDeathTime.setValue(ts);
        }
    }

    private static void setName(FindCandidates request, HCIMINFindCandidates jaxb) {
        if (request == null || request.getPerson() == null) {
            return;
        }
        List<PersonNameAttribute> nameAttributeList = request.getPerson().getName();

        if (nameAttributeList == null || nameAttributeList.isEmpty()) {
            return;
        }

        final QUPAMT101103BCQueryByParameterPayload queryByParameterPayload = jaxb.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload();
        QUPAMT101103BCPersonName personName = objectFactory.createQUPAMT101103BCPersonName();
        JAXBElement<QUPAMT101103BCPersonName> personNameElement = objectFactory.createQUPAMT101103BCQueryByParameterPayloadPersonName(personName);
        queryByParameterPayload.setPersonName(personNameElement);

        PersonNameAttribute nameAttribute = nameAttributeList.get(0);
        PN pn = convertNameAttributeToPN(nameAttribute);
        personName.setValue(pn);

    }

    private static void setAddress(FindCandidates request, HCIMINFindCandidates jaxb) {
        if (request == null || request.getPerson() == null) {
            return;
        }
        List<AddressAttribute> addressAttributeList = request.getPerson().getAddress();

        if (addressAttributeList == null || addressAttributeList.isEmpty()) {
            return;
        }

        AddressAttribute addressAttribute = addressAttributeList.get(0);

        final QUPAMT101103BCQueryByParameterPayload queryByParameterPayload = jaxb.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload();
        QUPAMT101103BCPersonAddr personAddress = objectFactory.createQUPAMT101103BCPersonAddr();
        JAXBElement<QUPAMT101103BCPersonAddr> personAddressElement = objectFactory.createQUPAMT101103BCQueryByParameterPayloadPersonAddr(personAddress);
        queryByParameterPayload.setPersonAddr(personAddressElement);

        AD addressValue = convertAddressAttributeToAD(addressAttribute);
        personAddress.setValue(addressValue);

    }

}
