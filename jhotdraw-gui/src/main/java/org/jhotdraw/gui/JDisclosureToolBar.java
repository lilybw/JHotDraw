/**
 * @(#)JDisclosureToolBar.java
 *
 * Copyright (c) 2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.gui;

import java.awt.*;
import javax.swing.*;

import org.jhotdraw.api_translation.EGBCConstant;
import org.jhotdraw.api_translation.GBCBuilder;
import org.jhotdraw.api_translation.IGBCBuilder;
import org.jhotdraw.api_translation.JButtonBuilder;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.gui.plaf.palette.PaletteProperty;
import org.jhotdraw.gui.plaf.palette.PaletteToolBarUI;

/**
 * A ToolBar with disclosure functionality.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class JDisclosureToolBar extends JToolBar {

    private static final long serialVersionUID = 1L;
    private JButton disclosureButton;

    /**
     * Creates new form.
     */
    public JDisclosureToolBar() {
        setUI(PaletteToolBarUI.createUI(this));
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        if(disclosureButton == null) {
            disclosureButton = createDisclosureButton();
        }
        add(disclosureButton, createConstraintsForDisclosureButton());
        initProperties();
    }

    private void initProperties(){
        putClientProperty(PaletteProperty.TOOLBAR_INSETS_OVERRIDE_PROPERTY.strVal, new Insets(0, 0, 0, 0));
        putClientProperty(PaletteProperty.TOOLBAR_ICON_PROPERTY.strVal, new EmptyIcon(10, 8));
    }

    private GridBagConstraints createConstraintsForDisclosureButton(){
        return new GBCBuilder()
                .setGridX(EGBCConstant.NONE)
                .setInsets(0,1,0,1)
                .setAnchor(EGBCConstant.SOUTHWEST)
                .setFillType(EGBCConstant.NONE)
                .setWeights(1,1)
                .build();
    }

    private JButton createDisclosureButton(){
        return new JButtonBuilder()
                .setUI((PaletteButtonUI) PaletteButtonUI.createUI(disclosureButton))
                .setBorderPainted(false)
                .setIcon(new DisclosureIcon())
                .setOpaque(false)
                .addProperties(
                        DisclosureIcon.CURRENT_STATE_PROPERTY, 1,
                        DisclosureIcon.STATE_COUNT_PROPERTY, 2
                )
                .addOnAction(e -> {
                    int newState = ((Integer) disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY) + 1)
                            % (Integer) disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
                    setDisclosureState(newState);
                })
                .build();
    }

    public void setDisclosureStateCount(int newValue) {
        int oldValue = getDisclosureStateCount();
        disclosureButton.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, newValue);
        firePropertyChange(JDisclosureProperty.DISCLOSURE_STATE_COUNT.strVal, oldValue, newValue);
    }

    public void setDisclosureState(int newValue) {
        int oldValue = getDisclosureState();
        disclosureButton.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, newValue);
        removeAll();
        JComponent disclosedComponent = getDisclosedComponent(newValue);
        if(disclosedComponent != null){
            add(disclosedComponent, getConstraintForDisclosedComponents());
        }
        add(disclosureButton, getButtonConstraints(disclosedComponent != null));
        invalidateAndRepaint();
        firePropertyChange(JDisclosureProperty.DISCLOSURE_STATE.strVal, oldValue, newValue);
    }

    private GridBagConstraints getButtonConstraints(boolean disclosedComponentExists){
        IGBCBuilder builder = new GBCBuilder()
                .setFillType(EGBCConstant.NONE)
                .setAnchor(EGBCConstant.SOUTHWEST)
                .setInsets(0, 1, 0, 1);
        if(disclosedComponentExists){
            builder.setGridX(EGBCConstant.BOTH)
                    .setWeights(1, 1);
        }else{
            builder.setGridX(EGBCConstant.NONE)
                    .setWeights(0 , 1);
        }
        return builder.build();
    }

    private GridBagConstraints getConstraintForDisclosedComponents() {
        return new GBCBuilder()
                .setGridX(EGBCConstant.BOTH)
                .setWeights(1, 1)
                .setFillType(EGBCConstant.BOTH)
                .setAnchor(EGBCConstant.WEST)
                .build();
    }

    private void invalidateAndRepaint() {
        invalidate();
        seekRootParent().validate();
        repaint();
    }

    private Container seekRootParent() {
        Container parent = getParent();
        while (parent.getParent() != null && !parent.getParent().isValid()) {
            parent = parent.getParent();
        }
        return parent;
    }

    public int getDisclosureStateCount() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
        return (value == null) ? 2 : value;
    }

    public int getDisclosureState() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY);
        return (value == null) ? 1 : value;
    }

    protected JComponent getDisclosedComponent(int state) {
        return new JLabel(Integer.toString(state));
    }
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * /
       // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
