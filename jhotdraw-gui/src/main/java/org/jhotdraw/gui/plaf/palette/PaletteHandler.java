package org.jhotdraw.gui.plaf.palette;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PaletteHandler implements ContainerListener,
        FocusListener, MouseInputListener, PropertyChangeListener {

    private final IPaletteToolBarUI palette;
    private JToolBar toolBar;
    private boolean isDragging = false;
    private Point origin = null;
    private boolean isArmed = false;

    public PaletteHandler(IPaletteToolBarUI paletteToolBarUI) {
        this.palette = paletteToolBarUI;
    }

    // ContainerListener
    @Override
    public void componentAdded(ContainerEvent evt) {
        Component c = evt.getChild();
        c.addFocusListener(this);

        if (palette.isRolloverBorders()) {
            palette.setBorderToRollover(c);
        } else {
            palette.setBorderToNonRollover(c);
        }
    }

    @Override
    public void componentRemoved(ContainerEvent evt) {
        Component c = evt.getChild();
        c.removeFocusListener(this);
        // Revert the button border
        palette.setBorderToNormal(c);
    }

    // FocusListener
    @Override
    public void focusGained(FocusEvent evt) {
        Component c = evt.getComponent();
        palette.setFocusedCompIndex(toolBar.getComponentIndex(c));
    }

    @Override
    public void focusLost(FocusEvent evt) {
    }


    @Override
    public void mousePressed(MouseEvent evt) {
        if (!toolBar.isEnabled()) {
            return;
        }
        isDragging = false;
        if (evt.getSource() instanceof JToolBar) {
            JComponent source = (JComponent) evt.getSource();
            Insets insets;
            if (source.getBorder() instanceof PaletteToolBarBorder) {
                insets = ((PaletteToolBarBorder) source.getBorder()).getDragInsets(source);
            } else {
                insets = source.getInsets();
            }
            isArmed = isClickOutsideBounds(evt, insets, source);
        }
    }

    private boolean isClickOutsideBounds(MouseEvent evt, Insets insets, JComponent source){
        return (evt.getX() <= insets.left) || (evt.getX() >= (source.getWidth() - insets.right))
                || (evt.getY() <= insets.top) || (evt.getY() >= (source.getHeight() - insets.bottom));
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
        palette.dragTo(position, origin);
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        if (!toolBar.isEnabled()) {
            return;
        }
        if (isDragging) {
            Point position = evt.getPoint();
            if (origin == null) {
                origin = evt.getComponent().getLocationOnScreen();
            }
            palette.floatAt(position, origin);
        }
        origin = null;
        isDragging = false;
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
            toolBar.updateUI();
            return;
        }
        if ("orientation".equals(propertyName)) {
            // Search for JSeparator components and change it's orientation
            // to match the toolbar and flip it's orientation.
            Component[] components = toolBar.getComponents();
            int orientation = ((Integer) evt.getNewValue());
            evaluateComponentSizeAndOrientation(components, orientation);
        } else if ((propertyName == null && PaletteProperty.IS_ROLLOVER == null) || (propertyName != null && propertyName.equals(PaletteProperty.IS_ROLLOVER.strVal))) {
            palette.installNormalBorders(toolBar);
            palette.setRolloverBorders(((Boolean) evt.getNewValue()));
        }
    }

    private void evaluateComponentSizeAndOrientation(Component[] components, int orientation) {
        for (Component component : components) {
            if (!(component instanceof JToolBar.Separator separator)) {
                continue;
            }
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

    public void setToolBar(JToolBar toolBar){
        this.toolBar = toolBar;
    }
    public boolean isArmed(){
        return isArmed;
    }
    public boolean isDragging(){
        return isDragging;
    }
}
