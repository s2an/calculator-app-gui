import java.util.Scanner;

public class CalculatorAppConsole {
    public static void main(String[] args) {
        try {
            runCalculator();
        } catch (ExitException e) {
            System.out.println("Exiting the calculator. Goodbye!");
        }
    }

    // Error handling for exiting the program
    public static class ExitException extends RuntimeException {
        public ExitException() {
            super("User chose to exit the program.");
        }
    }

    public static void runCalculator() {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);

        double num1 = handleNumberInput(scanner);
        char operator = handleOperatorInput(scanner);
        double num2 = handleNumberInput(scanner);
        double result = performCalculation(num1, num2, operator);
        System.out.println(result);
    }

    public static void printWelcomeMessage() {
        System.out.println("Welcome to the Calculator App!");
        System.out.println("Available operations: +, -, *, /");
        System.out.println("Type '!!!' to quit.");
    }

    public static double handleNumberInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            if (input.equals("!!!")) {
                throw new ExitException();
            }

            if (input.matches("-?\\d+")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid number.");
        }
    }


    public static char handleOperatorInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();

            if (input.equals("!!!")) {
                throw new ExitException();
            }

            if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")) {
                return input.charAt(0);
            }
            System.out.println("Invalid operator.");
        }
    }

    public static double performCalculation(double num1, double num2, char operator) {
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