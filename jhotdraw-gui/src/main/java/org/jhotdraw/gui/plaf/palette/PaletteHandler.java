package org.jhotdraw.gui.plaf.palette;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PaletteHandler implements ContainerListener,
        FocusListener, MouseInputListener, PropertyChangeListener {

    private final PaletteToolBarUI paletteToolBarUI;

    public PaletteHandler(PaletteToolBarUI paletteToolBarUI) {
        this.paletteToolBarUI = paletteToolBarUI;
    }

    // ContainerListener
    @Override
    public void componentAdded(ContainerEvent evt) {
        Component c = evt.getChild();
        if (paletteToolBarUI.toolBarFocusListener != null) {
            c.addFocusListener(paletteToolBarUI.toolBarFocusListener);
        }
        if (paletteToolBarUI.isRolloverBorders()) {
            paletteToolBarUI.setBorderToRollover(c);
        } else {
            paletteToolBarUI.setBorderToNonRollover(c);
        }
    }

    @Override
    public void componentRemoved(ContainerEvent evt) {
        Component c = evt.getChild();
        if (paletteToolBarUI.toolBarFocusListener != null) {
            c.removeFocusListener(paletteToolBarUI.toolBarFocusListener);
        }
        // Revert the button border
        paletteToolBarUI.setBorderToNormal(c);
    }

    // FocusListener
    @Override
    public void focusGained(FocusEvent evt) {
        Component c = evt.getComponent();
        paletteToolBarUI.focusedCompIndex = paletteToolBarUI.toolBar.getComponentIndex(c);
    }

    @Override
    public void focusLost(FocusEvent evt) {
    }

    // MouseInputListener (DockingListener)
    private JToolBar toolBar;
    private boolean isDragging = false;
    private Point origin = null;
    private boolean isArmed = false;



    @Override
    public void mousePressed(MouseEvent evt) {
        if (!toolBar.isEnabled()) {
            return;
        }
        isDragging = false;
        if (evt.getSource() instanceof JToolBar) {
            JComponent c = (JComponent) evt.getSource();
            Insets insets;
            if (c.getBorder() instanceof PaletteToolBarBorder) {
                insets = ((PaletteToolBarBorder) c.getBorder()).getDragInsets(c);
            } else {
                insets = c.getInsets();
            }
            isArmed = !(evt.getX() > insets.left && evt.getX() < c.getWidth() - insets.right
                    && evt.getY() > insets.top && evt.getY() < c.getHeight() - insets.bottom);
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        if (!toolBar.isEnabled()) {
            return;
        }
        if (isDragging == true) {
            Point position = evt.getPoint();
            if (origin == null) {
                origin = evt.getComponent().getLocationOnScreen();
            }
            paletteToolBarUI.floatAt(position, origin);
        }
        origin = null;
        isDragging = false;
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        if (!toolBar.isEnabled()) {
            return;
        }
        if (!isArmed) {
            return;
        }
        isDragging = true;
        Point position = evt.getPoint();
        if (origin == null) {
            origin = evt.getComponent().getLocationOnScreen();
        }
        paletteToolBarUI.dragTo(position, origin);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {}
    @Override
    public void mouseEntered(MouseEvent evt) {}
    @Override
    public void mouseExited(MouseEvent evt) {}
    @Override
    public void mouseMoved(MouseEvent evt) {}

    // PropertyChangeListener
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("lookAndFeel".equals(propertyName)) {
            paletteToolBarUI.toolBar.updateUI();
        } else if ("orientation".equals(propertyName)) {
            // Search for JSeparator components and change it's orientation
            // to match the toolbar and flip it's orientation.
            Component[] components = paletteToolBarUI.toolBar.getComponents();
            int orientation = ((Integer) evt.getNewValue());
            JToolBar.Separator separator;
            for (int i = 0; i < components.length; ++i) {
                if (components[i] instanceof JToolBar.Separator) {
                    separator = (JToolBar.Separator) components[i];
                    if ((orientation == JToolBar.HORIZONTAL)) {
                        separator.setOrientation(JSeparator.VERTICAL);
                    } else {
                        separator.setOrientation(JSeparator.HORIZONTAL);
                    }
                    Dimension size = separator.getSeparatorSize();
                    if (size != null && size.width != size.height) {
                        // Flip the orientation.
                        Dimension newSize
                                = new Dimension(size.height, size.width);
                        separator.setSeparatorSize(newSize);
                    }
                }
            }
        } else if ((propertyName == null && PaletteProperty.IS_ROLLOVER == null) || (propertyName != null && propertyName.equals(PaletteProperty.IS_ROLLOVER))) {
            paletteToolBarUI.installNormalBorders(paletteToolBarUI.toolBar);
            paletteToolBarUI.setRolloverBorders(((Boolean) evt.getNewValue()));
        }
    }

    public void setToolBar(JToolBar toolBar){
        this.toolBar = toolBar;
    }
}
