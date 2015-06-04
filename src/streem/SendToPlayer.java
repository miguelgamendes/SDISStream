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


public class SendToPlayer extends Thread
{

    private int port;
    private InetAddress address;
    
    DatagramPacket packet;
    DatagramSocket socket;
    
    byte[] buffer;
    
    public SendToPlayer(int port)
    {
        this.port = port;
        //buffer = new byte[1500];
        
        try
        {
            address = getByName("localhost");
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
            System.out.println("FAILED TO CREATE SOCKET");
            System.exit(10);
        }
        
        Shared shared = Shared.getInstance();
        
        int size;
        
        while (true)
        {
            if (!shared.empty())
            {
                buffer = shared.remove();
                //size = util.arrayCopy2(shared.remove(), buffer);
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    System.out.println("FAILED TO SEND TO SOCKET");
                    System.exit(21);
                }
            }
        }
    }
}
