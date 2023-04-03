import java.util.*;

public class ATMExample {
    public static void main(String args[]) {
        int balance = 10000, withdraw, deposit;
        Scanner sc = new Scanner(System.in);
        while (true ) {
            System.out.println("Automated Teller Machine");
            System.out.println("Choose 1 for WithDraw");
            System.out.println("Choose 2 for Deposit");
            System.out.println("Choose 3 for Check Balance");
            System.out.println("Choose 4 for EXIT");
            System.out.println("Choose the operation you want to perform:");
            int choise = sc.nextInt();
            switch(choise) {
                 case 1:
                System.out.println("Enter money to be withdraw:");
                withdraw = sc.nextInt();
                if (balance < withdraw) {
                    System.out.println("Insufficient Balance");
                }
                else {
                    balance = balance - withdraw;
                    System.out.println("Please collect your money");
                }
                System.out.println("");
                break;

                 case 2:
                System.out.println("Enter money to be deposit:");
                deposit = sc.nextInt();
                balance = balance + deposit;
                System.out.println("Your money has been successfully deposited");
                System.out.println("");
                break;
                 case 3:
                System.out.println("Balance : " + balance);
            }
            
        }
        sc.close();
        }
    }
