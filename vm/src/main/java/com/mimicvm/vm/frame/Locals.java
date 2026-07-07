package com.mimicvm.vm.frame;

import com.mimicvm.shared.type.Value;

public final class Locals {

    private final Value[] slots;

    public Locals(int max) {
        this.slots = new Value[max];
    }

    public Value get(int idx) {
        return slots[idx];
    }

    public void set(int idx, Value value) {
        slots[idx] = value;
    }
}
