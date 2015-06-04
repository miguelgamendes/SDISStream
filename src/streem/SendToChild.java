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
    
    BinaryStreamTreeServer server;
    
    byte[] buffer;
    
    public SendToChild(int port)
    {
        this.port = port;
        buffer = new byte[1500];
        
        try {
            server = new BinaryStreamTreeServer(15001);
        } catch (IOException ex) {
            System.out.println("Failed to create binarystreamtreeserver");
        }
        
    }
    
    @Override
    public void run()
    {
        
        Shared shared = Shared.getInstance();
        
        int size;
        
        while (true)
        {
            if (!shared.empty())
            {
                size = util.arrayCopy2(shared.remove(), buffer);
                server.send(buffer,size);

            }
        }
    }
}
