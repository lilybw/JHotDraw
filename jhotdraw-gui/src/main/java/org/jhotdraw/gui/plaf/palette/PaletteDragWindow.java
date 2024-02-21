package org.jhotdraw.gui.plaf.palette;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

class PaletteDragWindow extends JWindow {

    private static final long serialVersionUID = 1L;
    private Color borderColor = Color.gray;
    private final JToolBar toolBar;
    private int orientation;
    private Point offset; // offset of the mouse cursor inside the DragWindow

    PaletteDragWindow(Window w, JToolBar toolBar) {
        super(w);
        this.toolBar = toolBar;
        this.orientation = toolBar.getOrientation();
        getContentPane().add(new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                paintDragWindow(g);
            }
        });
    }

    public void setOrientation(int o) {
        if (isShowing()) {
            if (o == this.orientation) {
                return;
            }
            this.orientation = o;
            Dimension size = getSize();
            setSize(new Dimension(size.height, size.width));
            if (offset != null) {
                if (toolBar.getComponentOrientation().isLeftToRight()) {
                    setOffset(new Point(offset.y, offset.x));
                } else if (o == JToolBar.HORIZONTAL) {
                    setOffset(new Point(size.height - offset.y, offset.x));
                } else {
                    setOffset(new Point(offset.y, size.width - offset.x));
                }
            }
            repaint();
        }
    }

    /**
     * Paints the contents of the window used for dragging.
     *
     * @param g Graphics to paint to.
     * @throws NullPointerException is <code>g</code> is null
     * @since 1.5
     */
    protected void paintDragWindow(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, w, h);
        boolean wasDoubleBuffered = false;
        if (toolBar.isDoubleBuffered()) {
            wasDoubleBuffered = true;
            toolBar.setDoubleBuffered(false);
        }
        Graphics g2 = g.create();
        toolBar.paintAll(g2);
        g2.dispose();
        g.setColor(this.getBorderColor());
        g.drawRect(0, 0, w - 1, h - 1);
        if (wasDoubleBuffered) {
            toolBar.setDoubleBuffered(true);
        }
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point p) {
        this.offset = p;
    }

    public void setBorderColor(Color c) {
        if (this.borderColor == c) {
            return;
        }
        this.borderColor = c;
        repaint();
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    @Override
    public Insets getInsets() {
        return new Insets(1, 1, 1, 1);
    }
}
