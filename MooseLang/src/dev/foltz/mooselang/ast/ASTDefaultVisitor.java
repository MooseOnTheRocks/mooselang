package dev.foltz.mooselang.ast;

import dev.foltz.mooselang.ast.expression.*;
import dev.foltz.mooselang.ast.expression.literals.*;
import dev.foltz.mooselang.ast.statement.*;
import dev.foltz.mooselang.ast.typing.ASTTypeValue;
import dev.foltz.mooselang.ast.typing.ASTTypeName;
import dev.foltz.mooselang.ast.typing.ASTTypeRecord;
import dev.foltz.mooselang.ast.typing.ASTTypeUnion;

import java.util.function.Function;

public class ASTDefaultVisitor<T> implements ASTVisitor<T> {
    public final Function<ASTNode, T> defaultOp;

    public ASTDefaultVisitor(Function<ASTNode, T> defaultOp) {
        this.defaultOp = defaultOp;
    }

    @Override
    public T visit(ASTExprNone node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprBool node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprInt node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprString node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprRecord node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprName node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprIfThenElse node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprTyped<? extends ASTExpr> node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprCall node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTExprFieldAccess node) {
        return defaultOp.apply(node);
    }


    @Override
    public T visit(ASTStmtLet node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTStmtFuncDef node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTStmtTypeDef node) {
        return defaultOp.apply(node);
    }


    @Override
    public T visit(ASTTypeName node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTTypeUnion node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTTypeRecord node) {
        return defaultOp.apply(node);
    }

    @Override
    public T visit(ASTTypeValue node) {
        return defaultOp.apply(node);
    }
}
