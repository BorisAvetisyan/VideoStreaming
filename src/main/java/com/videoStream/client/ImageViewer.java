package com.videoStream.client;
import java.awt.*;

class ImageViewer extends Frame {

    Image image;

    ImageViewer(byte[] imb) {
        super("Client Webcam");
        image = getToolkit().createImage(imb);
        Insets insets = getInsets();
        setVisible(true);
        setSize(640,480);
    }

    public void setImage(byte[] imb) {
        image = getToolkit().createImage(imb);
        repaint();
    }

    public void paint(Graphics g) {
        Insets insets = getInsets();
        g.drawImage(image, getInsets().left, getInsets().top, this);
    }
}
