/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import BinaryStreamTree.AbortedConnectionException;
import BinaryStreamTree.Peer;

import java.io.IOException;

public class ReceiveFromParent extends Thread
{
    
    private int port;
    
    byte[] buffer;
    
    Peer receiver;
    
    public ReceiveFromParent(int port, String address)
    {
        this.port = port;
        //buffer = new byte[1500];
        try {
            receiver = new Peer(this.port, address);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to create Peer");
            System.exit(10);
        }
    }
    
    @Override
    public void run()
    {
        Shared shared = Shared.getInstance();
        
        while (true)
        {
           try {
                byte[] temp = receiver.receive(1328);
                shared.add(temp);
            } catch (AbortedConnectionException ex) {
                System.out.println("Failed to receive");
                System.exit(11);
            }
        }
    }
}
