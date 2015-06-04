/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import BinaryStreamTree.BinaryStreamTreeServer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static java.net.InetAddress.getByName;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SendToChild extends Thread
{

    private int port;
    private InetAddress address;
    
    DatagramPacket packet;
    DatagramSocket socket;

    BinaryStreamTreeServer server;
    
    byte[] buffer;
    
    public SendToChild(int port)
    {
        this.port = port;
        buffer = new byte[1500];
        
        try {
            server = new BinaryStreamTreeServer(15001);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to create binarystreamtreeserver");
        }
        
        try
        {
            address = getByName("192.168.109.218");
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
                size = util.arrayCopy2(shared.remove(), buffer);
                server.send(buffer);
                
                
                //Deprecated
                /*packet = new DatagramPacket(buffer, size, address, port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    System.out.println("FAILED TO SEND TO SOCKET");
                    System.exit(21);
                }*/
            }
        }
    }
}
