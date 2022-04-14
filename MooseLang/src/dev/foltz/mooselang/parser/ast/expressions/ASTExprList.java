package dev.foltz.mooselang.parser.ast.expressions;

import dev.foltz.mooselang.parser.ast.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class ASTExprList extends ASTExpr {
    public final List<ASTExpr> elements;

    public ASTExprList(List<ASTExpr> elements) {
        this.elements = List.copyOf(elements);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ASTExprList{" +
                "elements=" + elements +
                '}';
    }
}
