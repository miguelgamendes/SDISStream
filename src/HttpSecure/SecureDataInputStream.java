package HttpSecure;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by danfergo on 05-06-2015.
 */
public class SecureDataInputStream extends DataInputStream {
    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    byte decryptedWaste[] = new byte[0];
    byte encryptedWaste[] = new byte[0];
    volatile boolean isClosed = false;
    volatile protected ConcurrentLinkedQueue<byte[]> queue = new ConcurrentLinkedQueue<byte[]>();

    public SecureDataInputStream(InputStream in) {
        super(in);
    }

    public final int readEncrypted(byte b[], int off, int len) throws IOException {
        int r = read(b,off,len);
        if(r != -1) {
            byte[] tempEncryptedSegment = new byte[r + encryptedWaste.length];
            System.arraycopy(encryptedWaste, 0, tempEncryptedSegment, 0, encryptedWaste.length);
            System.arraycopy(b, 0, tempEncryptedSegment, encryptedWaste.length, r);

            encryptedWaste = tempEncryptedSegment;

            while (encryptedWaste.length >= 512) {
                queue.add(Arrays.copyOfRange(encryptedWaste, 0, 512));
                encryptedWaste = Arrays.copyOfRange(encryptedWaste, 512, encryptedWaste.length);
            }
        }else isClosed = true;
        return r;
    }

    public final int readDecrypted(byte b[], int off, int len, PublicKey pubKey) throws IOException {
        if(isClosed && decryptedWaste.length == 0 && queue.size() == 0) return -1; // socket is closed and there aren't any buffered bytes.


        int r = 0;
        int n  = decryptedWaste.length > len ? len : decryptedWaste.length ;
        System.arraycopy(decryptedWaste,0,b,off,n);

        byte [] nDecryptedWaste = new byte[decryptedWaste.length - n];
        System.arraycopy(decryptedWaste, n, nDecryptedWaste, 0, decryptedWaste.length - n);
        decryptedWaste = nDecryptedWaste;
        if(decryptedWaste.length != 0) return n;

        byte[] temp = new byte[0];
        while(n < len){
            if(queue.size() == 0) break;
            temp = queue.poll();
            //decrypt
            //temp = new Encryptor(pubKey).validateData(temp); #Uncomment_This_To_Turn_On_Encryption

            r = len-n < temp.length ? len-n : temp.length;
            System.arraycopy(temp,0,b,off+n,r);
            n+=r;
        }

        nDecryptedWaste = new byte[temp.length-r];
        System.arraycopy(temp, r, nDecryptedWaste, 0, temp.length-r);
        decryptedWaste = nDecryptedWaste;

        return n;
    }
}
