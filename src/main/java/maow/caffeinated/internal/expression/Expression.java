package maow.caffeinated.internal.expression;

import com.squareup.javapoet.TypeSpec;

import static maow.caffeinated.internal.parser.JavaParser.*;

public interface Expression extends Comparable<Expression> {
    String getName();
    String[] getEmittedFlags();
    float getPriority();
    void execute(TypeSpec.Builder builder, ClassBodyContext ctx, String... flags);
}
