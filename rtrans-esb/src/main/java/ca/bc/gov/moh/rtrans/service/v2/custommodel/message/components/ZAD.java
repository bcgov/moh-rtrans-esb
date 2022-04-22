/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message.components;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.AbstractComposite;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.v24.datatype.ST;

/**
 *
 * @author Trevor.Schiavone
 */
@SuppressWarnings("unused")
public class ZAD extends AbstractComposite {
    
    private Type[] data;
    
    public ZAD(Message message) {
        super(message);
        init();
    }
    
    private void init() {
        data = new Type[35];
        for (int i = 0; i < data.length; i++) {
            data[i] = new ST(getMessage());
        }
    }
    /**
     * Returns an array containing the data elements.
     * @return 
     */
    public Type[] getComponents() { 
        return this.data; 
    }
    
    /**
     * Returns an individual data component.
     *
     * @param number The component number (0-indexed)
     * @return 
     * @throws DataTypeException if the given element number is out of range.
     */
    @Override
    public Type getComponent(int number) throws DataTypeException { 

        try { 
            return this.data[number]; 
        } catch (ArrayIndexOutOfBoundsException e) { 
            throw new DataTypeException("Element " + number + " doesn't exist (Type " + getClass().getName() + " has only " + this.data.length + " components)"); 
        } 
    } 
    
    public ST getZAD1_AddressLine1() {
        return getTyped(0, ST.class);
    }
    public ST getZAD2_AddressLine2() {
        return getTyped(1, ST.class);
    }
    public ST getZAD3_AddressLine3() {
        return getTyped(2, ST.class);
    }
    public ST getZAD4_AddressLine4() {
        return getTyped(3, ST.class);
    }
    public ST getZAD5_AddressLine5() {
        return getTyped(4, ST.class);
    }
    public ST getZAD6_AddressLine6() {
        return getTyped(5, ST.class);
    }
    public ST getZAD7_StreetNumber() {
        return getTyped(6, ST.class);
    }
    public ST getZAD8_StreetNumberSuffix() {
        return getTyped(7, ST.class);
    }
    public ST getZAD9_StreetName() {
        return getTyped(8, ST.class);
    }
    public ST getZAD10_StreetType() {
        return getTyped(9, ST.class);
    }
    public ST getZAD11_StreetDirection() {
        return getTyped(10, ST.class);
    }
    public ST getZAD12_UnitIdentifier() {
        return getTyped(11, ST.class);
    }
    public ST getZAD13_UnitDesignator() {
        return getTyped(12, ST.class);
    }
    public ST getZAD14_DeliveryInstallationArea() {
        return getTyped(13, ST.class);
    }
    public ST getZAD15_DeliveryInstallationType() {
        return getTyped(14, ST.class);
    }
    public ST getZAD16_DeliveryInstallationQualifier() {
        return getTyped(15, ST.class);
    }
    public ST getZAD17_ModeOfDeliveryDesignator() {
        return getTyped(16, ST.class);
    }
    public ST getZAD18_ModeOfDeliveryId() {
        return getTyped(17, ST.class);
    }
    public ST getZAD19_PhysicalDescription() {
        return getTyped(18, ST.class);
    }
    public ST getZAD20_City() {
        return getTyped(19, ST.class);
    }
    public ST getZAD21_Province() {
        return getTyped(20, ST.class);
    }
    public ST getZAD22_PostalCode() {
        return getTyped(21, ST.class);
    }
    public ST getZAD23_Country() {
        return getTyped(22, ST.class);
    }
    public ST getZAD24_AddressType() {
        return getTyped(23, ST.class);
    }
    public ST getZAD25_OtherGeographicRegion() {
        return getTyped(24, ST.class);
    }
    public ST getZAD26_CountyOrParish() {
        return getTyped(25, ST.class);
    }
    public ST getZAD27_CensusTract() {
        return getTyped(26, ST.class);
    }
    public ST getZAD28_ValidAddressIndicator() {
        return getTyped(27, ST.class);
    }
    public ST getZAD29_ValidationDate() {
        return getTyped(28, ST.class);
    }
    public ST getZAD30_ValidForResidenceIndicator() {
        return getTyped(29, ST.class);
    }
    public ST getZAD31_ValidForResidenceDate() {
        return getTyped(30, ST.class);
    }
    public ST getZAD32_ValidForResidenceCategory() {
        return getTyped(31, ST.class);
    }
    public ST getZAD33_ValidForMailingIndicator() {
        return getTyped(32, ST.class);
    }
    public ST getZAD34_ValidPhysicalAddressIndicator() {
        return getTyped(33, ST.class);
    }
    public ST getZAD35_AddressValidationBestGuessIndicator() {
        return getTyped(34, ST.class);
    }
}
