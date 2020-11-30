/**
 * Provides methods for encryption, decryption, and
 * reading and writing to a file
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
 */

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

    /**
     * Constructs an encryption storage object with
     * a cipher and keypair
     */
    public EncryptionStorage(){
        try{
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            pair = keyPairGen.generateKeyPair();
            cipher = Cipher.getInstance("RSA");
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            e.printStackTrace();
        }
    }

    /**
     * Encrypts user's draw
     *
     * @param data user's draw
     * @return byte[]
     */
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

    /**
     * Writes byte[] to file
     *
     * @param filename derived from user's password
     * @param data encrypted data
     */
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

    /**
     * Reads draws from file
     *
     * @param filename derived from user's password
     * @return byte[]
     */
    public byte[] bytesFileReader(String filename){
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypts byte[]
     * @param data split data
     * @return String[] with draws
     */
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

    /**
     * Splits data into 256 byte chunks for decryption
     * @param data data from file
     * @return ArrayList of byte[]
     */
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
