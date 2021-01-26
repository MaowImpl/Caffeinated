package maow.caffeinated.internal.expression;

import com.squareup.javapoet.TypeSpec;
import maow.caffeinated.internal.expression.types.DataExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static maow.caffeinated.internal.parser.JavaParser.*;

public final class ExpressionFactory {
    private static final Map<String, Expression> EXPRESSIONS = new HashMap<>();

    public static TypeSpec execute(List<String> ids, ExpressionHandler handler, ClassBodyContext ctx) {
        final PriorityQueue<Expression> queue = new PriorityQueue<>();
        for (String id : ids) {
            final Expression expression = EXPRESSIONS.get(id);
            if (expression != null) queue.add(expression);
        }
        for (Expression expression : queue) handler.execute(expression, ctx);
        return handler.build();
    }

    static void add(Expression... expressions) {
        for (Expression expression : expressions) {
            EXPRESSIONS.put(expression.getName(), expression);
        }
    }

    static {
        add(new DataExpression());
    }
}
