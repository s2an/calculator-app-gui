// Switched to the newer junit5
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    private final CalculatorService calculator = new CalculatorService();

    @Test
    void testAddition() {
        assertEquals(5, calculator.calculate(2, 3, '+'));
    }

    @Test
    void testSubtraction() {
        assertEquals(2, calculator.calculate(5, 3,'-'));
    }

    @Test
    void testMultiplication() {
        assertEquals(15, calculator.calculate(5, 3, '*'));
    }

    @Test
    void testDivision() {
        assertEquals(2, calculator.calculate(6, 3, '/'));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate(6, 0, '/'));
    }

    @Test
    void testUnknownOperator() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(6, 0, 'a'));
    }
}
