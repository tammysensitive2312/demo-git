import java.io.*;
import java.util.*;

public class Solution {
    public static boolean checkString(String s) {
        try {
            return true;
        } catch (Exception e) {
           return false;
        }
    }
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    sc.close();
    String s = " "+ n;
    if (checkString(s)) {
        System.out.println("Good job");
    } else {
        System.out.println("Wrong answer");
    }
    }
}
