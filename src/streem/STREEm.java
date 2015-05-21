/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streem;

public class STREEm {

    private static final int SERVER = 1;
    private static final int CLIENT = 2;
    
    private int TYPE = 0;
    
    
    public void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("WRONG NUMBER OF ARGUMENTS\nUSAGE: APP SERVER/CLIENT ReceiverPort SenderPort");
            System.exit(1);
        }
        
        switch(args[0].trim().toUpperCase())
        {
            case "SERVER":
                TYPE = SERVER;
                break;
            case "CLIENT":
                TYPE = CLIENT;
                break;
            default:
                break;
        }
        
        Receiver receiver = new Receiver(Integer.parseInt(args[1]));
        
        
        switch (TYPE)
        {
            case SERVER:
                receiver.start();
                SendToChild sendeToChild = new SendToChild(Integer.parseInt(args[2]));
                sendeToChild.start();
                
                break;
            case CLIENT:
                receiver.start();
                
                SendToPlayer senderPlayer = new SendToPlayer(Integer.parseInt(args[2]));
                senderPlayer.start();

                //SendToChild sender = new SendToChild(Integer.parseInt(args[2]));
                //sender.start();
                
                break;
            default:
                System.out.println("WRONG TYPE");
                System.exit(2);
        }
        
        
    }
    
}
