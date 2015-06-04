import BinaryStreamTree.BinaryStreamTreeNode;
import BinaryStreamTree.BinaryStreamTreePeer;

import java.io.IOException;

/**
 * Created by danfergo on 03-06-2015.
 */
public class TestBSTClient {

    public static void main(String args[]) throws IOException {

        BinaryStreamTreePeer client = new BinaryStreamTreePeer(15003,"localhost:15001");

        while(true){
            System.out.println(client.receive(1)[0]);
        }

    }
}
