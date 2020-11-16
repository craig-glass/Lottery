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
import java.util.ArrayList;

public class EncryptionStorage {


    private Cipher cipher;
    private KeyPair pair;

    public EncryptionStorage(){
        try{
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            pair = keyPairGen.generateKeyPair();
            cipher = Cipher.getInstance("RSA");
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            e.printStackTrace();
        }
    }

    public byte[] encryptData(String data) {
        try{

            PublicKey publicKey = pair.getPublic();

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(data.getBytes());
            return cipher.doFinal();
        }
        catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void bytesFileWriter(String filename, byte[] data){
        try {
            OutputStream os = new FileOutputStream(filename, true);
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

    public String[] decryptData(ArrayList<byte[]> data) {
        int i = 0;
        int dataSize = data.size();
        String[] decryptedData = new String[dataSize];
        for(byte[] block : data){

            try {
                cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
                byte[] decipheredText = cipher.doFinal(block);
                decryptedData[i] = new String(decipheredText, StandardCharsets.UTF_8);
                i++;
            } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }

        return decryptedData;
    }

    public ArrayList<byte[]> splitData(byte[] data){
        int offset = 0;
        ArrayList<byte[]> dataSplit = new ArrayList<>();
        while(offset < data.length){
            byte[] blockSplit = new byte[256];
            System.arraycopy(data, offset,blockSplit,0,256);
            offset += 256;
            dataSplit.add(blockSplit);
        }
        return dataSplit;
    }
}
