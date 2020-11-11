import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class EncryptionStorage {


    private static SecretKeySpec secretKey;

    public EncryptionStorage(){}

    public static void setKey(String myKey){
        MessageDigest md;
        try{
            byte[] key = myKey.getBytes("UTF-8");
            md = MessageDigest.getInstance("SHA-1");
            key = md.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }


    public String encryptData(String data, String secret){
        try{
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] byteString = data.getBytes("UTF-8");
            byte[] encryptedBytes = cipher.doFinal(byteString);
            String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);
            return encryptedString;
        }
        catch(Exception e){
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decryptData(String data, String secret){
        try{
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(data);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedString = new String(decryptedBytes);
            return decryptedString;
        }
        catch(Exception e){
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public void textFileWriter(String filename, String data){
        try{
            FileWriter writer = new FileWriter(filename, true);
            writer.write(data);
            writer.flush();
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public String textFileReader(String filename){
        try{

            String data = null;
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while(reader.hasNext()){
                data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
            return data;

        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }


}
