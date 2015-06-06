package HttpSecure;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class Encryptor {
    private KeyPair keyPair = new KeyPair(null,null);
    //private Key otherPublicKey;
    private PublicKey publicKey;

    public Encryptor(){

    }

    public Encryptor(PublicKey key){
            publicKey =key;
    }

    public void generateKeys(){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(4096);
            this.keyPair = kpg.genKeyPair();
            this.publicKey = this.keyPair.getPublic();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey(){
        return this.publicKey;
    }

    public void setPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }

    public byte[] authenticateData(byte[] data,int size) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.keyPair.getPrivate());
            return cipher.doFinal(data,0,size); // também pode ter um offset e tamanho
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    byte[] validateData(byte[] data){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, this.publicKey);
            return cipher.doFinal(data); // também pode ter um offset e tamanho
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
