package com.mimicvm.transformer.translator;

import com.mimicvm.annotations.VirtualizeMe;
import com.mimicvm.shared.code.VMethod;
import com.mimicvm.transformer.translator.table.ConstantPool;
import com.mimicvm.transformer.translator.table.IFieldIdx;
import com.mimicvm.transformer.translator.table.IMethodIdx;
import com.mimicvm.transformer.translator.table.ITypeIdx;
import org.objectweb.asm.*;

import java.util.function.Consumer;

public final class ClassTranslator extends ClassVisitor {

    private final IMethodIdx table;
    private final IFieldIdx fields;
    private final IFieldIdx statics;
    private final ITypeIdx types;
    private final ConstantPool strings;
    private final Consumer<VMethod> onMethod;

    public ClassTranslator(IMethodIdx table, IFieldIdx fields, IFieldIdx statics, ITypeIdx types, ConstantPool strings, Consumer<VMethod> onMethod) {
        super(Opcodes.ASM9);
        this.table = table;
        this.fields = fields;
        this.statics = statics;
        this.types = types;
        this.strings = strings;
        this.onMethod = onMethod;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("<clinit>")) {
            return null;
        }

        return new MethodVisitor(Opcodes.ASM9) {
            @Override
            public AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
                if (annotationDescriptor.equals(Type.getDescriptor(VirtualizeMe.class))) {
                    mv = new MethodTranslator(table, fields, statics, types, strings, access, descriptor, onMethod);
                }

                return super.visitAnnotation(annotationDescriptor, visible);
            }
        };
    }
}
