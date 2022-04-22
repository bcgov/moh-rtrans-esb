/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.datatype.XTN;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Hebron Watson <hebron.watson at cgi.com>
 */
public class DataTypeValidator {

    public static boolean addressAttributeNotEmpty(AddressAttribute addr) {
        if (addr == null) {
            return false;
        }

        if (StringUtils.isNotBlank(addr.getCity())
                || StringUtils.isNotBlank(addr.getCountry())
                || StringUtils.isNotBlank(addr.getPostalCode())
                || StringUtils.isNotBlank(addr.getProvince())) {
            return true;
        }

        List<String> addressLines = addr.getStreetAddressLines();
        for (String sal : addressLines) {
            if (StringUtils.isNotBlank(sal)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Will check that the provided phone is valid: Must at least have the
     * areaCode, must only contain numbers and must be of type "PH"
     *
     * @param phoneElement
     * @return true if the Phone is valid, false if the phoneElement is null.
     * @throws HL7Exception if the phone is invalid
     */
    public static boolean validatePhoneNumber(XTN phoneElement) throws HL7Exception {
        boolean valid = true;
        if (phoneElement != null) {
            String areaCode = phoneElement.getAreaCityCode().getValue();
            String phoneNumber = phoneElement.getPhoneNumber().getValue();
            String telType = phoneElement.getTelecommunicationEquipmentType().getValue();
            // No phone numbers without area code and no letters allowed
            if (phoneNumber != null) {

                if (telType == null) {
                    throw new HL7Exception("Telecommunication Equipment Type is Missing.");
                } else if (!telType.equals("PH")) {
                    throw new HL7Exception("Invalid Telecommunication Equipment Type");
                }

                if (areaCode == null) {
                    throw new HL7Exception("Area Code Missing");
                }

                for (char c : phoneNumber.toCharArray()) {
                    if (!(c > 47 && c < 58)) {
                        throw new HL7Exception("Invalid Phone Number");
                    }
                }
            }
        } else {
            valid = false;
        }

        return valid;
    }

    /**
     * Will check that the given countryCode is valid:
     * Must be contained in the list of known codes
     * @param countryCode the countryCode to check
     * @return true if valid, false otherwise
     */
    public static boolean validateCountryCode(String countryCode) {
        Boolean countryCodeIsValid;

        if (countryCode != null && countryCode.length() == 3) {
            String[] codes = {
                "AFG", "ALA", "ALB", "DZA", "ASM", "AND", "AGO", "AIA", "ATA", "ATG", "ARG", "ARM", "ABW", "AUS",
                "AUT", "AZE", "BHS", "BHR", "BGD", "BRB", "BLR", "BEL", "BLZ", "BEN", "BMU", "BTN", "BOL", "BES",
                "BIH", "BWA", "BVT", "BRA", "IOT", "BRN", "BGR", "BFA", "BDI", "CPV", "KHM", "CMR", "CAN", "CYM",
                "CAF", "TCD", "CHL", "CHN", "CXR", "CCK", "COL", "COM", "COG", "COD", "COK", "CRI", "CIV", "HRV",
                "CUB", "CUW", "CYP", "CZE", "DNK", "DJI", "DMA", "DOM", "ECU", "EGY", "SLV", "GNQ", "ERI", "EST",
                "ETH", "FLK", "FRO", "FJI", "FIN", "FRA", "GUF", "PYF", "ATF", "GAB", "GMB", "GEO", "DEU", "GHA",
                "GIB", "GRC", "GRL", "GRD", "GLP", "GUM", "GTM", "GGY", "GIN", "GNB", "GUY", "HTI", "HMD", "VAT",
                "HND", "HKG", "HUN", "ISL", "IND", "IDN", "IRN", "IRQ", "IRL", "IMN", "ISR", "ITA", "JAM", "JPN",
                "JEY", "JOR", "KAZ", "KEN", "KIR", "PRK", "KOR", "KWT", "KGZ", "LAO", "LVA", "LBN", "LSO", "LBR",
                "LBY", "LIE", "LTU", "LUX", "MAC", "MKD", "MDG", "MWI", "MYS", "MDV", "MLI", "MLT", "MHL", "MTQ",
                "MRT", "MUS", "MYT", "MEX", "FSM", "MDA", "MCO", "MNG", "MNE", "MSR", "MAR", "MOZ", "MMR", "NAM",
                "NRU", "NPL", "NLD", "NCL", "NZL", "NIC", "NER", "NGA", "NIU", "NFK", "MNP", "NOR", "OMN", "PAK",
                "PLW", "PSE", "PAN", "PNG", "PRY", "PER", "PHL", "PCN", "POL", "PRT", "PRI", "QAT", "REU", "ROU",
                "RUS", "RWA", "BLM", "SHN", "KNA", "LCA", "MAF", "SPM", "VCT", "WSM", "SMR", "STP", "SAU", "SEN",
                "SRB", "SYC", "SLE", "SGP", "SXM", "SVK", "SVN", "SLB", "SOM", "ZAF", "SGS", "SSD", "ESP", "LKA",
                "SDN", "SUR", "SJM", "SWZ", "SWE", "CHE", "SYR", "TWN", "TJK", "TZA", "THA", "TLS", "TGO", "TKL",
                "TON", "TTO", "TUN", "TUR", "TKM", "TCA", "TUV", "UGA", "UKR", "ARE", "GBR", "USA", "UMI", "URY",
                "UZB", "VUT", "VEN", "VNM", "VGB", "VIR", "WLF", "ESH", "YEM", "ZMB", "ZWE"
            };
            
            List<String> countryCodeList = Arrays.asList(codes);
            countryCodeIsValid = countryCodeList.contains(countryCode);
            
        } else {
            countryCodeIsValid = false;
        }
        
        return countryCodeIsValid;
    }

}
