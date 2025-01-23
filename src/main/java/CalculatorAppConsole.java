import java.util.Scanner;


public class CalculatorAppConsole {
    public static void main(String[] args) {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);

        int num1 = handleNumberInput(scanner);
        char operator = handleOperatorInput(scanner);
        int num2 = handleNumberInput(scanner);
        int result = performCalculation(num1, num2, operator);
        System.out.println(result);
    }

    public static void printWelcomeMessage() {
        System.out.println("Welcome to the Calculator App!");
        System.out.println("Available operations: +, -, *, /");
        System.out.println("Type '!!!' to quit.");
    }

    public static int handleNumberInput(Scanner scanner) {
        return scanner.nextInt();
    }

    public static char handleOperatorInput(Scanner scanner) {
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

    public static int performCalculation(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    throw new ArithmeticException("Cannot divide by zero.");
                }
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}