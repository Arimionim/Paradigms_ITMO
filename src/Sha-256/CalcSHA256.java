import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalcSHA256 {

    public static void main(String[] args) {
        try{
            FileInputStream fstream = new FileInputStream(args[0]);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String fileName;
            while ((fileName = br.readLine()) != null){
                byte[] input = Files.readAllBytes(Paths.get(fileName));
                MessageDigest hasher;
                try {
                    hasher = MessageDigest.getInstance("SHA-256");
                }catch (NoSuchAlgorithmException e){
                    System.err.print("Error: can't create class, while processing file: " + fileName);
                    return;
                }
                byte[] result = hasher.digest(input);
                String output = DatatypeConverter.printHexBinary(result).toUpperCase();
                System.out.println(output);
            }
        }catch (IOException e){
            System.err.println("Error: can't open file: " + args[0]);
        }
    }
}