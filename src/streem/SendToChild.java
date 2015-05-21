/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static java.net.InetAddress.getByName;
import java.net.SocketException;

/**
 *
 * @author tiago
 */
public class SendToChild extends Thread
{

    private int port;
    private InetAddress address;
    
    DatagramPacket packet;
    DatagramSocket socket;
    
    byte[] buffer;
    
    public SendToChild(int port)
    {
        this.port = port;
        buffer = new byte[1500];
        
        try
        {
            address = getByName("192.168.2.108");
        } catch (Exception ex) {
            System.out.println("Host name failed");
            System.exit(20);
        }
        
    }
    
    @Override
    public void run()
    {
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            System.out.println("FAILLED TO CREATE SOCKET");
            System.exit(10);
        }
        
        Shared shared = Shared.getInstance();
        
        while (true)
        {
            if (shared.queueReceiver.size() > 0)
            {
                packet = new DatagramPacket(shared.qeueReceiver.peek(), shared.qeueReceiver.peek().length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    System.out.println("FAILLED TO SEND TO SOCKET");
                    System.exit(21);
                }
                shared.queueReceiver.remove();
            }
        }
    }
}
