// Switched to the newer junit5
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorAppGUITest {

    @Test
    void testWelcomeMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            CalculatorAppGUI.printWelcomeMessage();
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Welcome to the Calculator App!"));
            assertTrue(output.contains("Available operations: +, -, *, /"));
            assertTrue(output.contains("Type '!!!' to quit."));
        } finally {
            System.setOut(originalOut);
        }
    }

    // NUMBER INPUT TESTS
    @Test
    void testValidNumberInput() {
        String simulatedInput = "4\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppGUI.handleNumberInput(scanner);
            assertEquals(4.0, result, 0.0001);
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    void testInvalidNumberInput() {
        String simulatedInput = "q\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppGUI.handleNumberInput(scanner);

            assertEquals(1, result, 0.0001);
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid number."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testValidNegativeNumberInput() {
        String simulatedInput = "-5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppGUI.handleNumberInput(scanner);

            assertEquals(-5, result, 0.0001);
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    void testInvalidNegativeNumberInput() {
        String simulatedInput = "-a\n5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppGUI.handleNumberInput(scanner);

            assertEquals(5, result, 0.0001);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid number."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testValidFloatingPointNumberInput() {
        String simulatedInput = "5.75\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppGUI.handleNumberInput(scanner);

            assertEquals(5.75, result, 0.0001);
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    void testClearInNumberInput1() {
        String simulatedInput = "clear\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppGUI.handleNumberInput(scanner);
            assertTrue(Double.isNaN(result));
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    void testClearInNumberInput2() {
        String simulatedInput = "4\n+\nclear\n2\n+\n1000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppGUI.processCalculations(scanner, 1);

            String output = outputStream.toString();
            assertTrue(output.contains("Results cleared!"));
            assertTrue(output.contains("1002.0"));

        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    // OPERATOR INPUT TESTS
    @Test
    void testValidOperatorInput() {
        char[] validOperators = {'+', '-', '*', '/'};

        for (char operator : validOperators) {
            String simulatedInput = operator + "\n";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            Scanner scanner = new Scanner(System.in);

            char result = CalculatorAppGUI.handleOperatorInput(scanner);
            assertTrue(result == '+' || result == '-' || result == '*' || result == '/');

            System.setIn(System.in);
        }
    }

    @Test
    void testInvalidOperatorInput() {
        String simulatedInput = "q\n+\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            char result = CalculatorAppGUI.handleOperatorInput(scanner);

            assertEquals('+', result);
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid operator."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testClearInOperatorInput() {
        String simulatedInput = "4\nclear\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);
        // Currently passes, but also outputs "Invalid operator"
        // Tried switching to else if statements, but I think they are the same in java
        try {
            char result = CalculatorAppGUI.handleOperatorInput(scanner);
            assertEquals('C', result);
        } finally {
            System.setIn(System.in);
        }
    }

    // CALCULATION TESTS
    @Test
    void testCalculation() {
        String simulatedInput = "5\n+\n3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppGUI.processCalculations(scanner, 1);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("8"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testZeroCalculation() {
        String simulatedInput = "5\n/\n0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            CalculatorAppGUI.main(new String[]{});
            fail("Expected ArithmeticException was not thrown.");

        } catch (ArithmeticException e) {
            assertEquals("Cannot divide by zero.", e.getMessage());
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testNegativeNumberCalculation() {
        String simulatedInput = "-5\n+\n-3\n"; // -5 + -3
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppGUI.processCalculations(scanner, 1);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("-8"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testFloatingPointCalculation() {
        String simulatedInput = "5.5\n+\n3.25\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppGUI.processCalculations(scanner, 1);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("8.75"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testMultipleCalculations() {
        String simulatedInput = "5\n+\n3\n-\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppGUI.processCalculations(scanner, 2);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("1"));

        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    void testClearInCalculations() {
        String simulatedInput = "4\n+\nclear\n2\n+\n1000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppGUI.processCalculations(scanner, 1);

            String output = outputStream.toString();

            assertTrue(output.contains("Results cleared!"));
            assertTrue(output.contains("1002.0"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    // EXIT TESTS
    @Test
    void testExitDuringNumberInput() {
        String simulatedInput = "!!!\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        try {
            CalculatorAppGUI.handleNumberInput(new Scanner(System.in));
            fail("ExitException was not thrown.");
        } catch (CalculatorAppGUI.ExitException e) {
            assertEquals("User chose to exit the program.", e.getMessage());
        }
    }

    @Test
    void testExitDuringOperatorInput() {
        String simulatedInput = "!!!\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        try {
            CalculatorAppGUI.handleOperatorInput(new Scanner(System.in));
            fail("ExitException was not thrown.");
        } catch (CalculatorAppGUI.ExitException e) {
            assertEquals("User chose to exit the program.", e.getMessage());
        }
    }
}