package hl7v2sendmessage;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author trevor.schiavone
 */
public class HL7v2SendMessage {
    
    static String getDemoHd4DoesntExist = "";
    
    static String getDemoHd4Exists = "";
    
    static String findCandidatesExists = "";

    public static void main(String[] args) throws Exception {
        try (Socket sock = new Socket("127.0.0.1", 4417)) {

//            String msg = readInput(args[0]);
            String msg = getDemoHd4Exists;
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //baos.write(0x0b); //header byte
            //baos.write(msg.length()); //length... does this need to be padded?
            baos.write(msg.getBytes());
            byte[] message = baos.toByteArray();

            sock.getOutputStream().write(message);
            sock.getOutputStream().flush();
            byte[] response = readStream(sock.getInputStream());
            String output = new String(response, "UTF-8");
            Scanner s = new Scanner(output);
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
        }
    }

    private static String readInput(String filename) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        return new String(readStream(fis));
    }

    private static byte[] readStream(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = is.read(buff);
        while (len > 0) {
            baos.write(buff, 0, len);
            if (len == buff.length) {
                len = is.read(buff);
            } else {
                len = 0;
            }
        }
        return baos.toByteArray();
    }

}
