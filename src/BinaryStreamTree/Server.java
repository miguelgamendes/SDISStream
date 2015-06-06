package BinaryStreamTree;

import HttpSecure.Encryptor;
import com.sun.net.httpserver.HttpExchange;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by danfergo on 27-05-2015.
 */
public class Server extends Node {

    Encryptor encryptor = new Encryptor();

    public Server(int BS3PPort) throws IOException {
        super(BS3PPort);
        System.out.println("Generating keys");
        encryptor.generateKeys();
        System.out.println("Generated");
        this.key = encryptor.getPublicKey();
        System.out.println(encryptor.getPublicKey());

    }

    public void send(byte [] data, int n) {
        int off = 0, s;
        while (off < n){
            s = n - off > 500 ? 500 : n - off;
            byte [] tmp = new byte[s];
            System.arraycopy(data,off,tmp,0,s);

            byte [] encryptedData = encryptor.authenticateData(tmp,s);
            send(encryptedData, encryptedData.length, false);

            off+= s;
        }


        /**String encKey = new String(key.getEncoded());

       // byte[] publicBytes = Base64.getDecoder().decode();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encKey.getBytes());
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }**/
    }

}
