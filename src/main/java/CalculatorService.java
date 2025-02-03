// The service now only takes care of the calculation logic

public class CalculatorService {
    // now it's OOP (drop the static)
    public double calculate(double num1, double num2, char operator) {
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