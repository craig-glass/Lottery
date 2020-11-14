import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class EncryptionStorage {


    private Cipher cipher;
    private KeyPair pair;

    public EncryptionStorage(){}

    public byte[] encryptData(String data) {
        try{
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            pair = keyPairGen.generateKeyPair();
            PublicKey publicKey = pair.getPublic();
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(data.getBytes());
            return cipher.doFinal();
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void bytesFileWriter(String filename, byte[] data){
        try {
            OutputStream os = new FileOutputStream(filename);
            os.write(data);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] bytesFileReader(String filename){
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptData(byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
            byte[] decipheredText = cipher.doFinal(data);
            System.out.println(
                    new String(decipheredText, StandardCharsets.UTF_8));
            return new String(decipheredText, StandardCharsets.UTF_8);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
