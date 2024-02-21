package org.jhotdraw.gui;

public enum JDisclosureProperty {

    INVALID("invalid"),
    DISCLOSURE_STATE("disclosureState"),
    DISCLOSURE_STATE_COUNT("disclosureStateCount");

    /**
     * Method mapping from the original string value of each property.
     * Not to be confused with JDisclosureProperty.valueOf(String) as provided by default.
     * @return the corresponding property or INVALID.
     */
    public static JDisclosureProperty strValueOf(String string){
        for (JDisclosureProperty property : values()){
            if (property.strVal.equals(string)){
                return  property;
            }
        }
        return INVALID;
    }

    public final String strVal;
    JDisclosureProperty(String strVal){
        this.strVal = strVal;
    }

}
