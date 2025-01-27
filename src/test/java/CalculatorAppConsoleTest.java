import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CalculatorAppConsoleTest {

    @Test
    public void testWelcomeMessage() {
        // Arrange: Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act: Run the main method
            CalculatorAppConsole.printWelcomeMessage();
            // Assert: Verify the output contains the welcome message
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Welcome to the Calculator App!"));
            assertTrue(output.contains("Available operations: +, -, *, /"));
            assertTrue(output.contains("Type '!!!' to quit."));
        } finally {
            // Cleanup: Restore the original System.out
            System.setOut(originalOut);
        }
    }

    // NUMBER INPUT TESTS
    @Test
    public void testValidNumberInput() {
        String simulatedInput = "4\n"; // Simulates entering the number 4 and pressing Enter
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppConsole.handleNumberInput(scanner);
            assertEquals(4.0, result, 0.0001); // The delta is needed to account for a margin of error b/c java stores the numbers as binary approximations!
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    public void testInvalidNumberInput() {
        String simulatedInput = "q\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppConsole.handleNumberInput(scanner);

            assertEquals(1, result, 0.0001);
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid number."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testValidNegativeNumberInput() {
        String simulatedInput = "-5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppConsole.handleNumberInput(scanner);

            assertEquals(-5, result, 0.0001);
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    public void testInvalidNegativeNumberInput() {
        String simulatedInput = "-a\n5\n"; // Invalid input, then valid input
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppConsole.handleNumberInput(scanner);

            assertEquals(5, result, 0.0001);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid number."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testValidFloatingPointNumberInput() {
        String simulatedInput = "5.75\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppConsole.handleNumberInput(scanner);

            assertEquals(5.75, result, 0.0001);
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    public void testClearInNumberInput1() {
        String simulatedInput = "clear\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            double result = CalculatorAppConsole.handleNumberInput(scanner);
            assertTrue(Double.isNaN(result));
        } finally {
            System.setIn(System.in);
        }
    }

    @Test
    public void testClearInNumberInput2() {
        String simulatedInput = "4\n+\nclear\n1\n+\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppConsole.processCalculations(scanner, 1);

            String output = outputStream.toString();
            assertTrue(output.contains("Results cleared!"));
            //assertTrue(output.contains("2"));
            //not calculating correctly after the clear. make separate test
            assertEquals("2", output);

        } finally {
            System.setIn(System.in);
            System.setOut(System.out);
        }
    }

    // OPERATOR INPUT TESTS
    @Test
    public void testValidOperatorInput() {
        char[] validOperators = {'+', '-', '*', '/'};

        for (char operator : validOperators) {
            String simulatedInput = operator + "\n";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            Scanner scanner = new Scanner(System.in);

            char result = CalculatorAppConsole.handleOperatorInput(scanner);
            assertTrue(result == '+' || result == '-' || result == '*' || result == '/');

            System.setIn(System.in);
        }
    }

    @Test
    public void testInvalidOperatorInput() {
        String simulatedInput = "q\n+\n"; // Needs a valid input after an invalid one. Else the scanner causes it to throw a NoSuchElementException.
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            char result = CalculatorAppConsole.handleOperatorInput(scanner);

            assertEquals('+', result);
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid operator."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testClearInOperatorInput() {
        String simulatedInput = "4\nclear\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);
// Currently passes, but also outputs "Invalid operator"
        try {
            char result = CalculatorAppConsole.handleOperatorInput(scanner);
            assertEquals('C', result);
        } finally {
            System.setIn(System.in);
        }
    }

    // CALCULATION TESTS
    @Test
    public void testCalculation() {
        String simulatedInput = "5\n+\n3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppConsole.processCalculations(scanner, 1);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("8"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testZeroCalculation() {
        String simulatedInput = "5\n/\n0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            CalculatorAppConsole.main(new String[]{});
            fail("Expected ArithmeticException was not thrown.");

        } catch (ArithmeticException e) {
            assertEquals("Cannot divide by zero.", e.getMessage());
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testNegativeNumberCalculation() {
        String simulatedInput = "-5\n+\n-3\n"; // -5 + -3
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Needed to use the Scanner
        Scanner scanner = new Scanner(System.in);

        try {
            // Switched away from .main
            CalculatorAppConsole.processCalculations(scanner, 1);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("-8"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testFloatingPointCalculation() {
        String simulatedInput = "5.5\n+\n3.25\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppConsole.processCalculations(scanner, 1);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("8.75"));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testMultipleCalculations() {
        String simulatedInput = "5\n+\n3\n-\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppConsole.processCalculations(scanner, 2);

            String output = outputStream.toString().trim();
            assertTrue(output.contains("1"));

        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testClearInCalculations() {
        String simulatedInput = "4\n+\nclear\n1\nclear\n2\n+\n1000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            CalculatorAppConsole.processCalculations(scanner, 5);

            String output = outputStream.toString();
            assertTrue(output.contains("Results cleared!"));
            //need to figure out why the other clear isnt calculating correctly
            assertEquals("1002", output);
            assertTrue(output.contains("1002.0"));
        } finally {
            System.setIn(System.in);
            System.setOut(System.out);
        }
    }

    // EXIT TESTS
    @Test
    public void testExitDuringNumberInput() {
        String simulatedInput = "!!!\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        try {
            CalculatorAppConsole.handleNumberInput(new Scanner(System.in));
            fail("ExitException was not thrown.");
        } catch (CalculatorAppConsole.ExitException e) {
            assertEquals("User chose to exit the program.", e.getMessage());
        }
    }

    @Test
    public void testExitDuringOperatorInput() {
        String simulatedInput = "!!!\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        try {
            CalculatorAppConsole.handleOperatorInput(new Scanner(System.in));
            fail("ExitException was not thrown.");
        } catch (CalculatorAppConsole.ExitException e) {
            assertEquals("User chose to exit the program.", e.getMessage());
        }
    }
}