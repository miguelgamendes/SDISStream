/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Shared
{
    private static Shared instance = null;
    
    protected ConcurrentLinkedQueue<byte[]> queueReceiver = new ConcurrentLinkedQueue<byte[]>();
    
    private Shared()
    {
    }
    
    public static synchronized Shared getInstance()
    {
        if (instance == null)
        {
            instance = new Shared();
        }
        return instance;
    }
    
    protected synchronized void add(byte[] data)
    {
        queueReceiver.add(data);
    }
    
    protected synchronized byte[] remove()
    {
        return queueReceiver.poll();
    }
    
    protected boolean empty()
    {
        return queueReceiver.isEmpty();
    }
    
}
