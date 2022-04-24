package dev.foltz.mooselang.parser.ast.deconstructors;

import dev.foltz.mooselang.interpreter.Scope;
import dev.foltz.mooselang.interpreter.runtime.RTObject;
import dev.foltz.mooselang.parser.ast.ASTVisitor;
import dev.foltz.mooselang.parser.ast.expressions.ASTExprName;

public class ASTDeconName extends ASTDeconstructor {
    public final ASTExprName name;

    public ASTDeconName(ASTExprName name) {
        this.name = name;
    }

    @Override
    public RTObject deconstruct(RTObject rtObj, Scope scope) {
        if (!matches(rtObj)) {
            throw new IllegalStateException("Name deconstructor cannot accept: " + rtObj);
        }

        scope.bind(name.value, rtObj);
        return rtObj;
    }

    @Override
    public boolean matches(RTObject rtObj) {
        return true;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ASTDeconName{" +
                "name=" + name +
                '}';
    }
}
