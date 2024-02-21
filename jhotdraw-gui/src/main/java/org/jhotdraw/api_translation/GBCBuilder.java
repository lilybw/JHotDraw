package org.jhotdraw.api_translation;

import java.awt.*;

/**
 * GridBagConstraintsBuilder. Use this class for configuration of new GridBagConstraints.
 * Intended to take away a lot of the manual configuration required, as well as providing
 * quality-of-life improvements like supporting method chaining.
 *
 * @author GustavBW
 */
public class GBCBuilder implements IGBCBuilder {

    private Insets insets = new Insets(0,0,0,0);
    private EGBCConstant anchor = EGBCConstant.CENTER;
    private EGBCConstant fill = EGBCConstant.NONE;
    private EGBCConstant gridX = EGBCConstant.RELATIVE;
    private EGBCConstant gridY = EGBCConstant.RELATIVE;
    private double weightX = 0, weightY = 0;


    @Override
    public GridBagConstraints build() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = this.insets;
        gbc.anchor = this.anchor.intVal;
        gbc.fill = this.fill.intVal;
        gbc.gridx = this.gridX.intVal;
        gbc.gridy = this.gridY.intVal;
        gbc.weightx = this.weightX;
        gbc.weighty = this.weightY;
        return gbc;
    }

    @Override
    public IGBCBuilder setGrid(EGBCConstant gridX, EGBCConstant gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        return this;
    }

    @Override
    public IGBCBuilder setGridX(EGBCConstant gridX) {
        this.gridX = gridX;
        return this;
    }

    @Override
    public IGBCBuilder setGridY(EGBCConstant gridY) {
        this.gridY = gridY;
        return this;
    }

    @Override
    public IGBCBuilder setInsets(Insets insets) {
        this.insets = insets;
        return this;
    }

    @Override
    public IGBCBuilder setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top,left,bottom,right);
        return this;
    }

    @Override
    public IGBCBuilder setAnchor(EGBCConstant anchor) {
        this.anchor = anchor;
        return this;
    }

    @Override
    public IGBCBuilder setFillType(EGBCConstant fillType) {
        this.fill = fillType;
        return this;
    }

    @Override
    public IGBCBuilder setWeights(double x, double y) {
        this.weightX = x;
        this.weightY = y;
        return this;
    }

    @Override
    public IGBCBuilder setWeightX(double weight) {
        this.weightX = weight;
        return this;
    }

    @Override
    public IGBCBuilder setWeightY(double weight) {
        this.weightY = weight;
        return this;
    }
}
