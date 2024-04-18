package com.example.onpcalculator;

import java.util.EmptyStackException;

public class Stack {

    private final String[] stackArray;
    private int top;
    private int maxCapacity = 200;


    public Stack() {
        stackArray = new String[maxCapacity];
        top = -1;
    }

    public void push(String element) {
        if (top == stackArray.length - 1) {
            throw new StackOverflowError("Stack is full");
        }
        stackArray[++top] = element;
    }

    public String pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        String val = stackArray[top];
        stackArray[top] = "";
        top--;
        return val;
    }

    public String top() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackArray[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int getSize() {
        return top + 1;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}