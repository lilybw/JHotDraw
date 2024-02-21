package org.jhotdraw.gui.plaf.palette;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;

class PaletteColorManager {

    private Color dockingColor;
    private Color floatingColor;
    private Color dockingBorderColor;
    private Color floatingBorderColor;

    public PaletteColorManager(){
        this(null, null, null, null);
    }

    public PaletteColorManager(Color dockingColor, Color floatingColor, Color dockingBorderColor, Color floatingBorderColor){
        if (dockingColor == null || dockingColor instanceof UIResource) {
            this.dockingColor = UIManager.getColor("ToolBar.dockingBackground");
        }
        if (floatingColor == null || floatingColor instanceof UIResource) {
            this.floatingColor = UIManager.getColor("ToolBar.floatingBackground");
        }
        if (dockingBorderColor == null
                || dockingBorderColor instanceof UIResource) {
            this.dockingBorderColor = UIManager.getColor("ToolBar.dockingForeground");
        }
        if (floatingBorderColor == null
                || floatingBorderColor instanceof UIResource) {
            this.floatingBorderColor = UIManager.getColor("ToolBar.floatingForeground");
        }
    }

    /**
     * Sets all colors to null.
     */
    public void clear(){
        dockingColor = null;
        floatingColor = null;
        dockingBorderColor = null;
        floatingBorderColor = null;
    }


    public Color getDockingColor() {
        return dockingColor;
    }

    public void setDockingColor(Color dockingColor) {
        this.dockingColor = dockingColor;
    }

    public Color getFloatingColor() {
        return floatingColor;
    }

    public void setFloatingColor(Color floatingColor) {
        this.floatingColor = floatingColor;
    }

    public Color getDockingBorderColor() {
        return dockingBorderColor;
    }

    public void setDockingBorderColor(Color dockingBorderColor) {
        this.dockingBorderColor = dockingBorderColor;
    }

    public Color getFloatingBorderColor() {
        return floatingBorderColor;
    }

    public void setFloatingBorderColor(Color floatingBorderColor) {
        this.floatingBorderColor = floatingBorderColor;
    }


}
