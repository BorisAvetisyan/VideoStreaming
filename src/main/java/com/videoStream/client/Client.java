package com.videoStream.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Component
public class Client {

    private final Logger logger = LoggerFactory.getLogger(Client.class);

    @PostConstruct
    public void process() throws IOException {
        try {
            String hostName = "localhost";
            int port = 4242;

            DatagramSocket socket = new DatagramSocket();
            DatagramPacket outPacket, inPacket;

            String message = "Establish Connection";
            byte[] outBuffer = message.getBytes();
            outPacket = new DatagramPacket(outBuffer, outBuffer.length,
                    InetAddress.getByName(hostName), port);
            socket.send(outPacket);
            System.out.println("Package sent to "+hostName+" "+port);

            byte[] bufferStream = new byte[28000];
            inPacket = new DatagramPacket(bufferStream, bufferStream.length, InetAddress.getByName(hostName), port);
            socket.receive(inPacket);
            bufferStream = new byte[inPacket.getLength()];
            byte[] imagebytes = inPacket.getData();
            ImageViewer iv = new ImageViewer(imagebytes);

            while (true) {
                System.out.println("Length is from client side is " + inPacket.getLength());

                inPacket = new DatagramPacket(bufferStream, bufferStream.length);
                socket.receive(inPacket);
                System.out.println("received");
                imagebytes = inPacket.getData();
                iv.setImage(imagebytes);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
