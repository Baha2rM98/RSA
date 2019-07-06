package RSA;
import java.io.*;
import java.util.*;

public class FileManager {
    private FileWriter fw;
    private Scanner reader;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectInputStream ois;
    private ObjectOutputStream os;
    private final String TXTPostFix = ".txt";
    private final String BinaryPostFix = ".bin";

    public FileManager() {
    }
    public File makeFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
        fileName = fileName + TXTPostFix;
        File file = new File(directory, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public File makeBinaryFile (File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
        fileName = fileName + BinaryPostFix;
        File file = new File(directory, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

//    public String readFile(){
//
//    }

}
