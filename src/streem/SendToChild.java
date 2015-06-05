/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import BinaryStreamTree.Server;

import java.io.IOException;


public class SendToChild extends Thread
{
    private int port;
    
    Server server;
    
    byte[] buffer;
    
    public SendToChild(int port)
    {
        this.port = port;
        buffer = new byte[1500];
        
        try {
            server = new Server(this.port);
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
