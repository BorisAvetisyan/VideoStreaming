package com.videoStream.server;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
//import com.videostream.server.ImageSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Component
public class Server {

    @PostConstruct()
    public void process() throws IOException {
        try {
            int port = 4445;

            System.out.println(InetAddress.getLocalHost().getHostAddress());
            System.out.println(InetAddress.getLocalHost().getHostName());
            InetAddress address = InetAddress.getByName("224.0.0.0");
            Webcam webCamera = getWebCamera();
            assert webCamera != null;
            WebcamPanel webCamPanel = buildWebcamPanel(webCamera);
            buildCamWindow(webCamPanel);
            DatagramSocket socket = new DatagramSocket(4242);

            while (true) {
                byte[] buf = new byte[100];
                DatagramPacket receiverPacket = new DatagramPacket(buf, buf.length, address, port);
                System.out.println("Waiting for client on port "+ port);

                socket.receive(receiverPacket);
                InetAddress clientAddress = receiverPacket.getAddress();
                int clientPort = receiverPacket.getPort();
                ImageSender imageSender = new ImageSender(clientAddress, clientPort, webCamera);
                imageSender.start();
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public static void buildCamWindow(WebcamPanel webCamPanel) {
        JFrame window = new JFrame("Server Webcam");
        window.add(webCamPanel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

    public static WebcamPanel buildWebcamPanel(Webcam webCamera) {
        webCamera.setViewSize(WebcamResolution.VGA.getSize());
        WebcamPanel webCamPanel = new WebcamPanel(webCamera);
        webCamPanel.setFPSDisplayed(true);
        webCamPanel.setDisplayDebugInfo(true);
        webCamPanel.setImageSizeDisplayed(true);
        webCamPanel.setMirrored(true);
        return webCamPanel;
    }

    public static Webcam getWebCamera() {
        Webcam webCamera = null;
        for (Webcam webcam : Webcam.getWebcams()) {
            if (webcam.getName().indexOf("FaceTime") != -1) {
                webCamera = webcam;
            }
        }
        return webCamera;
    }

}
