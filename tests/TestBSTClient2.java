import BinaryStreamTree.BinaryStreamTreePeer;

import java.io.IOException;

/**
 * Created by danfergo on 04-06-2015.
 */
public class TestBSTClient2 {
    public static void main(String args[]) throws IOException {

        BinaryStreamTreePeer client = new BinaryStreamTreePeer(15011,"localhost:15000");

        while(true){
            System.out.println(client.receive(1)[0]);
        }


    }
}
