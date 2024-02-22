package org.jhotdraw.api_translation;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GBCBuilderTest {

    private IGBCBuilder getNewInstance(){
        return new GBCBuilder();
    }

    @org.junit.jupiter.api.Test
    void build() {
        //Regardless of the amount of configuration, build should never return null.
        assertNotNull(getNewInstance().build());
    }

    @org.junit.jupiter.api.Test
    void setGrid() {
        EGBCConstant eGridX = EGBCConstant.BOTH;
        EGBCConstant eGridY = EGBCConstant.RELATIVE;

        IGBCBuilder builder = getNewInstance();
        builder.setGrid(eGridX, eGridY);
        GridBagConstraints gcb = builder.build();

        assertEquals(gcb.gridx, eGridX.intVal);
        assertEquals(gcb.gridy, eGridY.intVal);
    }

    @org.junit.jupiter.api.Test
    void setGridX() {
        EGBCConstant eGridX = EGBCConstant.ABOVE_BASELINE;
        GridBagConstraints gcb = getNewInstance()
                .setGridX(eGridX)
                .build();

        assertEquals(gcb.gridx, eGridX.intVal);
    }

    @org.junit.jupiter.api.Test
    void setGridY() {
        EGBCConstant eGridY = EGBCConstant.CENTER;
        GridBagConstraints gcb = getNewInstance()
                .setGridY(eGridY)
                .build();

        assertEquals(gcb.gridy, eGridY.intVal);
    }

    @org.junit.jupiter.api.Test
    void setInsets() {
        java.util.List<Insets> testInsets = java.util.List.of(
                new Insets(0, 0, 0, 1),
                new Insets(0, 0, 1, 0),
                new Insets(0, 1, 0, 0),
                new Insets(1, 0, 0, 0),
                new Insets(1, 1, 1, 1),
                new Insets(0, 0, 0, 0)
        );

        for (Insets inset : testInsets){
            GridBagConstraints gcb = getNewInstance()
                    .setInsets(inset.top, inset.left, inset.bottom, inset.right)
                    .build();
            assertEquals(gcb.insets, inset);
            assertEquals(gcb.insets.top, inset.top);
            assertEquals(gcb.insets.left, inset.left);
            assertEquals(gcb.insets.bottom, inset.bottom);
            assertEquals(gcb.insets.right, inset.right);
        }
    }

    @org.junit.jupiter.api.Test
    void setAnchor() {
        EGBCConstant anchor = EGBCConstant.SOUTH;

        GridBagConstraints gcb = getNewInstance()
                .setAnchor(anchor)
                .build();

        assertEquals(gcb.anchor, anchor.intVal);
    }

    @org.junit.jupiter.api.Test
    void setFillType() {
        EGBCConstant fill = EGBCConstant.NONE;

        GridBagConstraints gcp = getNewInstance()
                .setFillType(fill)
                .build();

        assertEquals(gcp.fill, fill.intVal);
    }

    @org.junit.jupiter.api.Test
    void setWeightX() {
        double weightX = 0.1;

        GridBagConstraints gbc = getNewInstance()
                .setWeightX(weightX)
                .build();

        assertEquals(gbc.weightx, weightX);
    }

    @org.junit.jupiter.api.Test
    void setWeightY() {
        double weightY = 0.1;

        GridBagConstraints gbc = getNewInstance()
                .setWeightY(weightY)
                .build();

        assertEquals(gbc.weighty, weightY);
    }
}