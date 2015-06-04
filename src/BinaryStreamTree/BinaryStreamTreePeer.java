package BinaryStreamTree;

import HttpSecure.HttpURLSecureConnection;
import com.sun.net.httpserver.HttpExchange;

import javax.sql.rowset.serial.SerialRef;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.URL;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreePeer extends BinaryStreamTreeNode {

    BinaryStreamTreeRemoteUpperNode parent = null;
    BinaryStreamTreeRemoteUpperNode godfather = null;


    public BinaryStreamTreePeer(int BS3PPort, String BS3PParentAddress) throws IOException {
        super(BS3PPort);
        parent = new BinaryStreamTreeRemoteUpperNode(BS3PParentAddress, BS3PPort);
    }


    /**
     * Requests video chunks from BinaryStreamTreeRemoteNode
     * parent' socket resend it to it's children and return the upper layer.
     */
    public byte [] receive(int bytes) throws IOException {
        byte [] data = parent.receive(20);
        if(data ==null){
            System.out.println("asdasd");
        }
        send(data);
        return data;
    }

}
