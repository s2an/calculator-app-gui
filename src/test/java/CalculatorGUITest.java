import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorGUITest extends ApplicationTest {
    private CalculatorGUI calculatorGUI;
    private TextField inputField;
    private Button addButton, subtractButton, multiplyButton, divideButton, clearButton, enterButton;

    @Override
    public void start(Stage stage) {
        calculatorGUI = new CalculatorGUI();
        calculatorGUI.start(stage);
        inputField = lookup(".text-field").query();
        addButton = lookup(".button").match((b) -> ((Button) b).getText().equals("+")).query();
        subtractButton = lookup(".button").match((b) -> ((Button) b).getText().equals("-")).query();
        multiplyButton = lookup(".button").match((b) -> ((Button) b).getText().equals("*")).query();
        divideButton = lookup(".button").match((b) -> ((Button) b).getText().equals("/")).query();
        clearButton = lookup(".button").match((b) -> ((Button) b).getText().equals("C")).query();
        enterButton = lookup(".button").match((b) -> ((Button) b).getText().equals("Enter")).query();
    }

    @BeforeEach
    void setUp() {
        inputField.clear();
    }

    @Test
    void testValidNumberInput() {
        clickOn(inputField).write("5");
        clickOn(enterButton);
        assertEquals("", inputField.getText(), "Input field should be cleared after entering a number.");
    }

    @Test
    void testInvalidNumberInput() {
        clickOn(inputField).write("abc");
        clickOn(enterButton);
        assertEquals("abc", inputField.getText(), "Input field should not accept invalid numbers.");
    }

    @Test
    void testOperatorSelection() {
        clickOn(inputField).write("5");
        clickOn(enterButton);
        clickOn(addButton);
        assertEquals("+", inputField.getText(), "Operator should be displayed in the input field.");
    }

    @Test
    void testClearFunctionality() {
        clickOn(inputField).write("5");
        clickOn(enterButton);
        clickOn(clearButton);
        assertEquals("", inputField.getText(), "Input field should be cleared after clicking 'C'.");
    }
}
