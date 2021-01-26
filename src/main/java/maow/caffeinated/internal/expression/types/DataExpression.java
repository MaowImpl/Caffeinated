package maow.caffeinated.internal.expression.types;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import maow.caffeinated.internal.expression.BaseExpression;
import maow.caffeinated.internal.util.Utils;

import static maow.caffeinated.internal.parser.JavaParser.*;
import static maow.caffeinated.internal.util.ContextUtils.*;
import static maow.caffeinated.internal.util.PoetUtils.*;

public final class DataExpression extends BaseExpression {
    @Override
    public String getName() {
        return "data";
    }

    @Override
    public void execute(TypeSpec.Builder builder, ClassBodyContext ctx, String... flags) {
        final MethodSpec.Builder ctor = ctor();
        forEachField(ctx, (modifiers, type, fieldCtx) -> {
            final boolean isFinal = Utils.contains(modifiers, "final");
            forEachVariable(fieldCtx,
                    (id, value) -> {
                        builder.addField(field(modifiers, type, id, value));
                        builder.addMethod(getter(type, id));
                        if (!isFinal) builder.addMethod(setter(type, id));
                        if ((isFinal) && value.equals("")) {
                            ctor
                                    .addParameter(param(type, id))
                                    .addStatement("this.$L = this.$L", id, id);
                        }
                    });
        });
        builder.addMethod(ctor.build());
    }
}
