package org.jhotdraw.gui.plaf.palette;

import org.jhotdraw.gui.plaf.palette.PaletteHandler;
import org.jhotdraw.gui.plaf.palette.IPaletteToolBarUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PaletteHandlerTest {

    @Mock
    private IPaletteToolBarUI paletteToolBarUI;

    @Mock
    private JToolBar toolBar;

    @Mock
    private Component component;

    private PaletteHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new PaletteHandler(paletteToolBarUI);
        handler.setToolBar(toolBar);
        when(toolBar.getComponents()).thenReturn(new Component[0]); // Return an empty array
        when(toolBar.getLocationOnScreen()).thenReturn(new Point(0,0));
        when(toolBar.getWidth()).thenReturn(10);
        when(toolBar.getHeight()).thenReturn(10);
        when(toolBar.getInsets()).thenReturn(new Insets(0, 0, 0, 0));
    }

    @Test
    void testComponentAdded() {
        ContainerEvent evt = new ContainerEvent(toolBar, ContainerEvent.COMPONENT_ADDED, component);

        handler.componentAdded(evt);

        verify(component).addFocusListener(handler);
        verify(paletteToolBarUI, atLeastOnce()).isRolloverBorders();
    }

    @Test
    void testComponentRemoved() {
        ContainerEvent evt = new ContainerEvent(toolBar, ContainerEvent.COMPONENT_REMOVED, component);

        handler.componentRemoved(evt);

        verify(component).removeFocusListener(handler);
        verify(paletteToolBarUI).setBorderToNormal(component);
    }

    @Test
    void testFocusGained() {
        FocusEvent evt = new FocusEvent(component, FocusEvent.FOCUS_GAINED);

        handler.focusGained(evt);

        verify(paletteToolBarUI).setFocusedCompIndex(anyInt());
    }

    @Test
    void testMousePressed() {
        MouseEvent evt = new MouseEvent(toolBar, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0,
                0, 0, 1, false);

        handler.mousePressed(evt);

        assertFalse(handler.isArmed());
        assertFalse(handler.isDragging());
    }

    @Test
    void testPropertyChange() {
        PropertyChangeEvent evt = new PropertyChangeEvent(toolBar, "lookAndFeel", JToolBar.HORIZONTAL, JToolBar.VERTICAL);

        handler.propertyChange(evt);

        // Verify that orientation change triggers a UI update or any other specific action
        verify(toolBar, atLeastOnce()).updateUI();
    }


}
