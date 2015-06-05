package BinaryStreamTree.remote;

import java.io.*;
import java.net.*;

/**
 * Created by danfergo on 03-06-2015.
 */
public class UpperNode extends RemoteNode {

    ServerSocket socket;
    DataInputStream feed;
    int httpPort;

    public int getHttpPort(){
        return httpPort;
    }

    public UpperNode(String address, int httpport) throws IOException {

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

    public UpperNode connect(String address, int myDataSocketPort) throws IOException {
        URL url;
        UpperNode godfather = null;
        try{
            url = new URL("http://"+address+"/connect/?socket_port="+myDataSocketPort+"&http_port="+httpPort);
            System.out.println("Try address: "+url);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
            con.connect();

            System.out.println("Success address: "+con.getURL());

            String gf = con.getHeaderField("Godfather");
            if(gf != null){
                String add = gf.split(":")[0];
                int port = Integer.parseInt(gf.split(":")[1]);
                godfather = new UpperNode(add,port);
                System.out.println("My godfather is: "+godfather.getAddress()+":"+godfather.getPort());
            } else {
                System.out.println("I have no godfather.");
            }
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
            if(n == -1) {
                if(missingBytes == bytes) return null;
                byte reBuf[] = new byte[bytes - missingBytes];
                System.arraycopy(buf,0,reBuf,0,bytes - missingBytes);
            }
            missingBytes -=  n;
        }while(missingBytes > 0);

        return buf;


    }
}
