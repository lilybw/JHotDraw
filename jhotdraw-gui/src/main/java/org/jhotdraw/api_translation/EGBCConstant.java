package org.jhotdraw.api_translation;

/**
 * Providing additional reliability to the previous GridBagConstraints-constants.
 * Use this Enum over GridBagConstraints' constants whenever possible, and use its translation capabilities whenever needed.
 *
 * @author GustavBW
 */
public enum EGBCConstant {
    INVALID(Integer.MIN_VALUE),
    /**
     * Specifies that this component is the next-to-last component in its column or row (gridwidth, gridheight), or that this component be placed next to the previously added component (gridx, gridy).
     */
    RELATIVE(-1),
    /**
     * Specifies that this component is the last component in its column or row.
     */
    REMAINDER(0),
    /**
     * Do not resize the component.
     */
    NONE(0),
    /**
     * Resize the component both horizontally and vertically.
     */
    BOTH(1),
    /**
     * Resize the component horizontally but not vertically.
     */
    HORIZONTAL(2),
    /**
     * Resize the component vertically but not horizontally.
     */
    VERTICAL(3),
    /**
     * Put the component in the center of its display area.
     */
    CENTER(10),
    /**
     * Put the component at the top of its display area, centered horizontally.
     */
    NORTH(11),
    /**
     * Put the component at the top-right corner of its display area.
     */
    NORTHEAST(12),
    /**
     * Put the component on the right side of its display area, centered vertically.
     */
    EAST(13),
    /**
     * Put the component at the bottom-right corner of its display area.
     */
    SOUTHEAST(14),
    /**
     * Put the component at the bottom of its display area, centered horizontally.
     */
    SOUTH(15),
    /**
     * Put the component at the bottom-left corner of its display area.
     */
    SOUTHWEST(16),
    /**
     * Put the component on the left side of its display area, centered vertically.
     */
    WEST(17),
    /**
     * Put the component at the top-left corner of its display area.
     */
    NORTHWEST(18),
    /**
     * Place the component centered along the edge of its display area associated with the start of a page for the current ComponentOrientation. Equal to NORTH for horizontal orientations.
     */
    PAGE_START(19),
    /**
     * Place the component centered along the edge of its display area associated with the end of a page for the current ComponentOrientation. Equal to SOUTH for horizontal orientations.
     */
    PAGE_END(20),
    /**
     * Place the component centered along the edge of its display area where lines of text would normally begin for the current ComponentOrientation. Equal to WEST for horizontal, left-to-right orientations and EAST for horizontal, right-to-left orientations.
     */
    LINE_START(21),
    /**
     * Place the component centered along the edge of its display area where lines of text would normally end for the current ComponentOrientation. Equal to EAST for horizontal, left-to-right orientations and WEST for horizontal, right-to-left orientations.
     */
    LINE_END(22),
    /**
     * Place the component in the corner of its display area where the first line of text on a page would normally begin for the current ComponentOrientation. Equal to NORTHWEST for horizontal, left-to-right orientations and NORTHEAST for horizontal, right-to-left orientations.
     */
    FIRST_LINE_START(23),
    /**
     * Place the component in the corner of its display area where the first line of text on a page would normally end for the current ComponentOrientation. Equal to NORTHEAST for horizontal, left-to-right orientations and NORTHWEST for horizontal, right-to-left orientations.
     */
    FIRST_LINE_END(24),
    /**
     * Place the component in the corner of its display area where the last line of text on a page would normally start for the current ComponentOrientation. Equal to SOUTHWEST for horizontal, left-to-right orientations and SOUTHEAST for horizontal, right-to-left orientations.
     */
    LAST_LINE_START(25),
    /**
     * Place the component in the corner of its display area where the last line of text on a page would normally end for the current ComponentOrientation. Equal to SOUTHEAST for horizontal, left-to-right orientations and SOUTHWEST for horizontal, right-to-left orientations.
     */
    LAST_LINE_END(26),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally centered and vertically aligned along the baseline of the prevailing row. If the component does not have a baseline it will be vertically centered.
     */
    BASELINE(0x100),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally placed along the leading edge. For components with a left-to-right orientation, the leading edge is the left edge. Vertically the component is aligned along the baseline of the prevailing row. If the component does not have a baseline it will be vertically centered.
     */
    BASELINE_LEADING(0x200),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally placed along the trailing edge. For components with a left-to-right orientation, the trailing edge is the right edge. Vertically the component is aligned along the baseline of the prevailing row. If the component does not have a baseline it will be vertically centered.
     */
    BASELINE_TRAILING(0x300),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally centered. Vertically the component is positioned so that its bottom edge touches the baseline of the starting row. If the starting row does not have a baseline it will be vertically centered.
     */
    ABOVE_BASELINE(0x400),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally placed along the leading edge. For components with a left-to-right orientation, the leading edge is the left edge. Vertically the component is positioned so that its bottom edge touches the baseline of the starting row. If the starting row does not have a baseline it will be vertically centered.
     */
    ABOVE_BASELINE_LEADING(0x500),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally placed along the trailing edge. For components with a left-to-right orientation, the trailing edge is the right edge. Vertically the component is positioned so that its bottom edge touches the baseline of the starting row. If the starting row does not have a baseline it will be vertically centered.
     */
    ABOVE_BASELINE_TRAILING(0x600),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally centered. Vertically the component is positioned so that its top edge touches the baseline of the starting row. If the starting row does not have a baseline it will be vertically centered.
     */
    BELOW_BASELINE(0x700),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally placed along the leading edge. For components with a left-to-right orientation, the leading edge is the left edge. Vertically the component is positioned so that its top edge touches the baseline of the starting row. If the starting row does not have a baseline it will be vertically centered.
     */
    BELOW_BASELINE_LEADING(0x800),
    /**
     * Possible value for the anchor field. Specifies that the component should be horizontally placed along the trailing edge. For components with a left-to-right orientation, the trailing edge is the right edge. Vertically the component is positioned so that its top edge touches the baseline of the starting row. If the starting row does not have a baseline it will be vertically centered.
     */
    BELOW_BASELINE_TRAILING(0x900);

    /**
     * Returns the first matching EGBCConstant.
     * Note that "REMAINDER" and "NONE" have the same integer values in the original, making it programmatically impossible to distinguish between the two.
     * Additionally, EGBCConstant is exhaustive, including an "INVALID" option.
     * @return the corresponding EGBCConstant or EGBCConstant.INVALID.
     */
    public static EGBCConstant valueOf(int val){
        for (EGBCConstant c : EGBCConstant.values()){
            if (c.intVal == val){
                return c;
            }
        }
        return EGBCConstant.INVALID;
    }

    /**
     * The respective integer value used by the original GridBagConstraints class.
     */
    public final int intVal;
    EGBCConstant(int intVal){
        this.intVal = intVal;
    }

}
