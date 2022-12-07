package com.videoStream.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

@Component
public class Client {

    private final Logger logger = LoggerFactory.getLogger(Client.class);

    @PostConstruct
    public void process() throws IOException {
        try {
            String hostName = "127.0.0.1";
            String multicastHost = "224.0.0.0";
            int port = 4242;
            int multicastPort = 4445;

            MulticastSocket socket = new MulticastSocket(multicastPort);
            InetAddress group = InetAddress.getByName(multicastHost);
            socket.joinGroup(group);
            DatagramPacket outPacket, inPacket;

            String message = "Establish Connection";
            byte[] outBuffer = message.getBytes();
            outPacket = new DatagramPacket(outBuffer, outBuffer.length,
                    InetAddress.getByName(hostName), port);
            socket.send(outPacket);
            System.out.println("Package sent to "+hostName+" "+port);
            byte[] bufferStream = new byte[35000];
            inPacket = new DatagramPacket(bufferStream, bufferStream.length, InetAddress.getByName(hostName), port);
            socket.receive(inPacket);
            byte[] imagebytes = inPacket.getData();
            ImageViewer iv = new ImageViewer(imagebytes);

            System.out.println("socket.isConnected() -> " + socket.isConnected());
            while (true) {
                inPacket = new DatagramPacket(bufferStream, bufferStream.length);
                socket.receive(inPacket);
                imagebytes = inPacket.getData();
                iv.setImage(imagebytes);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
