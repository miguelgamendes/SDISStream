package BinaryStreamTree;

import HttpSecure.HttpURLSecureConnection;

import java.io.*;
import java.net.*;

/**
 * Created by danfergo on 03-06-2015.
 */
public class BinaryStreamTreeRemoteUpperNode extends BinaryStreamTreeRemoteNode{

    ServerSocket socket;
    DataInputStream feed;
    int httpPort;


    BinaryStreamTreeRemoteUpperNode(String address, int httpport) throws IOException {
        super(address, httpport);
        this.httpPort = httpport;
        //HttpURLSecureConnection con = new HttpURLSecureConnection(new URL(address),port); //after this, there is a socket opened in a server peer
        socket = new ServerSocket(0);
        connect(address, socket.getLocalPort());
        Socket conn = socket.accept();

       // System.out.println("merdas recebidas a serio 1 "+conn.getReceiveBufferSize());
       // System.out.println("merdas recebidas a serio 2 "+conn.getSendBufferSize());
        feed = new DataInputStream(conn.getInputStream());
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


    public byte[] receive(int bytes) {
        byte buf[]  = new byte[bytes];
        int missingBytes = bytes, n;
        try {
            do {
                n = feed.read(buf,bytes-missingBytes,missingBytes);
                if(n == 0){
                    byte reBuf[] = new byte[bytes - missingBytes];
                    System.arraycopy(buf,0,reBuf,0,bytes - missingBytes);
                }
                missingBytes -=  n;
            }while(missingBytes > 0);

            return buf;

         } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}
