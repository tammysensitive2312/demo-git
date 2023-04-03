import java.util.Scanner;
import java.util.ArrayList;

public class budgettingApp {
    static ArrayList<Expense> expenses = new ArrayList<Expense>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Quit");
            System.out.println("Enter choise: ");

            int choice = sc.nextInt();
            
            if (choice == 1) {
                addExpense();
            } else if (choice == 2) {
             viewExpenses();
            } else if (choice == 3) {
                break;
            } else {
                 System.out.println("Invalid choice");
            }
        }
    }
    public class Expense {
        String description;
        double amount;

        Expense(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
    }
    
    static void addExpense() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter description: ");
        String description = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        
        Expense expenses = new Expense(description, amount);
        System.out.println("Expense added");
        sc.close();
    }
    static void viewExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            System.out.println(expense.description + ": $" + expense.amount);
            total += expense.amount;
        }
        System.out.println("Total: $" + total);
    }
}
