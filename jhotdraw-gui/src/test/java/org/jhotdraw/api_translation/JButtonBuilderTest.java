package org.jhotdraw.api_translation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class JButtonBuilderTest {

    private IJButtonBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new JButtonBuilder();
    }

    @Test
    void testBuildDefaultButton() {
        JButton button = builder.build();
        assertNotNull(button, "Button should not be null");
        assertFalse(button.isOpaque(), "Default should be non-opaque");
        assertFalse(button.isBorderPainted(), "Default should not paint border");
    }

    @Test
    void testSetUI() {
        ButtonUI ui = new MetalButtonUI();
        JButton button = builder.setUI(ui).build();
        assertEquals(ui, button.getUI(), "UI should match the one set");
    }

    @Test
    void testSetBorderPainted() {
        JButton button = builder.setBorderPainted(true).build();
        assertTrue(button.isBorderPainted(), "Border painted should be true");
    }

    @Test
    void testSetIcon() {
        Icon icon = new ImageIcon();
        JButton button = builder.setIcon(icon).build();
        assertEquals(icon, button.getIcon(), "Icon should match the one set");
    }

    @Test
    void testSetOpaque() {
        JButton button = builder.setOpaque(true).build();
        assertTrue(button.isOpaque(), "Opaque should be true");
    }

    @Test
    void testAddProperty() {
        Object key = "key";
        Object value = "value";
        JButton button = builder.addProperty(key, value).build();
        assertEquals(value, button.getClientProperty(key), "Property value should match the one set");
    }

    @Test
    void testAddProperties() {
        JButton button = builder.addProperties("key1", "value1", "key2", "value2").build();
        assertEquals("value1", button.getClientProperty("key1"), "Property value1 should match");
        assertEquals("value2", button.getClientProperty("key2"), "Property value2 should match");
    }

    @Test
    void testAddActionListener() {
        final boolean[] actionFired = {false};
        ActionListener listener = e -> actionFired[0] = true;
        JButton button = builder.addOnAction(listener).build();
        for (ActionListener l : button.getActionListeners()) {
            l.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        assertTrue(actionFired[0], "Action listener should fire");
    }

    @Test
    void testActionListenerFireCount(){
        final int[] fireCount = {0};
        ActionListener listener = e -> fireCount[0]++;
        JButton button = builder.addOnAction(listener).build();
        for (ActionListener l : button.getActionListeners()) {
            l.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        assertEquals(fireCount[0], 1, "Expect the action listener to only fire once");
    }
}