package org.jhotdraw.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class JDisclosureToolBarTest {

    private JDisclosureToolBar toolBar;

    @BeforeEach
    void setUp() {
        // Initialize the toolbar before each test
        toolBar = new JDisclosureToolBar();
    }

    @Test
    void testDisclosureStateCount() {
        // Default state count should be 2
        assertEquals(2, toolBar.getDisclosureStateCount(), "Default disclosure state count should be 2.");

        // Change state count
        toolBar.setDisclosureStateCount(3);
        assertEquals(3, toolBar.getDisclosureStateCount(), "Disclosure state count should be updated to 3.");
    }

    @Test
    void testDisclosureState() {
        // Default state should be 1
        assertEquals(1, toolBar.getDisclosureState(), "Default disclosure state should be 1.");

        // Change disclosure state
        toolBar.setDisclosureState(2);
        assertEquals(2, toolBar.getDisclosureState(), "Disclosure state should be updated to 2.");

        // Verify component change upon state change
        JComponent component = toolBar.getDisclosedComponent(toolBar.getDisclosureState());
        assertNotNull(component, "Disclosed component should not be null after state change.");
        assertTrue(component instanceof JLabel, "Disclosed component should be a JLabel.");
        assertEquals("2", ((JLabel) component).getText(), "Label text should match the disclosure state.");
    }

    @Test
    void testComponentReconfigurationOnStateChange() {
        // Assuming the initial state is 1, change to state 2
        toolBar.setDisclosureState(2);

        // Verify the toolbar now contains the component for state 2
        assertEquals(2, toolBar.getComponentCount(), "Toolbar should contain two components after state change.");
        assertTrue(toolBar.getComponent(0) instanceof JLabel, "First component should be a JLabel.");
        assertEquals("2", ((JLabel) toolBar.getComponent(0)).getText(), "Label text should match the disclosure state.");
    }

    @Test
    void bddShowHide(){
        //Given I click the collapse button
        JButton disclosureButton = toolBar.getDisclosureButton();
        int initialDisclosureState = toolBar.getDisclosureState();
        //When I'm hovering over a toolbar section
        for (ActionListener actionListener : disclosureButton.getActionListeners()) {
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        //Then that section should get minimized
        int resultingDisclosureState = toolBar.getDisclosureState();
        assertNotEquals(initialDisclosureState, resultingDisclosureState);
    }
}