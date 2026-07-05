package com.mimicvm.transformer;

import com.mimicvm.shared.method.VMethod;
import com.mimicvm.shared.method.VModule;
import com.mimicvm.shared.type.Value;
import com.mimicvm.vm.Interpreter;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.objectweb.asm.Opcodes.*;


//class Hi {
//    static int three() {
//        return 1 + 2;
//    }
//}
class TransformerTest {

    @Test
    void translate1() {
        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V21, ACC_SUPER, "com/mimicvm/transformer/Hi", null, "java/lang/Object", null);

//        {
//            methodVisitor = classWriter.visitMethod(0, "<init>", "()V", null, null);
//            methodVisitor.visitCode();
//            methodVisitor.visitVarInsn(ALOAD, 0);
//            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
//            methodVisitor.visitInsn(RETURN);
//            methodVisitor.visitMaxs(1, 1);
//            methodVisitor.visitEnd();
//        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC, "three", "()I", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitInsn(ICONST_3);
            methodVisitor.visitInsn(IRETURN);
            methodVisitor.visitMaxs(1, 0);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();


        final byte[] bytes = classWriter.toByteArray();

        final List<byte[]> methods = new Transformer(bytes).translate();

        final VModule module = new VModule(new VMethod[]{
                new VMethod(0, 2, 0, methods.getFirst())
        });

        final Value result = new Interpreter(module, 0).run();

        assertEquals(Value.i32(3), result);
    }
}
