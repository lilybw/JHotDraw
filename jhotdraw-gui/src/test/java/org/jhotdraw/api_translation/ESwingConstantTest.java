package org.jhotdraw.api_translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ESwingConstantTest {

    @Test
    void positionValueOf() {
        // Test positions
        assertSame(ESwingConstant.CENTER, ESwingConstant.positionValueOf(0), "CENTER should be mapped to 0");
        assertSame(ESwingConstant.TOP, ESwingConstant.positionValueOf(1), "TOP should be mapped to 1");

    }

    @Test
    void directionValueOf() {
        // Test compass directions
        assertSame(ESwingConstant.NORTH, ESwingConstant.directionValueOf(1), "NORTH should be mapped to 1");
        assertSame(ESwingConstant.EAST, ESwingConstant.directionValueOf(3), "EAST should be mapped to 3");

    }

    @Test
    void orientationValueOf() {
        // Test orientations
        assertSame(ESwingConstant.HORIZONTAL, ESwingConstant.orientationValueOf(0), "HORIZONTAL should be mapped to 0");
        assertSame(ESwingConstant.VERTICAL, ESwingConstant.orientationValueOf(1), "VERTICAL should be mapped to 1");
    }

    @Test
    void textDirectionValueOf() {
        // Test text directions
        assertSame(ESwingConstant.LEADING, ESwingConstant.textDirectionValueOf(10), "LEADING should be mapped to 10");
        assertSame(ESwingConstant.TRAILING, ESwingConstant.textDirectionValueOf(11), "TRAILING should be mapped to 11");
    }

    @Test
    void testInvalidValueOf() {
        // Test invalid inputs for each translation method
        assertSame(ESwingConstant.INVALID, ESwingConstant.positionValueOf(-2), "Invalid position should return INVALID");
        assertSame(ESwingConstant.INVALID, ESwingConstant.directionValueOf(100), "Invalid direction should return INVALID");
        assertSame(ESwingConstant.INVALID, ESwingConstant.orientationValueOf(-1), "Invalid orientation should return INVALID");
        assertSame(ESwingConstant.INVALID, ESwingConstant.textDirectionValueOf(14), "Invalid text direction should return INVALID");
    }
}