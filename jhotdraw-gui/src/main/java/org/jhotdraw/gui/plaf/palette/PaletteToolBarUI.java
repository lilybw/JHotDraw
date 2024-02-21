/**
 * @(#)PaletteToolBarUI.java
 *
 * Copyright (c) 2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.gui.plaf.palette;

import org.jhotdraw.api_translation.ESwingConstant;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

/**
 * ToolBarUI for palette components.
 * <p>
 * This UI differs from BasicToolBarUI, in that the component holding the toolbar
 * is supposed to use BoxLayout instead of BorderLayout. This allows to have
 * multiple toolbars in the same component. The toolbars can be reorderd in the
 * component, but they are not allowed to float in their own floating window.
 * <p>
 * The JToolBar starts dragging only, if the drag starts over the insets of
 * its border.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class PaletteToolBarUI extends ToolBarUI implements IPaletteToolBarUI {
    protected JToolBar toolBar;
    protected Integer constraintBeforeFloating = 0;
    protected int focusedCompIndex = -1;
    private static final boolean IS_FLOATING_ALLOWED = false;
    private static Border rolloverBorder;
    private static Border nonRolloverBorder;
    private static Border nonRolloverToggleBorder;
    private PaletteDragWindow dragWindow;
    private final PaletteColorManager colorManager = new PaletteColorManager();
    private final HashMap<AbstractButton, Border> borderTable = new HashMap<>();
    private final HashMap<AbstractButton, Boolean> rolloverTable = new HashMap<>();
    private RootPaneContainer floatingToolBar;
    private Container dockingSource;
    private PaletteHandler handler;
    private boolean rolloverBorders = false;
    private boolean floating;
    private int floatingX;
    private int floatingY;

    public static ComponentUI createUI(JComponent c) {
        return new PaletteToolBarUI();
    }

    @Override
    public void installUI(JComponent c) {
        toolBar = (JToolBar) c;
        // Set defaults
        installDefaults();
        installComponents();
        installListeners();
        installKeyboardActions();
        // Initialize instance vars
        floating = false;
        floatingX = floatingY = 0;
        floatingToolBar = null;
        setOrientation(toolBar.getOrientation());
        LookAndFeel.installProperty(c, "opaque", Boolean.TRUE);
        if (c.getClientProperty(PaletteProperty.FOCUSED_COMP_INDEX.strVal) != null) {
            focusedCompIndex = ((Integer) (c.getClientProperty(PaletteProperty.FOCUSED_COMP_INDEX.strVal)));
        }
    }

    @Override
    public void uninstallUI(JComponent c) {
        // Clear defaults
        uninstallDefaults();
        uninstallComponents();
        uninstallListeners();
        uninstallKeyboardActions();
        // Clear instance vars
        if (isFloating()) {
            setFloating(false, null);
        }
        floatingToolBar = null;
        dragWindow = null;
        dockingSource = null;
        c.putClientProperty(PaletteProperty.FOCUSED_COMP_INDEX.strVal, focusedCompIndex);
    }

    private void installDefaults() {
        PaletteLookAndFeel.installBorder(toolBar, "ToolBar.border");
        PaletteLookAndFeel.installColorsAndFont(toolBar,
                "ToolBar.background",
                "ToolBar.foreground",
                "ToolBar.font");
        // ToolBar rollover button borders
        Object rolloverProp = toolBar.getClientProperty(PaletteProperty.IS_ROLLOVER);
        if (rolloverProp == null) {
            rolloverProp = UIManager.get("ToolBar.isRollover");
        }
        if (rolloverProp != null) {
            rolloverBorders = ((Boolean) rolloverProp);
        }
        if (rolloverBorder == null) {
            rolloverBorder = createRolloverBorder();
        }
        if (nonRolloverBorder == null) {
            nonRolloverBorder = createNonRolloverBorder();
        }
        if (nonRolloverToggleBorder == null) {
            nonRolloverToggleBorder = createNonRolloverToggleBorder();
        }
        setRolloverBorders(isRolloverBorders());
    }

    private void uninstallDefaults() {
        LookAndFeel.uninstallBorder(toolBar);
        colorManager.clear();
        installNormalBorders(toolBar);
        rolloverBorder = null;
        nonRolloverBorder = null;
        nonRolloverToggleBorder = null;
    }

    private void installComponents() {
    }

    private void uninstallComponents() {
    }

    private void installListeners() {
        PaletteHandler handler = getHandler();
        handler.setToolBar(toolBar);

        toolBar.addMouseMotionListener(handler);
        toolBar.addMouseListener(handler);
        toolBar.addPropertyChangeListener(handler);
        toolBar.addContainerListener(handler);

        // Put focus listener on all components in toolbar
        Component[] components = toolBar.getComponents();
        for (Component component : components) {
            component.addFocusListener(handler);
        }
    }

    private void uninstallListeners() {
        toolBar.removeMouseMotionListener(handler);
        toolBar.removeMouseListener(handler);
        toolBar.removePropertyChangeListener(handler);
        toolBar.removeContainerListener(handler);

        // Remove focus listener from all components in toolbar
        Component[] components = toolBar.getComponents();
        for (Component component : components) {
            component.removeFocusListener(handler);
        }

        handler = null;
    }

    private void installKeyboardActions() {
        InputMap km = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        SwingUtilities.replaceUIInputMap(toolBar, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
                km);
        PaletteLazyActionMap.installLazyActionMap(toolBar, PaletteToolBarUI.class,
                "ToolBar.actionMap");
    }

    private InputMap getInputMap(int condition) {
        if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) {
            return (InputMap) PaletteLookAndFeel.getInstance().get(
                    "ToolBar.ancestorInputMap");
        }
        return null;
    }

    private void uninstallKeyboardActions() {
        SwingUtilities.replaceUIActionMap(toolBar, null);
        SwingUtilities.replaceUIInputMap(toolBar, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
                null);
    }

    private void navigateFocusedComp(int direction) {
        int nComp = toolBar.getComponentCount();
        if (focusedCompIndex < 0 || focusedCompIndex >= nComp) {
            return;
        }
        switch (ESwingConstant.directionValueOf(direction)) {
            case EAST, SOUTH -> navigateEastAndSouth(nComp);
            case WEST, NORTH -> navigateWestAndNorth(nComp);
        }
    }

    private void navigateWestAndNorth(int nComp) {
        int j = focusedCompIndex - 1;
        while (j != focusedCompIndex) {
            if (j < 0) {
                j = nComp - 1;
            }
            Component comp = toolBar.getComponentAtIndex(j--);
            if (validateCompFocus(comp)) {
                comp.requestFocus();
                break;
            }
        }
    }

    private void navigateEastAndSouth(int nComp) {
        int j = focusedCompIndex + 1;
        while (j != focusedCompIndex) {
            if (j >= nComp) {
                j = 0;
            }
            Component comp = toolBar.getComponentAtIndex(j++);
            if (validateCompFocus(comp)) {
                comp.requestFocus();
                break;
            }
        }
    }

    private boolean validateCompFocus(Component comp){
        return comp != null && comp.isFocusable() && comp.isEnabled();
    }

    /**
     * Creates a rollover border for toolbar components. The
     * rollover border will be installed if rollover borders are
     * enabled.
     * <p>
     * Override this method to provide an alternate rollover border.
     *
     * @since 1.4
     */
    private Border createRolloverBorder() {
        Object border = UIManager.get("ToolBar.rolloverBorder");
        if (border != null) {
            return (Border) border;
        }
        return new EmptyBorder(0, 0, 0, 0);
    }

    /**
     * Creates the non rollover border for toolbar components. This
     * border will be installed as the border for components added
     * to the toolbar if rollover borders are not enabled.
     * <p>
     * Override this method to provide an alternate rollover border.
     *
     * @since 1.4
     */
    private Border createNonRolloverBorder() {
        Object border = UIManager.get("ToolBar.nonrolloverBorder");
        if (border != null) {
            return (Border) border;
        }
        return new EmptyBorder(0, 0, 0, 0);
    }

    /**
     * Creates a non rollover border for Toggle buttons in the toolbar.
     */
    private Border createNonRolloverToggleBorder() {
        return new EmptyBorder(0, 0, 0, 0);
    }

    /**
     * Creates a window which contains the toolbar after it has been
     * dragged out from its container
     *
     * @return a <code>RootPaneContainer</code> object, containing the toolbar.
     */
    private RootPaneContainer createFloatingWindow(JToolBar toolbar) {
        JDialog dialog;
        Window window = SwingUtilities.getWindowAncestor(toolbar);
        if (window instanceof Frame) {
            dialog = new ToolBarDialog((Frame) window, toolbar.getName(), false);
        } else if (window instanceof Dialog) {
            dialog = new ToolBarDialog((Dialog) window, toolbar.getName(), false);
        } else {
            dialog = new ToolBarDialog((Frame) null, toolbar.getName(), false);
        }
        dialog.getRootPane().setName("ToolBar.FloatingWindow");
        dialog.setTitle(toolbar.getName());
        dialog.setResizable(false);
        WindowListener wl = createFrameListener();
        dialog.addWindowListener(wl);
        return dialog;
    }

    private PaletteDragWindow createDragWindow(JToolBar toolbar) {
        Window frame = null;
        if (toolBar != null) {
            Container p;
            for (p = toolBar.getParent(); p != null && !(p instanceof Window); p = p.getParent()) {
            }
            frame = (Window) p;
        }
        if (floatingToolBar == null) {
            floatingToolBar = createFloatingWindow(toolBar);
        }
        if (floatingToolBar instanceof Window) {
            frame = (Window) floatingToolBar;
        }
        PaletteDragWindow w = new PaletteDragWindow(frame, toolBar);
        JRootPane rp = ((RootPaneContainer) w).getRootPane();
        rp.putClientProperty("Window.alpha", 0.6f);
        return w;
    }

    /**
     * Returns a flag to determine whether rollover button borders
     * are enabled.
     *
     * @return true if rollover borders are enabled; false otherwise
     * @see #setRolloverBorders
     * @since 1.4
     */
    public boolean isRolloverBorders() {
        return rolloverBorders;
    }

    /**
     * Sets the flag for enabling rollover borders on the toolbar and it will
     * also install the apropriate border depending on the state of the flag.
     *
     * @param rollover if true, rollover borders are installed.
     * Otherwise non-rollover borders are installed
     * @see #isRolloverBorders
     * @since 1.4
     */
    public void setRolloverBorders(boolean rollover) {
        rolloverBorders = rollover;
        if (rolloverBorders) {
            installRolloverBorders(toolBar);
        } else {
            installNonRolloverBorders(toolBar);
        }
    }

    /**
     * Installs rollover borders on all the child components of the JComponent.
     * <p>
     * This is a convenience method to call <code>setBorderToRollover</code>
     * for each child component.
     *
     * @param c container which holds the child components (usally a JToolBar)
     * @see #setBorderToRollover
     * @since 1.4
     */
    private void installRolloverBorders(JComponent c) {
        // Put rollover borders on buttons
        Component[] components = c.getComponents();
        for (Component component : components) {
            if (component instanceof JComponent) {
                ((JComponent) component).updateUI();
                setBorderToRollover(component);
            }
        }
    }

    /**
     * Installs non-rollover borders on all the child components of the JComponent.
     * A non-rollover border is the border that is installed on the child component
     * while it is in the toolbar.
     * <p>
     * This is a convenience method to call <code>setBorderToNonRollover</code>
     * for each child component.
     *
     * @param c container which holds the child components (usally a JToolBar)
     * @see #setBorderToNonRollover
     * @since 1.4
     */
    private void installNonRolloverBorders(JComponent c) {
        // Put non-rollover borders on buttons. These borders reduce the margin.
        Component[] components = c.getComponents();
        for (int i = 0; i < components.length; ++i) {
            if (components[i] instanceof JComponent) {
                ((JComponent) components[i]).updateUI();
                setBorderToNonRollover(components[i]);
            }
        }
    }

    /**
     * Installs normal borders on all the child components of the JComponent.
     * A normal border is the original border that was installed on the child
     * component before it was added to the toolbar.
     * <p>
     * This is a convenience method to call <code>setBorderNormal</code>
     * for each child component.
     *
     * @param c container which holds the child components (usally a JToolBar)
     * @see #setBorderToNonRollover
     * @since 1.4
     */
    public void installNormalBorders(JComponent c) {
        // Put back the normal borders on buttons
        Component[] components = c.getComponents();
        for (int i = 0; i < components.length; ++i) {
            setBorderToNormal(components[i]);
        }
    }

    /**
     * Sets the border of the component to have a rollover border which
     * was created by <code>createRolloverBorder</code>.
     *
     * @param c component which will have a rollover border installed
     * @see #createRolloverBorder
     * @since 1.4
     */
    public void setBorderToRollover(Component c) {
        if (true) {
            return;
        }
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) c;
            Border border = borderTable.get(b);
            if (border == null || border instanceof UIResource) {
                borderTable.put(b, b.getBorder());
            }
            // Only set the border if its the default border
            if (b.getBorder() instanceof UIResource) {
                b.setBorder(getRolloverBorder(b));
            }
            rolloverTable.put(b, b.isRolloverEnabled());
            b.setRolloverEnabled(true);
        }
    }

    private Border getRolloverBorder(AbstractButton b) {
        Object borderProvider = UIManager.get("ToolBar.rolloverBorderProvider");
        if (borderProvider == null) {
            return rolloverBorder;
        }
        //return ((BorderProvider) borderProvider).getRolloverBorder(b);
        return null;
    }

    /**
     * Sets the border of the component to have a non-rollover border which
     * was created by <code>createNonRolloverBorder</code>.
     *
     * @param c component which will have a non-rollover border installed
     * @see #createNonRolloverBorder
     * @since 1.4
     */
    public void setBorderToNonRollover(Component c) {
        if (true) {
            return;
        }
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) c;
            Border border = borderTable.get(b);
            if (border == null || border instanceof UIResource) {
                borderTable.put(b, b.getBorder());
            }
            // Only set the border if its the default border
            if (b.getBorder() instanceof UIResource) {
                if (b instanceof JToggleButton) {
                    ((JToggleButton) b).setBorder(nonRolloverToggleBorder);
                } else {
                    b.setBorder(nonRolloverBorder);
                }
            }
            rolloverTable.put(b, b.isRolloverEnabled() ? Boolean.TRUE : Boolean.FALSE);
            b.setRolloverEnabled(false);
        }
    }

    /**
     * Sets the border of the component to have a normal border.
     * A normal border is the original border that was installed on the child
     * component before it was added to the toolbar.
     *
     * @param c component which will have a normal border re-installed
     * @see #createNonRolloverBorder
     * @since 1.4
     */
    public void setBorderToNormal(Component c) {
        if (true) {
            return;
        }
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) c;
            Border border = borderTable.remove(b);
            b.setBorder(border);
            Boolean value = rolloverTable.remove(b);
            if (value != null) {
                b.setRolloverEnabled(value);
            }
        }
    }

    public void setFloatingLocation(int x, int y) {
        floatingX = x;
        floatingY = y;
    }

    public boolean isFloating() {
        return floating;
    }

    public void setFloating(boolean b, Point p) {
        if (toolBar.isFloatable()) {
            if (dragWindow != null) {
                dragWindow.setVisible(false);
            }
            if (this.floating && IS_FLOATING_ALLOWED) {
                if (dockingSource == null) {
                    dockingSource = toolBar.getParent();
                    dockingSource.remove(toolBar);
                }
                constraintBeforeFloating = calculateConstraint();
                if (handler != null) {
                    UIManager.addPropertyChangeListener(handler);
                }
                if (floatingToolBar == null) {
                    floatingToolBar = createFloatingWindow(toolBar);
                }
                floatingToolBar.getContentPane().add(toolBar, BorderLayout.CENTER);
                if (floatingToolBar instanceof Window) {
                    ((Window) floatingToolBar).pack();
                }
                if (floatingToolBar instanceof Window) {
                    ((Window) floatingToolBar).setLocation(floatingX, floatingY);
                }
                if (floatingToolBar instanceof Window) {
                    ((Window) floatingToolBar).setVisible(true);
                }
            } else {
                if (floatingToolBar == null) {
                    floatingToolBar = createFloatingWindow(toolBar);
                }
                if (floatingToolBar instanceof Window) {
                    ((Window) floatingToolBar).setVisible(false);
                }
                floatingToolBar.getContentPane().remove(toolBar);
                Integer constraint = getDockingConstraint(dockingSource,
                        p);
                if (constraint == null) {
                    constraint = 0;
                }
                int orientation = mapConstraintToOrientation(constraint);
                setOrientation(orientation);
                if (dockingSource == null) {
                    dockingSource = toolBar.getParent();
                }
                if (handler != null) {
                    UIManager.removePropertyChangeListener(handler);
                }
                dockingSource.add(toolBar, constraint.intValue());
            }
            dockingSource.invalidate();
            Container dockingSourceParent = dockingSource.getParent();
            if (dockingSourceParent != null) {
                dockingSourceParent.validate();
            }
            dockingSource.repaint();
        }
    }

    private int mapConstraintToOrientation(Object constraint) {
        int orientation = toolBar.getOrientation();
        if (constraint != null) {
            if (constraint.equals(BorderLayout.EAST) || constraint.equals(BorderLayout.WEST)) {
                orientation = JToolBar.VERTICAL;
            } else if (constraint.equals(BorderLayout.NORTH) || constraint.equals(BorderLayout.SOUTH)) {
                orientation = JToolBar.HORIZONTAL;
            }
        }
        return orientation;
    }

    public void setOrientation(int orientation) {
        toolBar.setOrientation(orientation);
        if (dragWindow != null) {
            dragWindow.setOrientation(orientation);
        }
    }

    /**
     * Gets the color displayed when over a docking area
     */
    public Color getDockingColor() {
        return colorManager.getDockingColor();
    }

    /**
     * Sets the color displayed when over a docking area
     */
    public void setDockingColor(Color c) {
        colorManager.setDockingColor(c);
    }

    /**
     * Gets the color displayed when over a floating area
     */
    public Color getFloatingColor() {
        return colorManager.getFloatingColor();
    }

    /**
     * Sets the color displayed when over a floating area
     */
    public void setFloatingColor(Color c) {
        colorManager.setFloatingColor(c);
    }

    public boolean canDock(Component c, Point p) {
        return (p != null && getDockingConstraint(c, p) != null);
    }

    private Integer calculateConstraint() {
        Integer constraint = null;
        LayoutManager lm = dockingSource.getLayout();
        if (lm instanceof BoxLayout) {
            for (int i = 0, n = dockingSource.getComponentCount(); i < n; i++) {
                if (dockingSource.getComponent(i) == toolBar) {
                    constraint = i;
                    break;
                }
            }
        }
        return (constraint != null) ? constraint : constraintBeforeFloating;
    }

    private Integer getDockingConstraint(Component c, Point p) {
        if (p == null) {
            return constraintBeforeFloating;
        }
        if (c.contains(p)) {
            for (int i = 0, n = dockingSource.getComponentCount(); i < n; i++) {
                Component child = dockingSource.getComponent(i);
                Point childP = new Point(p.x - child.getX(), p.y - child.getY());
                if (child.contains(childP)) {
                    return Math.min(n - 1, (childP.x <= child.getWidth()) ? i : i + 1);
                }
            }
            if (dockingSource.getComponentCount() == 0
                    || p.x < dockingSource.getComponent(0).getX()) {
                return 0;
            }
            return dockingSource.getComponentCount() - 1;
        }
        return null;
    }

    public void dragTo(Point position, Point origin) {
        if (toolBar.isFloatable() == true) {
            try {
                if (dragWindow == null) {
                    dragWindow = createDragWindow(toolBar);
                }
                Point offset = dragWindow.getOffset();
                if (offset == null) {
                    //Dimension size = toolBar.getPreferredSize();
                    Dimension size = toolBar.getSize();
                    offset = new Point(size.width / 2, size.height / 2);
                    dragWindow.setOffset(offset);
                }
                Point global = new Point(origin.x + position.x,
                        origin.y + position.y);
                Point dragPoint = new Point(global.x - offset.x,
                        global.y - offset.y);
                if (dockingSource == null) {
                    dockingSource = toolBar.getParent();
                }
                constraintBeforeFloating = calculateConstraint();
                Point dockingPosition = dockingSource.getLocationOnScreen();
                Point comparisonPoint = new Point(global.x - dockingPosition.x,
                        global.y - dockingPosition.y);
                if (canDock(dockingSource, comparisonPoint)) {
                    dragWindow.setBackground(getDockingColor());
                    Object constraint = getDockingConstraint(dockingSource,
                            comparisonPoint);
                    int orientation = mapConstraintToOrientation(constraint);
                    dragWindow.setOrientation(orientation);
                    dragWindow.setBorderColor(colorManager.getDockingBorderColor());
                } else {
                    dragWindow.setBackground(getFloatingColor());
                    dragWindow.setBorderColor(colorManager.getFloatingBorderColor());
                }
                dragWindow.setLocation(dragPoint.x, dragPoint.y);
                if (dragWindow.isVisible() == false) {
                    //Dimension size = toolBar.getPreferredSize();
                    Dimension size = toolBar.getSize();
                    dragWindow.setSize(size.width, size.height);
                    dragWindow.setVisible(true);
                }
            } catch (IllegalComponentStateException e) {
                // allowed empty
            }
        }
    }

    public void floatAt(Point position, Point origin) {
        if (toolBar.isFloatable() == true) {
            try {
                Point offset = dragWindow.getOffset();
                if (offset == null) {
                    offset = position;
                    dragWindow.setOffset(offset);
                }
                Point global = new Point(origin.x + position.x,
                        origin.y + position.y);
                setFloatingLocation(global.x - offset.x,
                        global.y - offset.y);
                if (dockingSource != null) {
                    Point dockingPosition = dockingSource.getLocationOnScreen();
                    Point comparisonPoint = new Point(global.x - dockingPosition.x,
                            global.y - dockingPosition.y);
                    if (canDock(dockingSource, comparisonPoint)) {
                        setFloating(false, comparisonPoint);
                    } else {
                        setFloating(true, null);
                    }
                } else {
                    setFloating(true, null);
                }
                dragWindow.setOffset(null);
            } catch (IllegalComponentStateException e) {
                // allowed empty
            }
        }
    }

    @Override
    public void setFocusedCompIndex(int index) {
        this.focusedCompIndex = index;
    }

    private PaletteHandler getHandler() {
        if (handler == null) {
            handler = new PaletteHandler(this);
        }
        return handler;
    }

    private WindowListener createFrameListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent w) {
                if (toolBar.isFloatable() == true) {
                    if (dragWindow != null) {
                        dragWindow.setVisible(false);
                    }
                    floating = false;
                    if (floatingToolBar == null) {
                        floatingToolBar = createFloatingWindow(toolBar);
                    }
                    if (floatingToolBar instanceof Window) {
                        ((Window) floatingToolBar).setVisible(false);
                    }
                    floatingToolBar.getContentPane().remove(toolBar);
                    Integer constraint = constraintBeforeFloating;
                    if (dockingSource == null) {
                        dockingSource = toolBar.getParent();
                    }
                    if (handler != null) {
                        UIManager.removePropertyChangeListener(handler);
                    }
                    dockingSource.add(toolBar, constraint.intValue());
                    dockingSource.invalidate();
                    Container dockingSourceParent = dockingSource.getParent();
                    if (dockingSourceParent != null) {
                        dockingSourceParent.validate();
                    }
                    dockingSource.repaint();
                }
            }
        };
    }
}
