import java.util.Scanner;


public class CalculatorAppConsole {
    public static void main(String[] args) {
        printWelcomeMessage();
        handleNumberInput();
    }

    public static void printWelcomeMessage() {
        System.out.println("Welcome to the Calculator App!");
        System.out.println("Available operations: +, -, *, /");
        System.out.println("Type '!!!' to quit.");
    }

    public static int handleNumberInput() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    public static char handleOperatorInput() {
        Scanner scanner = new Scanner(System.in);
        char operator;

        while (true) {
            operator = scanner.next().charAt(0);
            if (operator == '+' || operator == '-' || operator == '*' || operator == '/') {
                return operator;
            } else {
                System.out.println("Invalid operator.");
            }
        }
    }
}
