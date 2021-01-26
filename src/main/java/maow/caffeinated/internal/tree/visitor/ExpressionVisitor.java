package maow.caffeinated.internal.tree.visitor;

import com.squareup.javapoet.TypeSpec;
import maow.caffeinated.internal.expression.ExpressionFactory;
import maow.caffeinated.internal.expression.ExpressionHandler;
import maow.caffeinated.internal.parser.JavaParserBaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.stream.Collectors;

import static maow.caffeinated.internal.parser.JavaParser.*;

public final class ExpressionVisitor extends JavaParserBaseVisitor<TypeSpec> {
    @Override
    public TypeSpec visitTypeDeclaration(TypeDeclarationContext ctx) {
        final ClassDeclarationContext classCtx = ctx.classDeclaration();
        if (classCtx != null) {
            final List<String> ids = ctx.cafExpression().stream()
                    .map(CafExpressionContext::IDENTIFIER)
                    .map(ParseTree::getText)
                    .collect(Collectors.toList());
            final ExpressionHandler handler = new ExpressionHandler(classCtx);
            return ExpressionFactory.execute(ids, handler, classCtx.classBody());
        }
        return super.visitTypeDeclaration(ctx);
    }
}
