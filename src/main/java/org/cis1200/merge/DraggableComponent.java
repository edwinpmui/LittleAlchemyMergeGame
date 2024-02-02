
package org.cis1200.merge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.cis1200.merge.Main.*;

public class DraggableComponent extends JComponent {
    public class ImageListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {

        }

        @Override
        public void componentMoved(ComponentEvent e) {
            for (Component i : getBoard().getComponents()) {
                for (Component j : getBoard().getComponents()) {
                    Item obj1 = (Item) i;
                    Item obj2 = (Item) j;
                    obj1.merge(obj2);

                }
            }
            getBoard().repaint();
            Main.getStatus().setText(collectedItems());
        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }

    private boolean draggable = true;
    private Point anchorPoint;
    private Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private boolean overbearing = false;

    public DraggableComponent() {
        addDragListeners();
        setOpaque(true);
        setBackground(new Color(240, 240, 240));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void addDragListeners() {
        final DraggableComponent handle = this;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int anchorX = anchorPoint.x;
                int anchorY = anchorPoint.y;

                Point parentOnScreen = getParent().getLocationOnScreen();
                Point mouseOnScreen = e.getLocationOnScreen();
                Point position = new Point(
                        mouseOnScreen.x - parentOnScreen.x - anchorX,
                        mouseOnScreen.y - parentOnScreen.y - anchorY
                );
                setLocation(position);
                if (overbearing) {
                    getParent().setComponentZOrder(handle, 0);
                    repaint();
                }
            }
        });
    }

    private void removeDragListeners() {
        for (MouseMotionListener listener : this.getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        setCursor(Cursor.getDefaultCursor());
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
        if (draggable) {
            addDragListeners();
        } else {
            removeDragListeners();
        }

    }

    public Cursor getDraggingCursor() {
        return draggingCursor;
    }

    public void setDraggingCursor(Cursor draggingCursor) {
        this.draggingCursor = draggingCursor;
    }

    public boolean isOverbearing() {
        return overbearing;
    }

    public void setOverbearing(boolean overbearing) {
        this.overbearing = overbearing;
    }

    private String collectedItems() {
        if (getDiscoveredCollection().size() >= getElementCollection().size()) {
            return "You discovered all items!";
        } else {
            return getDiscoveredCollection().size() + 4 + "/" + getElementCollection().size()
                    + " Discovered Elements";
        }
    }

}
