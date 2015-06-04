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

    public int getHttpPort(){
        return httpPort;
    }

    BinaryStreamTreeRemoteUpperNode(String address, int httpport) throws IOException {

        super(address, httpport);
        this.httpPort = httpport;
        socket = new ServerSocket(0);
    }

    public int getSocketPort(){

       return socket.getLocalPort();
    }

    public void accept() throws IOException {

        Socket conn = socket.accept();
        feed = new DataInputStream(conn.getInputStream());
    }

    public BinaryStreamTreeRemoteUpperNode connect(String address, int myDataSocketPort, String reconn) throws IOException {
        URL url;
        BinaryStreamTreeRemoteUpperNode godfather = null;
        try{
            url = new URL("http://"+address+"/?socket_port="+myDataSocketPort+"&http_port="+httpPort+"&reconn="+reconn);
            System.out.println("try address "+url);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();


            String gf = con.getHeaderField("Godfather");
            if(gf != null){
                String add = gf.split(":")[0];
                int port = Integer.parseInt(gf.split(":")[1]);
                godfather = new BinaryStreamTreeRemoteUpperNode(add,port);
            } else {
                System.out.println("godfather is null");
            }
            System.out.println("sucess address "+con.getURL());
        } catch (Exception e){
            e.printStackTrace();
        }

        return godfather;
    }


    public byte[] receive(int bytes) throws IOException{

        byte buf[]  = new byte[bytes];
        int missingBytes = bytes, n;

        do {
            n = feed.read(buf,bytes-missingBytes,missingBytes);
            if(n == -1) return null;
            if(n == 0){
                byte reBuf[] = new byte[bytes - missingBytes];
                System.arraycopy(buf,0,reBuf,0,bytes - missingBytes);
            }
            missingBytes -=  n;
        }while(missingBytes > 0);

        return buf;


    }
}
