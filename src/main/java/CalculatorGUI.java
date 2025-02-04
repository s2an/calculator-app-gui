import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;

public class CalculatorGUI extends Application {
    private final CalculatorCLI calculatorCLI = new CalculatorCLI();
    private final TextField inputField = new TextField();

    @Override
    public void start(Stage primaryStage) {
        inputField.setPromptText("0");
        Button addButton = new Button("+");
        Button subtractButton = new Button("-");
        Button multiplyButton = new Button("*");
        Button divideButton = new Button("/");
        Button clearButton = new Button("C");
        Button exitButton = new Button("!!!");
        Button enterButton = new Button("Enter");

        addButton.setOnAction(e -> handleOperator('+'));
        subtractButton.setOnAction(e -> handleOperator('-'));
        multiplyButton.setOnAction(e -> handleOperator('*'));
        divideButton.setOnAction(e -> handleOperator('/'));
        clearButton.setOnAction(e -> handleOperator('C'));
        exitButton.setOnAction(e -> handleOperator('Q'));

        // This links to handleNumberInput
        // Would be better if the operator acts as the enter button, would also need to add an '=' button
        // Refactor after getting this working
        enterButton.setOnAction(e -> handleNumberInput());

        // Layout
        VBox layout = new VBox(10, inputField, enterButton, addButton, subtractButton, multiplyButton, divideButton, clearButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(layout, 300, 300));
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    private void handleNumberInput() {
        try {
            double num = Double.parseDouble(inputField.getText());
            calculatorCLI.processCalculations(new GUIInputScanner(num).getScanner(), 1);
            inputField.clear();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number: " + e.getMessage());
        }
    }

    private void handleOperator(char operator) {
        inputField.setText(String.valueOf(operator));
        calculatorCLI.processCalculations(new GUIInputScanner(operator).getScanner(), 1);
        inputField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class GUIInputScanner {
        private final Scanner scanner;

        // Constructor for numbers
        public GUIInputScanner(double num) {
            // Wrap number inside Scanner
            this.scanner = new Scanner(String.valueOf(num));
        }

        // Constructor for operators
        public GUIInputScanner(char operator) {
            // Wrap operator inside Scanner
            this.scanner = new Scanner(String.valueOf(operator));
        }

        // Return the real Scanner instance
        public Scanner getScanner() {
            return scanner;
        }
    }
}