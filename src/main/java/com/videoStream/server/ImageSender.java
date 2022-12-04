package com.videoStream.server;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ImageSender extends Thread {
    InetAddress ip;
    int port;
    private Webcam webcam;

    ImageSender(InetAddress x, int y, Webcam webcam) {
        ip = x; port = y;
        this.webcam = webcam;
    }

    public void run() {
        DatagramSocket socket = null;
        int i = 0;
        try {
            socket = new DatagramSocket();
            while (true) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(webcam.getImage(), "jpg", outputStream);
                outputStream.flush();
                byte[] imageBuffer = outputStream.toByteArray();
                System.out.println(imageBuffer.length);

                DatagramPacket packet = new DatagramPacket(imageBuffer, imageBuffer.length, ip, port);

                System.out.println("waiting for request ....");
                System.out.println("Sending packet to "+ip+" port "+port);
                socket.send(packet);

                try {
                    Thread.currentThread().sleep(10);
                } catch (Exception e) {}

                 // TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
