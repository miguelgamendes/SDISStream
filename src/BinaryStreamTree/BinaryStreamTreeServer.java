package BinaryStreamTree;

import java.net.DatagramPacket;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreeServer extends BinaryStreamTreeNode {

    public BinaryStreamTreeServer(){

    }

    public void send(byte [] data) {
        if(youngerSon != null){
            youngerSon.send(data);
        }

        if(olderSon != null){
            olderSon.send(data);
        }
    }

    @Override
    public void disconnect() {

    }
}
