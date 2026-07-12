package com.mimicvm.shared.code;

/**
 * @param typeNames all type names referenced in the module
 * @param constants constant pool
 */
public record VModule(String[] typeNames, String[] constants, VMethod[] methods) {

    public VModule {
        if (methods == null) {
            throw new IllegalArgumentException("methods must not be null");
        }
        if (typeNames == null) {
            typeNames = new String[0];
        }
        if (constants == null) {
            constants = new String[0];
        }
    }

    public VModule(String[] typeNames, VMethod[] methods) {
        this(typeNames, new String[0], methods);
    }

    public VModule(VMethod[] methods) {
        this(new String[0], new String[0], methods);
    }

    public VMethod method(int idx) {
        return methods[idx];
    }

    public String typeName(int idx) {
        return typeNames[idx];
    }

    public String constant(int idx) {
        return constants[idx];
    }
}
