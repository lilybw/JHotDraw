package org.jhotdraw.gui.plaf.palette;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PalettePropertyTest {

    @Test
    void testEnumValuesMatchStringValues() {
        // Test that each enum constant matches its expected string value
        assertEquals("invalid", PaletteProperty.INVALID.strVal);
        assertEquals("JToolBar.isRollover", PaletteProperty.IS_ROLLOVER.strVal);
        assertEquals("Palette.ToolBar.isDividerDrawn", PaletteProperty.IS_DIVIDER_DRAWN.strVal);
        assertEquals("Palette.ToolBar.icon", PaletteProperty.TOOLBAR_ICON_PROPERTY.strVal);
        assertEquals("Palette.ToolBar.textIconGap", PaletteProperty.TOOLBAR_TEXT_ICON_GAP_PROPERTY.strVal);
        assertEquals("Palette.ToolBar.insetsOverride", PaletteProperty.TOOLBAR_INSETS_OVERRIDE_PROPERTY.strVal);
        assertEquals("JToolBar.focusedCompIndex", PaletteProperty.FOCUSED_COMP_INDEX.strVal);
    }

    @Test
    void testStrValueOfMatchesEnum() {
        // Test the strValueOf method for each enum constant
        assertSame(PaletteProperty.INVALID, PaletteProperty.strValueOf("invalid"));
        assertSame(PaletteProperty.IS_ROLLOVER, PaletteProperty.strValueOf("JToolBar.isRollover"));
        assertSame(PaletteProperty.IS_DIVIDER_DRAWN, PaletteProperty.strValueOf("Palette.ToolBar.isDividerDrawn"));
        assertSame(PaletteProperty.TOOLBAR_ICON_PROPERTY, PaletteProperty.strValueOf("Palette.ToolBar.icon"));
        assertSame(PaletteProperty.TOOLBAR_TEXT_ICON_GAP_PROPERTY, PaletteProperty.strValueOf("Palette.ToolBar.textIconGap"));
        assertSame(PaletteProperty.TOOLBAR_INSETS_OVERRIDE_PROPERTY, PaletteProperty.strValueOf("Palette.ToolBar.insetsOverride"));
        assertSame(PaletteProperty.FOCUSED_COMP_INDEX, PaletteProperty.strValueOf("JToolBar.focusedCompIndex"));
    }

    @Test
    void testStrValueOfReturnsInvalidForUnmatchedString() {
        // Test that an unmatched string returns INVALID
        assertSame(PaletteProperty.INVALID, PaletteProperty.strValueOf("nonexistent.property"));
    }
}