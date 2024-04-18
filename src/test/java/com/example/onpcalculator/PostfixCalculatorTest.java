package com.example.onpcalculator;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;
public class PostfixCalculatorTest {
    @Test
    public void testStackOperations() {
        Stack stack = new Stack();

        assertTrue(stack.isEmpty(), "isEmpty() doesn't return true on empty stack");
        assertThrows(EmptyStackException.class, stack::pop,
                "pop() doesn't throw exception on empty stack");
        assertThrows(EmptyStackException.class, stack::top,
                "pop() doesn't throw exception on empty stack");

        // Push elements onto the stack
        String pushedItem = "3";
        stack.push(pushedItem);
        assertSame(stack.top(),pushedItem, "stack.top() doesn't return correct value");
        assertSame(stack.top(),pushedItem, "stack.top() doesn't return correct value");
        assertFalse(stack.isEmpty(), "stack doesn't return false on filled stack");

        // Pop elements from the stack
        assertSame(pushedItem, stack.pop());
        assertTrue(stack.isEmpty(), "isEmpty() returns false on empty stack");
        assertThrows(EmptyStackException.class, stack::pop,
                "pop() doesn't throw exception on empty stack");

        String pushedItem1 = "6";
        String pushedItem2 = "3";
        String pushedItem3 = "7";
        stack.push(pushedItem1);
        stack.push(pushedItem2);
        stack.push(pushedItem3);

        assertSame(stack.pop(),pushedItem3,"pop doesn't return correct item 3");
        assertSame(stack.pop(),pushedItem2,"pop doesn't return correct item 2");
        assertSame(stack.pop(),pushedItem1,"pop doesn't return correct item 1");

        stack.push(null);
        assertSame(stack.pop(),null,"pop() doesn't return null");
    }

    @Test
    public void testPostfixEvaluation() {
        String[] postfixExpr = {"3", "5", "+"};
        assertEquals(8.0, PostfixCalculator.evaluatePostfix(postfixExpr));

        String[] postfixExprFloat = {"5","2","4.5","+","*","2.5","/"};
        assertEquals(13.0, PostfixCalculator.evaluatePostfix(postfixExprFloat));

        String[] postfixExprComplex = {"7", "3", "+", "5", "2", "-", "*", "4", "/"};
        assertEquals(7.5, PostfixCalculator.evaluatePostfix(postfixExprComplex));

        String[] invalidExpr = {"3", "+"}; // Invalid postfix expression
        assertThrows(EmptyStackException.class, () -> PostfixCalculator.evaluatePostfix(invalidExpr));

        String[] divByZeroExpr = {"5", "0", "/"}; // Division by zero
        assertThrows(ArithmeticException.class, () -> PostfixCalculator.evaluatePostfix(divByZeroExpr));
    }

    @Test
    public void testInfixToPostfixString_SimpleExpression() {
        String infixExpression = "3 + 5 * (2 - 4)";
        String expectedPostfix = "3 5 2 4 - * +";
        String actualPostfix = PostfixCalculator.infixToPostfixString(infixExpression);
        assertEquals(expectedPostfix, actualPostfix);
    }

    @Test
    public void testInfixToPostfixString_ComplexExpression() {
        String infixExpression = "(7 + 3) * (5 - 2) / 4";
        String expectedPostfix = "7 3 + 5 2 - * 4 /";
        String actualPostfix = PostfixCalculator.infixToPostfixString(infixExpression);
        assertEquals(expectedPostfix, actualPostfix);
    }

    @Test
    public void testInfixToPostfixString_WithFloatingPointNumbers() {
        String infixExpression = "3.5 * (2 + 4.2) / 1.5";
        String expectedPostfix = "3.5 2 4.2 + * 1.5 /";
        String actualPostfix = PostfixCalculator.infixToPostfixString(infixExpression);
        assertEquals(expectedPostfix, actualPostfix);
    }

    @Test
    public void testInfixToPostfixString_WithInvalidExpression() {
        String infixExpression = "3 + 5 *";
        assertThrows(IllegalArgumentException.class, () -> {
            PostfixCalculator.infixToPostfixString(infixExpression);
        });
    }

    @Test
    public void testInfixToPostfixString_WithMismatchedParentheses() {
        String infixExpression = "3 + (5 * 2";
        assertThrows(IllegalArgumentException.class, () -> {
            PostfixCalculator.infixToPostfixString(infixExpression);
        });
    }
}
