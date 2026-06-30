package com.mimicvm.vm;

public final class Stack {

    private final int[] elements;
    private int top = 0;

    public Stack(int max) {
        this.elements = new int[max];
    }

    public void push(int value) {
        elements[top++] = value;
    }

    public int pop() {
        return elements[--top];
    }
}
