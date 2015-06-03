package BinaryStreamTree;

import com.sun.net.httpserver.HttpExchange;

import javax.sql.rowset.serial.SerialRef;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreePeer extends BinaryStreamTreeNode {

    BinaryStreamTreeRemoteNode parent = null;
    BinaryStreamTreeRemoteNode godfather = null;
    BufferedReader feed;

    public BinaryStreamTreePeer(InetAddress address) throws IOException {
        super();

        //TODO connect to parent. if it fails, throw some crappy exception. probably implement it in a separated method.
        // = new ServerSocket(6789);
    }

    public BinaryStreamTreePeer(String address) throws IOException {
        this(InetAddress.getByName(address));
    }


    /**
     * Requests video chunks from BinaryStreamTreeRemoteNode
     * parent' socket resend it to it's children and return the upper layer.
     */
    public byte [] receive() throws IOException {
        byte [] data = feed.readLine().getBytes();
        if(olderSon != null) olderSon.send(data);
        if(youngerSon != null) olderSon.send(data);
        return data;
    }

}
