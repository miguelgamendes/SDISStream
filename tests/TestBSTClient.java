import BinaryStreamTree.BinaryStreamTreeNode;
import BinaryStreamTree.BinaryStreamTreePeer;

import java.io.IOException;

/**
 * Created by danfergo on 03-06-2015.
 */
public class TestBSTClient {

    public static void main(String args[]) throws IOException {

        BinaryStreamTreePeer client = new BinaryStreamTreePeer("asdads");

        while(true){
            System.out.println(new String(client.receive()));
        }

    }
}
