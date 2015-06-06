package BinaryStreamTree.remote;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by danfergo on 03-06-2015.
 */
public class LowerNode extends RemoteNode {

    Socket outputSocket;
    DataOutputStream outputStream;
    //SecureDataOutputStream outputStream;

    public LowerNode(String address, int httpport, int port) throws IOException {
        super(address, httpport);
        outputSocket = new Socket(address,port);
        outputStream = new DataOutputStream(outputSocket.getOutputStream());
        //outputStream = new SecureDataOutputStream(outputSocket.getOutputStream());
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
        outputStream.write(data, 0, n);
        outputStream.flush();
    }

}
