
package pkg12;

import java.awt.Checkbox;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.zip.Checksum;
public class HashFunctionTest {

    public static String calculateHash(MessageDigest algorithm,
            String fileName) throws Exception{

        FileInputStream     fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DigestInputStream   dis = new DigestInputStream(bis, algorithm);

        // read the file and update the hash calculation
        while (dis.read() != -1);

        // get the hash value as byte array
        byte[] hash = algorithm.digest();

        return byteArray2Hex(hash);
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static void main(String[] args) throws Exception {
        String fileName = "C:\\Users\\QN\\Desktop\\1.txt";
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        MessageDigest md5  = MessageDigest.getInstance("MD5");        

        System.out.println(calculateHash(sha1, fileName));
        System.out.println(calculateHash(md5, fileName));
        
        File file = new File(fileName);
        byte[] b = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(b);
        long a= checksum(b, b.length);
        int b1 = getCrc16(b);
    }
    static long checksum(byte[] buf, int length) {
        int i = 0;
        long sum = 0;
        while (length > 0) {
            sum += (buf[i++]&0xff) << 8;
            if ((--length)==0) break;
            sum += (buf[i++]&0xff);
            --length;
        }

        return (~((sum & 0xFFFF)+(sum >> 16)))&0xFFFF;
    }
    
    
    public static int getCrc16(byte[] buffer) {
        return getCrc16(buffer, 0, buffer.length, 0xA001, 0);
        }

    public synchronized static int getCrc16(byte[] buffer, int offset, int bufLen, int polynom, int preset) {
        preset &= 0xFFFF;
        polynom &= 0xFFFF;
        int crc = preset;
        for (int i = 0; i < bufLen; i++) {
            int data = buffer[i + offset] & 0xFF;
            crc ^= data;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0) {
                    crc = (crc >> 1) ^ polynom;
                } else {
                    crc = crc >> 1;
                }
            }
        }
        return crc & 0xFFFF;
    }
}