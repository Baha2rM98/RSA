package RSA;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Baha2r
 **/

public class RSA extends FileManager {
    private static BigInteger n;
    private static BigInteger d;
    private static BigInteger e;
    private static String SDT;
    private static ArrayList<String> SET;
    private static ArrayList<String> detachedText;

    public RSA() {
        super();
        try {
            final int N = 1024;
            final int C = 2;
            final BigInteger ONE = BigInteger.ONE;
            SecureRandom secureRandom = new SecureRandom();
            BigInteger p = BigInteger.probablePrime(N, secureRandom);
            BigInteger q = BigInteger.probablePrime(N, secureRandom);
            n = p.multiply(q);
            BigInteger Phi = p.subtract(ONE).multiply(q.subtract(ONE));
            e = BigInteger.probablePrime((N / C), secureRandom);
            while (Phi.gcd(e).intValue() != 1) {
                e = e.add(ONE);
            }
            d = e.modInverse(Phi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> makeBlocks(String text) {
        final int n = 32;
        ArrayList<String> strings = new ArrayList<>();
        if (text.length() <= n) {
            strings.add(text);
            return strings;
        }
        char[] chars = text.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        int k, p, j, temp = 0;
        for (int i = 0; i < chars.length; i++) {
            if ((i + 1) % n == 0) {
                k = i - 31;
                p = i + 1;
                for (j = k; j < p; j++) {
                    stringBuilder.append(chars[j]);
                }
                strings.add(stringBuilder.toString());
                stringBuilder.delete(0, n);
                temp = i + 1;
            }
        }
        for (int i = temp; i < chars.length; i++) {
            stringBuilder.append(chars[i]);
        }
        strings.add(stringBuilder.toString());
        stringBuilder.delete(0, n);
        return strings;
    }

    private String listToString(ArrayList<String> strings) {
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string);
            builder.append(", ");
        }
        return builder.toString();
    }

    private ArrayList<String> stringToList(String text) {
        String[] strings = text.split(", ");
        return new ArrayList<>(Arrays.asList(strings));
    }

    public ArrayList<String> encryption(String Message) {
        try {
            BigInteger ET;
            detachedText = makeBlocks(Message);
            SET = new ArrayList<>();
            for (String aDetachedText : detachedText) {
                if (aDetachedText.getBytes().length == 0)
                    continue;
                if (!AsciiChecker.checkRange(aDetachedText.getBytes())) {
                    ET = new BigInteger(ABS.abs(aDetachedText.getBytes())).modPow(e, n);
                    SET.add(ET.toString());
                } else {
                    ET = new BigInteger(aDetachedText.getBytes()).modPow(e, n);
                    SET.add(ET.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SET;
    }

    public String decryption(ArrayList<String> encryptedMessage) {
        try {
            StringBuilder tempSb = new StringBuilder();
            for (String anEncryptedMessage : encryptedMessage) {
                if (!AsciiChecker.checkRange(detachedText.get(0).getBytes())) {
                    SDT = new String(ABS.subAbs(new BigInteger(anEncryptedMessage).modPow(d, n).toByteArray()));
                    tempSb.append(SDT);
                } else {
                    SDT = new String(new BigInteger(anEncryptedMessage).modPow(d, n).toByteArray());
                    tempSb.append(SDT);
                }
            }
            SDT = tempSb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SDT;
    }

    /**
     * Returns encrypted file As a File Class, It uses RSA 1024 algorithm.
     * @param directory A File that is parent of main text file or binary file.
     * @param fileName  Name of file that is going to be create. Enter file name with suffix.
     * @return Encrypted file.
     **/

    public File fileEncryption(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        File file = null;
        File[] files = directory.listFiles();
        assert files != null;
        for (File value : files) {
            if (value.getName().equals(fileName)) {
                file = value;
                break;
            }
        }
        if (file == null) {
            System.err.println("There is no file with this information in this directory!");
            return null;
        }
        String text;
        if (file.getName().contains(".txt")) {
            text = readFile(file);
            if (text.equals("")) {
                System.err.println("file is empty!");
                return null;
            }
            fileName = "Encrypted_" + fileName;
            System.out.println("Your file is encrypted now!\n");
            return writeFile(directory, fileName, listToString(encryption(text)));
        }
        if (file.getName().contains(".bin")) {
            text = readBinaryFile(file);
            if (text.equals("")) {
                System.err.println("file is empty!");
                return null;
            }
            fileName = "Encrypted_" + fileName;
            System.out.println("Your file is encrypted now!\n");
            return writeBinaryFile(directory, fileName, listToString(encryption(text)));
        }
        return null;
    }

    /**
     * Returns decrypted file As a File Class, It uses RSA 1024 algorithm.
     * @param directory A File that is parent of main text file or binary file.
     * @param fileName  Name of file that is going to be create. Enter file name with suffix.
     * @return Decrypted file.
     **/

    public File fileDecryption(File directory, String fileName) throws IOException {
        String tempName = fileName;
        fileName = "Encrypted_" + fileName;
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        File encryptedFile = null;
        File[] files = directory.listFiles();
        assert files != null;
        for (File value : files) {
            if (value.getName().equals(fileName)) {
                encryptedFile = value;
                break;
            }
        }
        if (encryptedFile == null) {
            System.err.println("There is no file with this information in this directory!");
            return null;
        }
        String encrypted;
        ArrayList<String> encryptedList;
        if (encryptedFile.getName().contains(".txt")) {
            encrypted = readFile(encryptedFile);
            if (encrypted.equals("")) {
                System.err.println("file is empty!");
                return null;
            }
            encryptedList = stringToList(encrypted);
            String finalName = "Decrypted_" + tempName;
            System.out.println("Your file is decrypted now!\n");
            return writeFile(directory, finalName, decryption(encryptedList));
        }
        if (encryptedFile.getName().contains(".bin")) {
            encrypted = readBinaryFile(encryptedFile);
            if (encrypted.equals("")) {
                System.err.println("file is empty!");
                return null;
            }
            encryptedList = stringToList(encrypted);
            String finalName = "Decrypted_" + tempName;
            System.out.println("Your file is decrypted now!\n");
            return writeBinaryFile(directory, finalName, decryption(encryptedList));
        }
        return null;
    }
}