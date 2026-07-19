package com.mimicvm.shared.type;

public enum Type {
    I32,
    I64,
    F32,
    F64,
    REF,
    VOID;

    public boolean isWide() {
        return this == I64 || this == F64;
    }
}
