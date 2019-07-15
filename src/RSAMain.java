import RSA.RSA;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RSAMain {
    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        RSA rsa = new RSA();
        System.out.println("If you wanna use persian words, this program doesn't support symbols and numbers!\nIt just support space character.\n\n");
        System.out.println("For using string mod enter 1, For using file mod enter 2, For exit enter 0.");
        String ans = scn.next();
        if (ans.equals("1")) {
            while (true) {
                System.out.println("Enter Text : (enter q or Q for exit)");
                String s = scn.nextLine();
                if (s.equals("q") || s.equals("Q")) {
                    System.out.println("Terminated!");
                    break;
                } else {
                    ArrayList<String> e = rsa.encryption(s);
                    System.out.println(e);
                    String d = rsa.decryption(e);
                    System.out.println(d);
                    System.out.println();
                }
            }
        }
        if (ans.equals("2")) {
            while (true) {
                System.out.println("Enter the path of your file's directory (for example C:\\User\\...)\nEnter q or Q for exit");
                String way = scn.next();
                if (way.equals("q") || way.equals("Q")) {
                    System.out.println("Terminated!");
                    break;
                } else {
                    File path = new File(way);
                    System.out.println("Enter your file name with suffix (for example .txt .bin and...)");
                    String name = scn.next();
                    rsa.fileEncryption(path, name);
                    System.out.println("Do you wanna decrypt this file?\nPress y or n...");
                    String res = scn.next();
                    if (res.equals("y") || res.equals("Y")) {
                        rsa.fileDecryption(path, name);
                    } else {
                        System.out.println("Ops! you haven't chosen y!\nTerminated...");
                        break;
                    }
                }
            }
        }
    }
}