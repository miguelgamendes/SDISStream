package BinaryStreamTree;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreePeer extends BinaryStreamTreeNode {

    BinaryStreamTreeRemoteNode parent = null;
    BinaryStreamTreeRemoteNode godfather = null;


    public BinaryStreamTreePeer(InetAddress address) {
        //TODO connect to parent. if it fails, throw some crappy exception.
    }

    public BinaryStreamTreePeer(String address) throws UnknownHostException {
        this(InetAddress.getByName(address));
    }

    public void receive(byte [] packet) {
        //TODO receive from parent.
    }

    @Override
    public void disconnect() {

    }
}
