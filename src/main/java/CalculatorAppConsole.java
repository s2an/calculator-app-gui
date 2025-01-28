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

        try {
            processCalculations(scanner, 256000);
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void processCalculations(Scanner scanner, int iterationLimit) {
        double num1 = handleNumberInput(scanner);
        // Need to limit iterations so tests do have an infinite loop
        int iterations = 0;

        while (iterations < iterationLimit) {

            char operator = handleOperatorInput(scanner);
            if (operator == 'C') { // Handle "clear"
                System.out.println("Results cleared!");
                num1 = handleNumberInput(scanner); // Allow input of a new number
                continue; // Skip to the next iteration to allow a new number input
            }

            double num2 = handleNumberInput(scanner);
            if (Double.isNaN(num2)) {
                System.out.println("Results cleared!");
                num1 = handleNumberInput(scanner);
                continue;
            }

            num1 = performCalculation(num1, num2, operator);
            System.out.println(num1);

            iterations++;
        }
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

            if (input.equalsIgnoreCase("clear")) {
                return Double.NaN;
            }

            if (input.matches("-?\\d+(\\.\\d+)?")) {
                return Double.parseDouble(input);
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

            if (input.equalsIgnoreCase("clear")) {
                return 'C';
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