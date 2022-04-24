package dev.foltz.mooselang.interpreter.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RTFuncDispatcher extends RTObject {
    public final String funcName;
    public final List<RTFuncDef> funcDefs;

    public RTFuncDispatcher(String funcName) {
        this.funcName = funcName;
        funcDefs = new ArrayList<>();
    }

    public void addFuncDef(RTFuncDef rtFuncDef) {
        for (RTFuncDef funcDef : funcDefs) {
            if (funcDef.funcParams.equals(rtFuncDef.funcParams)) {
                throw new IllegalStateException("Cannot add function definition with same parameters: " + rtFuncDef.funcName + ": " + rtFuncDef.funcParams);
            }
        }
        funcDefs.add(rtFuncDef);
    }

    public Optional<RTFunc> dispatch(List<RTObject> args) {
        // TODO: Better function dispatch based on arguments.
        //       Currently just returning the first matching function.
        for (RTFuncDef funcDef : funcDefs) {
//            System.out.println("Comparing: " + funcDef.funcParams);
            if (funcDef.accepts(args)) {
                return Optional.of(funcDef);
            }
        }

        return Optional.empty();
    }
}
