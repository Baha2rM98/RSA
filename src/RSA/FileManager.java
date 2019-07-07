package RSA;

import java.io.*;
import java.util.Scanner;

abstract class FileManager {

    FileManager() {
    }

    public File makeFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
//        String TXTPostFix = ".txt";
//        fileName = "Encrypted _ " + fileName;
        File file = new File(directory, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public File makeBinaryFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
//        String binaryPostFix = ".bin";
//        fileName = "Encrypted _ " + fileName;
        File file = new File(directory, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public String readFile(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        StringBuilder textBuilder = new StringBuilder();
        while (reader.hasNextLine()) {
            textBuilder.append(reader.nextLine());
        }
        return textBuilder.toString();
    }

    public String readBinaryFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        final int size = ois.available();
        byte[] buf = new byte[size];
        for (int i = 0; i < size; i++) {
            buf[i] = ois.readByte();
        }
        return new String(buf);
    }

    public File writeBinaryFile(File directory, String fileName, String text) throws IOException {
        File file = makeBinaryFile(directory, fileName);
        assert file != null;
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.write(text.getBytes());
        os.flush();
        os.close();
        fos.flush();
        fos.close();
        return file;
    }

    public File writeFile(File directory, String fileName, String text) throws IOException {
        File file = makeFile(directory, fileName);
        assert file != null;
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.flush();
        fw.close();
        return file;
    }

    public void deleteFile(File file) {
        if (!file.exists()) {
            System.err.println("There is nothing to delete!");
            return;
        }
        if (file.exists())
            file.delete();
    }
}
