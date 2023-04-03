import java.util.Scanner;

public class TypingSpeedChecker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter some text: ");
        String text = input.nextLine();
        long startTime = System.currentTimeMillis();
        System.out.println("Type the text again: ");
        String typedText = input.nextLine();
        long endTime = System.currentTimeMillis();
        if (text.equals(typedText)) {
            long elapsedTime = endTime - startTime;
            double typingSpeed = (double) text.length() / (elapsedTime /1000);
            System.out.println("Your typing speed is: " + typingSpeed + " characters per second.");
        } else {
            System.out.println("The text you typed does not match the original text.");
        }
        input.close();
    }
}
