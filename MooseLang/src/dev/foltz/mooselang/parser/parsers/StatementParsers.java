package dev.foltz.mooselang.parser.parsers;

import dev.foltz.mooselang.ast.expression.ASTExpr;
import dev.foltz.mooselang.ast.expression.ASTExprName;
import dev.foltz.mooselang.ast.expression.ASTExprTyped;
import dev.foltz.mooselang.ast.statement.ASTStmt;
import dev.foltz.mooselang.ast.statement.ASTStmtFuncDef;
import dev.foltz.mooselang.ast.statement.ASTStmtLet;
import dev.foltz.mooselang.ast.statement.ASTStmtTypeDef;
import dev.foltz.mooselang.ast.typing.ASTType;
import dev.foltz.mooselang.parser.IParser;
import dev.foltz.mooselang.parser.ParseResult;
import dev.foltz.mooselang.parser.ParseState;
import dev.foltz.mooselang.tokenizer.TokenType;

import java.util.List;

import static dev.foltz.mooselang.parser.ParseResult.failure;
import static dev.foltz.mooselang.parser.parsers.ExpressionParsers.*;
import static dev.foltz.mooselang.parser.parsers.ParserCombinators.*;
import static dev.foltz.mooselang.parser.parsers.TypingParsers.parseTypeTopLevel;

public class StatementParsers {
    public static final IParser<List<ASTStmt>> parseProgram = StatementParsers::parseProgram;
    public static final IParser<ASTStmt> parseStmt = StatementParsers::parseStmt;
    public static final IParser<ASTStmtLet> parseStmtLet = StatementParsers::parseStmtLet;
    public static final IParser<ASTStmtFuncDef> parseStmtFuncDef = StatementParsers::parseStmtFuncDef;
    public static final IParser<ASTStmtTypeDef> parseStmtTypeDef = StatementParsers::parseStmtTypeDef;

    public static ParseResult<List<ASTStmt>> parseProgram(ParseState state) {
        var r = all(parseStmt).map(ls -> (List<ASTStmt>) ls).parse(state);
        if (r.failed()) {
            return failure(r.state, "Failed to parse program: " + r.getMsg());
        }
        return r;
    }

    public static ParseResult<ASTStmt> parseStmt(ParseState state) {
        return any(
            parseStmtLet,
            parseStmtFuncDef,
            parseStmtTypeDef
        ).map(stmt -> (ASTStmt) stmt).mapErrorMsg(s -> "parseStmt failed: " + s).parse(state);
    }

    public static ParseResult<ASTStmtLet> parseStmtLet(ParseState state) {
        return sequence(
            expect(TokenType.T_KW_LET),
            parseExprName,
            optional(parseTypeAnnotation),
            expect(TokenType.T_EQUALS),
            parseExpr
        ).map(objs -> {
            var name = (ASTExprName) objs.get(1);
            var typeHint = objs.get(2);
            var body = (ASTExpr) objs.get(3);
            if (typeHint instanceof ASTType type) {
                return new ASTStmtLet(name, type, body);
            }
            else {
                return new ASTStmtLet(name, body);
            }
        }).mapErrorMsg(s -> "parseStmtLet failed: " + s).parse(state);
    }

    public static ParseResult<ASTStmtFuncDef> parseStmtFuncDef(ParseState state) {
        return sequence(
            expect(TokenType.T_KW_DEF),
            parseExprName,
            sequence(
                expect("("),
                sepBy0(
                    parseExprNameWithType,
                    expect(",")
                ),
                expect(")")
            ).map(objs -> objs.get(1)),
            optional(parseTypeAnnotation),
            expect("="),
            parseExpr
        ).map(objs -> {
            var name = (ASTExprName) objs.get(1);
            var typedParams = (List<ASTExprTyped<ASTExprName>>) objs.get(2);
            var retType = (ASTType) objs.get(3);
            var body = (ASTExpr) objs.get(5);
            return new ASTStmtFuncDef(name, typedParams, retType, body);
        }).parse(state);
    }

    public static ParseResult<ASTStmtTypeDef> parseStmtTypeDef(ParseState state) {
        return sequence(
            expect("type"),
            parseExprName,
            expect("="),
            parseTypeTopLevel
        ).map(objs -> {
            var name = (ASTExprName) objs.get(1);
            var type = (ASTType) objs.get(3);
            return new ASTStmtTypeDef(name, type);
        }).parse(state);
    }
}
