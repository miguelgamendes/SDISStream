/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import BinaryStreamTree.BinaryStreamTreePeer;
import java.io.IOException;

public class ReceiveFromParent extends Thread
{
    
    private int port;
    
    byte[] buffer;
    
    BinaryStreamTreePeer receiver;
    
    public ReceiveFromParent(int port)
    {
        this.port = port;
        buffer = new byte[1500];
        try {
            receiver = new BinaryStreamTreePeer(15003,"localhost:15001");
        } catch (IOException ex) {
            System.out.println("Failed to create BinaryStreamTreePeer");
        }
    }
    
    @Override
    public void run()
    {
        Shared shared = Shared.getInstance();
        
        while (true)
        {
           try {
                buffer = receiver.receive(1500);
                byte[] temp = new byte[util.getLenght(buffer) + 1];
                util.arrayCopy1(buffer, temp);
                shared.add(temp);
            } catch (IOException ex) {
                System.out.println("Failed to receive");
            }
        }
    }
}
