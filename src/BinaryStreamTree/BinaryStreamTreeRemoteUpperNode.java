package BinaryStreamTree;

import HttpSecure.HttpURLSecureConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by danfergo on 03-06-2015.
 */
public class BinaryStreamTreeRemoteUpperNode extends BinaryStreamTreeRemoteNode{

    ServerSocket socket;
    BufferedReader feed;
    int httpPort;


    BinaryStreamTreeRemoteUpperNode(String address, int httpport) throws IOException {
        super(address, httpport);
        this.httpPort = httpport;
        //HttpURLSecureConnection con = new HttpURLSecureConnection(new URL(address),port); //after this, there is a socket opened in a server peer
        socket = new ServerSocket(0);
        connect(address, socket.getLocalPort());
        Socket conn = socket.accept();
        feed = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    public void connect(String address, int myDataSocketPort) throws IOException {
        URL url;

        boolean sucess = false;
        while(!sucess){
            url = new URL("http://"+address+"/?socket_port="+myDataSocketPort+"&http_port="+httpPort);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();

            int res = con.getResponseCode();
            if(res == 200){
                System.out.println("sucess address "+address);
                sucess = true;
            } else if (res == 300){
               address = con.getHeaderField("Location");
            }

        }


    }


    public byte[] receive() {

        try {
            return feed.readLine().getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
