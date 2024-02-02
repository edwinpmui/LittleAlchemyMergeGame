package org.cis1200.merge;

import java.awt.*;
import java.awt.image.ImageObserver;

public class DraggableImageComponent extends DraggableComponent implements ImageObserver {

    private Image image;
    private boolean autoSize = false;
    private Dimension autoSizeDimension = new Dimension(0, 0);

    public DraggableImageComponent() {
        super();
        setLayout(null);
        setBackground(Color.black);
        ImageListener imgListener = new ImageListener();
        addComponentListener(imgListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, getWidth(), getHeight());
        if (image != null) {
            setAutoSizeDimension();
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private Dimension adaptDimension(Dimension source, Dimension dest) {
        int sW = source.width;
        int sH = source.height;
        int dW = dest.width;
        int dH = dest.height;
        double ratio = ((double) sW) / ((double) sH);
        if (sW >= sH) {
            sW = dW;
            sH = (int) (sW / ratio);
        } else {
            sH = dH;
            sW = (int) (sH * ratio);
        }
        return new Dimension(sW, sH);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
        if (infoflags == ALLBITS) {
            repaint();
            setAutoSizeDimension();
            return false;
        }
        return true;
    }

    private void setAutoSizeDimension() {
        if (!autoSize) {
            return;
        }
        if (image != null) {
            if (image.getHeight(null) == 0 || getHeight() == 0) {
                return;
            }
            if ((getWidth() / getHeight()) == (image.getWidth(null) / (image.getHeight(null)))) {
                return;
            }
            autoSizeDimension = adaptDimension(
                    new Dimension(image.getWidth(null), image.getHeight(null)), this.getSize()
            );
            setSize(autoSizeDimension.width, autoSizeDimension.height);
        }
    }

    public void grow(int pixels) {
        double ratio = getWidth() / getHeight();
        setSize(getSize().width + pixels, (int) (getSize().height + (pixels / ratio)));
    }

    public boolean isAutoSize() {
        return autoSize;
    }

    public void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String image) {
        setImage(Toolkit.getDefaultToolkit().getImage(image));
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
        setAutoSizeDimension();
    }

}
