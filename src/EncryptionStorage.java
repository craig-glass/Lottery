import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Random;

public class EncryptionStorage {

    private Cipher cipher;
    private KeyPair pair;

    public EncryptionStorage(){}

    public byte[] encryptData(String data){
        try{
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            pair = keyPairGen.generateKeyPair();
            PublicKey publicKey = pair.getPublic();
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(data.getBytes());
            return cipher.doFinal();
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException
        | InvalidKeyException ex){
            ex.printStackTrace();
        }
        catch(BadPaddingException e){
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }
        return null;
    }

    public String decryptData(byte[] data){
        try{
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
            byte[] decipheredText = cipher.doFinal(data);
            return new String(decipheredText, StandardCharsets.UTF_8);
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
            e.printStackTrace();
        }
        return null;
    }

    public void bytesFileWriter(String filename, byte[] data){
        try{
            OutputStream os = new FileOutputStream(filename);
            os.write(data);
            os.flush();
            os.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public byte[] bytesFileReader(String filename){
        try{


            File file = new File(filename);
            RandomAccessFile data = new RandomAccessFile(file, "r");
            byte [] fileData = new byte[256];

            data.read(fileData);


            return fileData;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Cipher getCipher(){
        return cipher;
    }

    public void setCipher(Cipher ciph){
        this.cipher = ciph;
    }

    public KeyPair getPair(){
        return pair;
    }

}
