package org.jhotdraw;

import org.jhotdraw.api_translation.EGBCConstantTest;
import org.jhotdraw.api_translation.ESwingConstantTest;
import org.jhotdraw.api_translation.GBCBuilderTest;
import org.jhotdraw.api_translation.JButtonBuilderTest;
import org.jhotdraw.gui.JDisclosureToolBarTest;
import org.jhotdraw.gui.plaf.palette.PaletteHandlerTest;
import org.jhotdraw.gui.plaf.palette.PalettePropertyTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ESwingConstantTest.class,
        EGBCConstantTest.class,
        GBCBuilderTest.class,
        JButtonBuilderTest.class,
        PaletteHandlerTest.class,
        PalettePropertyTest.class,
        JDisclosureToolBarTest.class
})
public class JhotDrawTestSuite {
}
