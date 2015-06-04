/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class ReceiveFromPlayer extends Thread
{
    
    private int port;
    
    DatagramPacket packet;
    DatagramSocket socket;
    
    byte[] buffer;
    
    public ReceiveFromPlayer(int port)
    {
        this.port = port;
        buffer = new byte[1500];
    }
    
    @Override
    public void run()
    {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.out.println("FAILED TO CREATE SOCKET");
            System.exit(10);
        }
        
        packet = new DatagramPacket(buffer, buffer.length);
        
        Shared shared = Shared.getInstance();
        
        while (true)
        {
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                System.out.println("FAILED TO RECEIVE FROM SOCKET");
                System.exit(11);
            }

            byte[] temp = new byte[packet.getLength()];
            util.arrayCopy1(packet.getData(), temp);
            shared.add(temp);
        }
    }
}
