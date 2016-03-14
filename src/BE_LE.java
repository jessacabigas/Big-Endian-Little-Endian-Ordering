
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jessa
 */
public class BE_LE {

    ArrayList output;

    public static void main(String[] args) throws FileNotFoundException {

        String input;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter filename: ");
        // input = scan.next();
        input = "mp1.in";
        BE_LE tmp = new BE_LE();
        tmp.output = new ArrayList();
        tmp.toBigEndian(input);
        tmp.output.clear();
        tmp.toLittleEndian(input);

    }

    private void toBigEndian(String input) throws FileNotFoundException {
        Scanner read = new Scanner(new File(input));
        System.out.println("BIG ENDIAN:");
        while (read.hasNext()) {
            String tmp = read.next();
            if (tmp.matches("[a-zA-Z]+")) {
                String temp = BE_String(tmp);

            } else {//for integers
                String w = BE_convertInt(tmp);
                ArrayList temp = BE_32bit(w);
                for (int i = 0; i < temp.size(); i++) {
                //    System.out.print(temp.get(i) + " ");
                    output.add(temp.get(i));
                }
            }
        }
        BE_display();
    }

    private String BE_String(String tmp) {
        while (tmp.length() % 4 != 0) {
            tmp += "0";
        }
        for (int i = 0; i < tmp.length(); i++) {
            output.add(tmp.charAt(i));
        }
        return tmp;
    }

    private void toLittleEndian(String input) throws FileNotFoundException {
        Scanner read = new Scanner(new File(input));
        System.out.println("\nSMALL ENDIAN");
        while (read.hasNext()) {
            String tmp = read.next();
            if (tmp.matches("[a-zA-Z]+")) {
                String temp = LE_String(tmp);
            } else {
               String w = BE_convertInt(tmp);
                ArrayList temp = BE_32bit(w);
                for (int i = 0; i < temp.size(); i++) {
              //      System.out.print(temp.get(i) + " ");
                    output.add(temp.get(i));
                }
            }
        }
        BE_display();
    }

    private String LE_String(String tmp) {
        while (tmp.length() % 4 != 0) {
            tmp += "0";
        }
        StringBuilder sb = new StringBuilder();
        String newString = sb.toString();
        String ns = "";
        int finish = 4;
        int start = 0;
        for (int i = 0; i < tmp.length() / 4; i++) {
            for (; start < finish; start++) {
                String t = Character.toString(tmp.charAt(start));
                ns += t;
            }
            finish += 4;
            sb.append(ns);
            sb = sb.reverse();
            newString += sb.toString();
            ns = "";
            sb.setLength(0);
        }
        
         for (int i = 0; i < newString.length(); i++) {
            output.add(newString.charAt(i));
        }
        return newString;
    }

    private String BE_convertInt(String tmp) {
     //   System.out.println("");
        StringBuilder sb = new StringBuilder();
        int temp = Integer.parseInt(tmp);
        //   System.out.println("temp: " + temp);
        String ns = "";
        String zeros = "";
        int x = temp;
        while (x > 0) {
            ns += String.valueOf(x % 2);
            x = x / 2;
        }
        sb.append(ns);
        sb = sb.reverse();
        ns = sb.toString();
        for (int i = 32 - sb.length(); i > 0; i--) {
            zeros += "0";
        }
        zeros += ns;
        //  System.out.println(zeros);
        return zeros;
    }

    private ArrayList<Integer> BE_32bit(String w) {
        ArrayList<Integer> temp = new ArrayList();
        int start = 0;
        int finish = 8;
        while (finish <= 32) {
            temp.add(BE_valueOf8bits(w.substring(start, finish)));
            start += 8;
            finish += 8;
        }
        return temp;
    }

    private Integer BE_valueOf8bits(String tmp) {
        int ans = 0;
        int two = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(tmp);
        sb = sb.reverse();
        for (int i = 0; i < sb.length(); i++) {
            ans += Character.getNumericValue(sb.charAt(i)) * two;
            two *= 2;
        }
        return ans;
    }

    private void BE_display() {
        for (int i = 0; i < output.size(); i++) {
            System.out.print("\t");
            if (i % 4 == 0) {
                System.out.print("\n" +output.get(i) );
                
            } else {
                System.out.print(output.get(i) + "\t");
            }
        }
        System.out.println("");
    }

}
