import java.util.Scanner;

public class CalculatorCLI {
    // encapsulated (private) and immutable (final)
    private final CalculatorService calculator;

    public CalculatorCLI() {
        this.calculator = new CalculatorService();
    }

    // Error handling for exiting the program
    public static class ExitException extends RuntimeException {
        public ExitException() {
            super("User chose to exit the program.");
        }
    }

    public void runCalculator(int iterationLimit) {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);

        try {
            processCalculations(scanner, iterationLimit);
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processCalculations(Scanner scanner, int iterationLimit) {
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

            num1 = calculator.calculate(num1, num2, operator);
            System.out.println(num1);

            iterations++;
        }
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to the Calculator App!");
        System.out.println("Available operations: +, -, *, /");
        System.out.println("Type '!!!' to quit.");
    }

    public double handleNumberInput(Scanner scanner) {

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


    public char handleOperatorInput(Scanner scanner) {
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
}