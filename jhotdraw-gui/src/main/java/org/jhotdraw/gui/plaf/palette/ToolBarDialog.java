package org.jhotdraw.gui.plaf.palette;

import javax.swing.*;
import java.awt.*;

class ToolBarDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public ToolBarDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public ToolBarDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    // Override createRootPane() to automatically resize
    // the frame when contents change
    @Override
    protected JRootPane createRootPane() {
        JRootPane rootPane = new JRootPane() {
            private static final long serialVersionUID = 1L;
            private boolean packing = false;

            @Override
            public void validate() {
                super.validate();
                if (!packing) {
                    packing = true;
                    pack();
                    packing = false;
                }
            }
        };
        rootPane.setOpaque(true);
        return rootPane;
    }
}
