package org.jhotdraw.api_translation;

import javax.swing.*;

/**
 * Providing additional reliability to the previous SwingConstants.
 * Use this Enum over SwingConstants whenever possible, and use its translation capabilities whenever needed.
 *
 * @author GustavBW
 */
public enum ESwingConstant {
    INVALID(-1),

    //Positions

    CENTER(0),
    TOP(1),
    LEFT(2),
    BOTTOM(3),
    RIGHT(4),

    //Compass directions

    NORTH(1),
    NORTH_EAST(2),
    EAST(3),
    SOUTH_EAST(4),
    SOUTH(5),
    SOUTH_WEST(6),
    WEST(7),
    NORTH_WEST(8),

    //Orientations

    HORIZONTAL(0),
    VERTICAL(1),

    //Text direction
    /**
     * Identifies the leading edge of text for use with left-to-right and right-to-left languages. Used by buttons and labels.
     */
    LEADING(10),
    /**
     * Identifies the trailing edge of text for use with left-to-right and right-to-left languages. Used by buttons and labels.
     */
    TRAILING(11),
    /**
     * Identifies the next direction in a sequence.
     */
    NEXT(12),
    /**
     * Identifies the previous direction in a sequence.
     */
    PREVIOUS(13);

    /**
     * Get the ESwingConstant value of the corresponding SwingConstant position.
     * Use ESwingConstant.directionValueOf for translating directions.
     * Use ESwingConstant.orientationValueOf for translating orientations.
     * Use ESwingConstant.textDirectionValueOf for translation text direction.
     * @return the corresponding ESwingConstant or INVALID.
     */
    public static ESwingConstant positionValueOf(int val){
        return switch (val) {
            case 0 -> CENTER;
            case 1 -> TOP;
            case 2 -> LEFT;
            case 3 -> BOTTOM;
            case 4 -> RIGHT;
            default -> INVALID;
        };
    }
    /**
     * Get the ESwingConstant value of the corresponding SwingConstant direction.
     * Use ESwingConstant.positionValueOf for translating positions.
     * Use ESwingConstant.orientationValueOf for translating orientations.
     * Use ESwingConstant.textDirectionValueOf for translation text direction.
     * @return the corresponding ESwingConstant or INVALID.
     */
    public static ESwingConstant directionValueOf(int val){
        return switch (val) {
            case 1 -> NORTH;
            case 2 -> NORTH_EAST;
            case 3 -> EAST;
            case 4 -> SOUTH_EAST;
            case 5 -> SOUTH;
            case 6 -> SOUTH_WEST;
            case 7 -> WEST;
            case 8 -> NORTH_WEST;
            default -> INVALID;
        };
    }
    /**
     * Get the ESwingConstant value of the corresponding SwingConstant orientation.
     * Use ESwingConstant.positionValueOf for translating positions.
     * Use ESwingConstant.directionValueOf for translating directions.
     * Use ESwingConstant.textDirectionValueOf for translation text direction.
     * @return the corresponding ESwingConstant or INVALID.
     */
    public static ESwingConstant orientationValueOf(int val){
        return switch (val) {
            case 0 -> HORIZONTAL;
            case 1 -> VERTICAL;
            default -> INVALID;
        };
    }
    /**
     * Get the ESwingConstant value of the corresponding SwingConstant text direction.
     * Use ESwingConstant.positionValueOf for translating positions.
     * Use ESwingConstant.directionValueOf for translating directions.
     * Use ESwingConstant.orientationValueOf for translation orientation.
     * @return the corresponding ESwingConstant or INVALID.
     */
    public static ESwingConstant textDirectionValueOf(int val){
        return switch (val) {
            case 10 -> LEADING;
            case 11 -> TRAILING;
            case 12 -> NEXT;
            case 13 -> PREVIOUS;
            default -> INVALID;
        };
    }


    public final int intVal;
    ESwingConstant(int intVal){
        this.intVal = intVal;
    }
}
