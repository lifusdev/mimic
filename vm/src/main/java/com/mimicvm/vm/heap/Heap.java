package com.mimicvm.vm.heap;

import com.mimicvm.shared.type.Type;
import com.mimicvm.shared.type.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple heap.
 * id 0 is reserved for null
 */
public final class Heap {

    private final List<VObject> objects = new ArrayList<>();

    public Heap() {
        objects.add(null);
    }

    public int alloc(int fieldCount) {
        objects.add(new VObject(fieldCount));
        return objects.size() - 1;
    }

    public int alloc(int fieldCount, int typeIdx) {
        objects.add(new VObject(fieldCount, typeIdx));
        return objects.size() - 1;
    }

    public int alloc(int fieldCount, int typeIdx, Type[] fieldTypes) {
        objects.add(new VObject(fieldCount, typeIdx, fieldTypes));
        return objects.size() - 1;
    }

    public int allocArray(int len, int typeIdx, Value initVal) {
        objects.add(new VObject(len, typeIdx, initVal));
        return objects.size() - 1;
    }

    public int allocMultiarray(int[] lens, int[] typeIndices, Value[] initVals) {
        if (lens.length == 0 || lens.length != typeIndices.length || lens.length != initVals.length) {
            throw new IllegalArgumentException("Multiarray metadata does not match");
        }

        //check
        for (final int len : lens) {
            if (len < 0) {
                throw new NegativeArraySizeException(String.valueOf(len));
            }
        }

        return allocMultiarray(lens, typeIndices, initVals, 0);
    }

    private int allocMultiarray(int[] lens, int[] typeIndices, Value[] initVals, int d) {
        final int ref = allocArray(lens[d], typeIndices[d], initVals[d]);

        if (d + 1 < lens.length) {
            final VObject array = get(ref);

            /*
              Each slot points to its own internal array
             */
            for (int i = 0; i < lens[d]; i++) {
                array.field(i, Value.ref(allocMultiarray(lens, typeIndices, initVals, d + 1)));
            }
        }

        return ref;
    }

    public VObject get(int id) {
        if (id == 0) {
            throw new NullPointerException("null reference");
        }
        return objects.get(id);
    }
}
