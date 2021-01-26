package maow.caffeinated.internal.expression;

import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static maow.caffeinated.internal.parser.JavaParser.*;

public final class ExpressionHandler {
    private final List<String> flags = new ArrayList<>();
    private final TypeSpec.Builder builder;

    public ExpressionHandler(ClassDeclarationContext ctx) {
        final String name = ctx.IDENTIFIER().getText();
        builder = TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC);
    }

    public void execute(Expression expression, ClassBodyContext ctx) {
        expression.execute(builder, ctx, flags.toArray(new String[0]));
        addFlags(expression.getEmittedFlags());
    }

    private void addFlags(String[] flags) {
        final List<String> flagList = Arrays.asList(flags);
        this.flags.addAll(flagList);
    }

    public TypeSpec build() {
        return builder.build();
    }
}
