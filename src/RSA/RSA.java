package RSA;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

//Created by Baha2r

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
     * Please enter fileName with suffix
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
        String text;
        assert file != null;
        if (file.getName().contains(".txt")) {
            text = readFile(file);
            System.out.println("Your file is encrypted now!\n\n");
            fileName = "Encrypted_" + fileName;
            return writeFile(directory, fileName, listToString(encryption(text)));
        }
        if (file.getName().contains(".bin")) {
            text = readBinaryFile(file);
            fileName = "Encrypted_" + fileName;
            System.out.println("Your file is encrypted now!\n\n");
            return writeBinaryFile(directory, fileName, listToString(encryption(text)));
        }
        return null;
    }

    public File fileDecryption(File directory, String fileName) throws IOException {
        String tempName = fileName;
        fileName = "Encrypted_" + fileName;
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
        String encrypted;
        ArrayList<String> encryptedList;
        assert file != null;
        if (file.getName().contains(".txt")) {
            encrypted = readFile(file);
            encryptedList = stringToList(encrypted);
            String finalName = "Decrypted_" + tempName;
            System.out.println("Your file is decrypted now!\n\n");
            return writeFile(directory, finalName, decryption(encryptedList));
        }
        if (file.getName().contains(".bin")) {
            encrypted = readBinaryFile(file);
            encryptedList = stringToList(encrypted);
            String finalName = "Decrypted_" + tempName;
            System.out.println("Your file is decrypted now!\n\n");
            return writeBinaryFile(directory, finalName, decryption(encryptedList));
        }
        return null;
    }
}