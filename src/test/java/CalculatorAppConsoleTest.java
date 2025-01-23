import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
    public void testNumberInput() {
        String simulatedInput = "4\n"; // Simulates entering the number 4 and pressing Enter
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        try {
            int result = CalculatorAppConsole.handleInput();
            assertEquals(4, result);
        } finally {
            System.setIn(System.in);
        }
    }
}
