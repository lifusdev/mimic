package com.mimicvm.vm.call;

import com.mimicvm.shared.call.StaticCall;
import com.mimicvm.shared.type.Value;

@FunctionalInterface
public interface ICallInvoker {

    Value invoke(StaticCall call, Value... args);
}
