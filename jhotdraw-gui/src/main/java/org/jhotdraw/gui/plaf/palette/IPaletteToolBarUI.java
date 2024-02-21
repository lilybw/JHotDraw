package org.jhotdraw.gui.plaf.palette;

import javax.swing.*;
import java.awt.*;

public interface IPaletteToolBarUI extends SwingConstants {

    boolean isRolloverBorders();
    void setRolloverBorders(boolean state);
    void installNormalBorders(JComponent c);
    void setBorderToRollover(Component c);
    void setBorderToNonRollover(Component c);
    void setBorderToNormal(Component c);
    void setFloatingLocation(int x, int y);
    boolean isFloating();
    void setFloating(boolean b, Point p);
    void setOrientation(int orientation);
    Color getDockingColor();
    void setDockingColor(Color c);
    Color getFloatingColor();
    void setFloatingColor(Color c);
    void dragTo(Point position, Point origin);
    void floatAt(Point position, Point origin);
    void setFocusedCompIndex(int index);
}
