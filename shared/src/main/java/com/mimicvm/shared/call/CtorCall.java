package com.mimicvm.shared.call;

import java.util.Objects;

public record CtorCall(String owner, String desc) implements ICall {

    public CtorCall {
        Objects.requireNonNull(owner, "owner must not be null");
        Objects.requireNonNull(desc, "desc must not be null");
    }

    @Override
    public String name() {
        return "<init>";
    }
}
