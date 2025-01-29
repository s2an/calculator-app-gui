import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class CalculatorGUITest extends ApplicationTest {
    private TextField num1, num2;
    private Label resultLabel;
    private Button addButton;

    @Override
    public void start(Stage stage) {
        CalculatorGUI app = new CalculatorGUI();
        app.start(stage);

        num1 = lookup(".text-field").nth(0).query();
        num2 = lookup(".text-field").nth(1).query();
        resultLabel = lookup(".label").query();
        addButton = lookup(".button").query();
    }

    @Test
    void testAdditionInGUI() {
        interact(() -> {
            num1.setText("5");
            num2.setText("3");
        });

        clickOn(addButton);
        assertEquals("Result: 8.0", resultLabel.getText());
    }

    @Test
    void testInvalidInput() {
        interact(() -> {
            num1.setText("abc");
            num2.setText("3");
        });

        clickOn(addButton);
        assertEquals("Invalid input.", resultLabel.getText());
    }
}

