package org.jhotdraw.api_translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EGBCConstantTest {

    @Test
    void testIntValueMapping() {
        // Test a selection of enum values to ensure they are mapped correctly
        assertEquals(-1, EGBCConstant.RELATIVE.intVal, "RELATIVE should be mapped to -1");
        assertEquals(0, EGBCConstant.REMAINDER.intVal, "REMAINDER should be mapped to 0");
        assertEquals(1, EGBCConstant.BOTH.intVal, "BOTH should be mapped to 1");
        assertEquals(10, EGBCConstant.CENTER.intVal, "CENTER should be mapped to 10");
    }

    @Test
    void testValueOf() {
        // Test that valueOf returns the correct enum constant for a given value
        assertSame(EGBCConstant.RELATIVE, EGBCConstant.valueOf(-1), "valueOf -1 should return RELATIVE");
        assertSame(EGBCConstant.REMAINDER, EGBCConstant.valueOf(0), "valueOf 0 should return REMAINDER");
        assertSame(EGBCConstant.BOTH, EGBCConstant.valueOf(1), "valueOf 1 should return BOTH");
    }

    @Test
    void testValueOfInvalid() {
        // Test that valueOf returns INVALID for a value not represented by any constant
        assertSame(EGBCConstant.INVALID, EGBCConstant.valueOf(Integer.MAX_VALUE), "valueOf with no matching constant should return INVALID");
    }

    @Test
    void testValueOfWithSharedValues() {
        // Special test cases for shared values
        // Note: This is a conceptual demonstration. In practice, REMAINDER and NONE share the same value and cannot be distinguished.
        // This test assumes a hypothetical scenario where there's a need to distinguish between values that are mapped similarly in practice.
        assertSame(EGBCConstant.REMAINDER, EGBCConstant.valueOf(0), "valueOf 0 should preferably return REMAINDER over NONE due to their shared value");
    }
}