package BinaryStreamTree;

import HttpSecure.HttpSecureServer;
import HttpSecure.HttpURLSecureConnection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.net.www.protocol.http.Handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreeRemoteNode {
    //HttpURLSecureConnection httpConnection = null;
    DataOutputStream outputStream = null;
    Socket outSocket = null;

    BinaryStreamTreeRemoteNode(String address, int port) {
        try {
            outSocket = new Socket(address,port);
            outputStream = new DataOutputStream(outSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public String getAddress(){
        return outSocket.getInetAddress().getHostName();
    }

    public void send(byte[] data) {
        //TODO if exists a socket , send data to socket, else discard it.
    }


    private void connect(){

    }

    /*** NOTIFICATIONS **/
    public void disconnect(){
        //TODO to implement nice exit from the Tree. Send it
    }


}
