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

    @Test
    public void testValidNumberInput() {
        String simulatedInput = "4\n"; // Simulates entering the number 4 and pressing Enter
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in);

        try {
            int result = CalculatorAppConsole.handleNumberInput(scanner);
            assertEquals(4, result);
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
            int result = CalculatorAppConsole.handleNumberInput(scanner);

            assertEquals(1, result);
            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid number."));
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

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

            // Cleanup: Restore System.in
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
    public void testCalculation() {
        String simulatedInput = "5\n+\n3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            CalculatorAppConsole.main(new String[]{});

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