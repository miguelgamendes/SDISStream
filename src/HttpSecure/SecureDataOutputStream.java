package HttpSecure;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by danfergo on 05-06-2015.
 */
public class SecureDataOutputStream extends DataOutputStream {
    private KeyPair keyPair = new KeyPair(null,null);
    private PublicKey publicKey;

    public SecureDataOutputStream(OutputStream outputStream) {
        super(outputStream);
    }


    public void encryptAndWrite(byte[] data, int i, int n) throws IOException {
        super.write(data, i, n);
    }


}
