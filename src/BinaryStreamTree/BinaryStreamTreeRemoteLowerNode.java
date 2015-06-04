package BinaryStreamTree;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by danfergo on 03-06-2015.
 */
public class BinaryStreamTreeRemoteLowerNode  extends BinaryStreamTreeRemoteNode{

    Socket outputSocket;
    DataOutputStream outputStream;

    BinaryStreamTreeRemoteLowerNode(String address, int httpport, int port) throws IOException {
        super(address, httpport);
        outputSocket = new Socket(address,port);
        outputStream = new DataOutputStream(outputSocket.getOutputStream());
        System.out.println("address!"+address+"!httpport!"+httpport+"!port!"+port);
    }
    //TODO connects?
   /*
    BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
    Socket clientSocket = new Socket("localhost", 6789);
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    outToServer.writeBytes(sentence + '\n');
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);
    clientSocket.close(); */



    public void send(byte[] data, int n) throws IOException {
        try{
            outputStream.write(data, 0, n);
            outputStream.flush();
        } catch (Exception e){
            System.out.println("disconnected");
        }

    }

}
