package BinaryStreamTree;

import HttpSecure.HttpURLSecureConnection;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import javax.sql.rowset.serial.SerialRef;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.URL;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.server.ExportException;
import java.util.Map;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreePeer extends BinaryStreamTreeNode {

    BinaryStreamTreeRemoteUpperNode parent = null;
    BinaryStreamTreeRemoteUpperNode godfather = null;


    public BinaryStreamTreePeer(int BS3PPort, String BS3PParentAddress) throws IOException {
        super(BS3PPort);
        parent = new BinaryStreamTreeRemoteUpperNode(BS3PParentAddress, BS3PPort);
        godfather = parent.connect(BS3PParentAddress, parent.getSocketPort(),"false");
        if(godfather != null) System.out.println("My godfather is "+godfather.getAddress()+":"+godfather.getPort());
        parent.accept();

    }

    @Override
    public void secureHandle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();

        if(olderSon == null){
            httpExchange.getResponseHeaders().add("Godfather", parent.getAddress() + ":" + parent.getPort());
        } else if(youngerSon == null){
            httpExchange.getResponseHeaders().add("Godfather", olderSon.getAddress() + ":" + olderSon.getPort());
        }

        super.secureHandle(httpExchange);

    }

    public void handleDisconnection(){
        parent = godfather;
        String address = parent.getAddress()+":"+parent.getPort();
        try {
            System.out.println("Connecting to godfather");
            godfather = parent.connect(address, parent.getSocketPort(),"true");
            parent.accept();
            System.out.println("Connected to godfather");
        } catch (IOException e) {
            System.out.println("Not connected");
            e.printStackTrace();
        }
    }

    /**
     * Requests video chunks from BinaryStreamTreeRemoteNode
     * parent' socket resend it to it's children and return the upper layer.
     */
    public byte [] receive(int bytes) {
        byte [] data = new byte[0];
        try {
            data = parent.receive(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(data !=null){
            send(data, data.length);
        } else {
            handleDisconnection();
            return "1".getBytes();
        }
        return data;
    }

}
