package com.example.onpcalculator;

import java.util.*;

public class PostfixCalculator {

    public static double evaluatePostfix(String[] tokens) {
        Stack stack = new Stack();

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(token);
            } else {
                double b = Double.parseDouble(stack.pop());
                double a = Double.parseDouble(stack.pop());
                stack.push(String.valueOf(applyOperator(a, b, token)));
            }
        }

        if (stack.getSize() != 1) {
            throw new IllegalStateException("Invalid postfix expression");
        }

        return Double.parseDouble(stack.pop());
    }

    public static String infixToPostfixString(String infixExpression) {
        List<String> infixTokens = tokenize(infixExpression);
        Stack stack = new Stack();
        List<String> postfixTokens = new ArrayList<>();
        int operandCount = 0;
        int operatorCount = 0;

        for (String token : infixTokens) {
            if (isNumeric(token)) {
                operandCount++;
                postfixTokens.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.top().equals("(")) {
                    postfixTokens.add(stack.pop());
                }
                if (!stack.isEmpty() && stack.top().equals("(")) {
                    stack.pop(); // Remove the '(' from the stack
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses in infix expression");
                }
            } else if (isOperator(token.charAt(0))) {
                operatorCount++;
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.top())) {
                    postfixTokens.add(stack.pop());
                }
                stack.push(token);
            } else {
                throw new IllegalArgumentException("Invalid token in infix expression: " + token);
            }
        }

        // Pop remaining operators from the stack
        while (!stack.isEmpty()) {
            String top = stack.pop();
            if (top.equals("(") || top.equals(")")) {
                throw new IllegalArgumentException("Mismatched parentheses in infix expression");
            }
            postfixTokens.add(top);
        }

        // Validate operator-operand balance
        if (operandCount != operatorCount + 1) {
            throw new IllegalArgumentException("Invalid expression: Incorrect number of operands for operators");
        }

        return String.join(" ", postfixTokens);
    }

    private static List<String> tokenize(String infixExpression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char ch : infixExpression.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                continue;
            }
            if (isOperator(ch) || ch == '(' || ch == ')') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(String.valueOf(ch));
            } else {
                currentToken.append(ch);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0; // Lower precedence for parentheses
        }
    }

    private static double applyOperator(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
