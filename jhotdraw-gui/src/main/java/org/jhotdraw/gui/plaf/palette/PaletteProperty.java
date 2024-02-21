package org.jhotdraw.gui.plaf.palette;

public enum PaletteProperty {

    INVALID("invalid"),
    /**
     * Rollover button implementation
     */
    IS_ROLLOVER("JToolBar.isRollover"),
    IS_DIVIDER_DRAWN("Palette.ToolBar.isDividerDrawn"),
    /**
     * The value of this client property must be an Icon or null.
     */
    TOOLBAR_ICON_PROPERTY("Palette.ToolBar.icon"),
    /**
     * The value of this client property must be an Integer or null, if it is null, the value 2 is used.
     */
    TOOLBAR_TEXT_ICON_GAP_PROPERTY("Palette.ToolBar.textIconGap"),
    /**
     * The value of this client property must be an Insets object or null, if it is null, the insets of the toolbar border are used
     */
    TOOLBAR_INSETS_OVERRIDE_PROPERTY("Palette.ToolBar.insetsOverride"),
    FOCUSED_COMP_INDEX("JToolBar.focusedCompIndex");


    public static PaletteProperty strValueOf(String string){
        for (PaletteProperty property : values()){
            if (string.equals(property.strVal)){
                return property;
            }
        }
        return INVALID;
    }

    public final String strVal;

    PaletteProperty(String strVal){
        this.strVal = strVal;
    }

}
