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

    public Value defaultValue() {
        return switch (this) {
            case I32 -> Value.i32(0);
            case I64 -> Value.i64(0);
            case F32 -> Value.f32(0);
            case F64 -> Value.f64(0);
            case REF -> Value.NULL;
            case VOID -> throw new IllegalStateException("void has no default value");
        };
    }
}
