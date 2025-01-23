import java.util.Scanner;


public class CalculatorAppConsole {
    public static void main(String[] args) {
        printWelcomeMessage();
        handleInput();
    }

    public static void printWelcomeMessage() {
        System.out.println("Welcome to the Calculator App!");
        System.out.println("Available operations: +, -, *, /");
        System.out.println("Type '!!!' to quit.");
    }

    public static int handleInput() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }
}
