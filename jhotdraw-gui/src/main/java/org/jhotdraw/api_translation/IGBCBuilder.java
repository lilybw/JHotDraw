package org.jhotdraw.api_translation;

import java.awt.*;

/**
 * GridBagConstraintsBuilder interface. Use implementations of this interface for configuration of new GridBagConstraints.
 * Intended to take away a lot of the manual configuration required, as well as providing
 * quality-of-life improvements like supporting method chaining.
 *
 * @author GustavBW
 */
public interface IGBCBuilder extends IBuilder<GridBagConstraints> {

    IGBCBuilder setGrid(EGBCConstant gridX, EGBCConstant gridY);
    IGBCBuilder setGridX(EGBCConstant gridX);
    IGBCBuilder setGridY(EGBCConstant gridY);
    IGBCBuilder setInsets(int top, int left, int bottom, int right);
    IGBCBuilder setAnchor(EGBCConstant anchor);
    IGBCBuilder setFillType(EGBCConstant fillType);
    IGBCBuilder setWeights(double x, double y);
    IGBCBuilder setWeightX(double weight);
    IGBCBuilder setWeightY(double weight);
}
