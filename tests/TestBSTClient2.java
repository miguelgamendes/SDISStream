import BinaryStreamTree.BinaryStreamTreePeer;

import java.io.IOException;

/**
 * Created by danfergo on 04-06-2015.
 */
public class TestBSTClient2 {
    public static void main(String args[]) throws IOException {

        BinaryStreamTreePeer client = new BinaryStreamTreePeer(15004,"localhost:15001");

        while(true){
            System.out.println(new String(client.receive()));
        }

    }
}