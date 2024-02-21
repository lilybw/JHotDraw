package org.jhotdraw.api_translation;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;

import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;

import java.awt.event.ActionListener;

/**
 * JButtonBuilder interface. Use implementations of this interface for configuration of new instances of JButton.
 * Intended to take away a lot of the manual configuration required, as well as providing
 * quality-of-life improvements like supporting method chaining.
 *
 * @author GustavBW
 */
public interface IJButtonBuilder extends IBuilder<JButton> {
    /**
     * Sets the L&F object that renders this component.
     * @return this
     */
    IJButtonBuilder setUI(ButtonUI ui);

    /**
     * Sets the borderPainted property. If true and the button has a border, the border is painted. The default value for the borderPainted property is true.
     * Some look and feels might not support the borderPainted property, in which case they ignore this.
     * @return this
     */
    IJButtonBuilder setBorderPainted(boolean state);

    /**
     * Sets the button's default icon. This icon is also used as the "pressed" and "disabled" icon if there is no explicitly set pressed icon.
     * @return this
     */
    IJButtonBuilder setIcon(Icon icon);

    /**
     * If true the component paints every pixel within its bounds. Otherwise, the component may not paint some or all of its pixels, allowing the underlying pixels to show through.
     * The default value of this property is false for JComponent. However, the default value for this property on most standard JComponent subclasses (such as JButton and JTree) is look-and-feel dependent.
     * @return this
     */
    IJButtonBuilder setOpaque(boolean state);

    /**
     * Adds an arbitrary key/value "client property" to this component.
     * The get/putClientProperty methods provide access to a small per-instance hashtable. Callers can use get/putClientProperty to annotate components that were created by another module. For example, a layout manager might store per child constraints this way. For example: <br/><br/>
     * componentA.putClientProperty("to the left of", componentB); <br/><br/>
     * If value is null this method will remove the property. Changes to client properties are reported with PropertyChange events. The name of the property (for the sake of PropertyChange events) is key.toString().
     * The clientProperty dictionary is not intended to support large scale extensions to JComponent nor should be it considered an alternative to subclassing when designing a new component
     * @return this
     */
    IJButtonBuilder addProperty(Object key, Object value);

    /**
     * See IJButtonBuilder.addProperty(...).
     * This version takes any amount of pairs.
     * If the given number of objects is uneven, the last object is ignored.
     * @return this
     */
    IJButtonBuilder addProperties(Object... keyValuePairs);

    /**
     * Adds an ActionListener to the button.
     * @return this
     */
    IJButtonBuilder addOnAction(ActionListener listener);


}
