package org.jhotdraw.api_translation;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Use this class for configuration of new instances of JButton.
 * Intended to take away a lot of the manual configuration required, as well as providing
 * quality-of-life improvements like supporting method chaining.
 *
 * @author GustavBW
 */
public class JButtonBuilder implements IJButtonBuilder {

    private record KVProperty(Object key, Object value){}

    private final List<KVProperty> properties = new ArrayList<>();
    private final List<ActionListener> listeners = new ArrayList<>();

    private Icon icon;
    private ButtonUI ui;
    private boolean isOpaque = false;
    private boolean isBorderPainted = false;

    @Override
    public JButton build() {
        JButton btn = new JButton();
        for(KVProperty kvPair : properties){
            btn.putClientProperty(
                    kvPair.key(), kvPair.value()
            );
        }
        for(ActionListener listener : listeners){
            btn.addActionListener(listener);
        }
        btn.setOpaque(isOpaque);
        btn.setBorderPainted(isBorderPainted);
        if(icon != null){
            btn.setIcon(icon);
        }
        if(ui != null){
            btn.setUI(ui);
        }
        return btn;
    }

    @Override
    public IJButtonBuilder setUI(ButtonUI ui) {
        this.ui = ui;
        return this;
    }

    @Override
    public IJButtonBuilder setBorderPainted(boolean state) {
        this.isBorderPainted = state;
        return this;
    }

    @Override
    public IJButtonBuilder setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public IJButtonBuilder setOpaque(boolean state) {
        this.isOpaque = state;
        return this;
    }

    @Override
    public IJButtonBuilder addProperty(Object key, Object value) {
        properties.add(new KVProperty(key, value));
        return this;
    }

    @Override
    public IJButtonBuilder addProperties(Object... keyValuePairs) {
        //Guard statement. The only case where the code below will fail.
        if(keyValuePairs.length < 2) return this;

        //Implicitly ignores the last element for an array of uneven length.
        for(int i = 0; i < keyValuePairs.length - 1; i += 2){
            properties.add(new KVProperty(keyValuePairs[i], keyValuePairs[i + 1]));
        }
        return this;
    }

    @Override
    public IJButtonBuilder addOnAction(ActionListener listener) {
        listeners.add(listener);
        return this;
    }
}
