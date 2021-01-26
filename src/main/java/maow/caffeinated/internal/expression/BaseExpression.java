package maow.caffeinated.internal.expression;

public abstract class BaseExpression implements Expression {
    @Override
    public String[] getEmittedFlags() {
        return new String[0];
    }

    @Override
    public float getPriority() {
        return 0.0f;
    }

    @Override
    public int compareTo(Expression o) {
        return Float.compare(getPriority(), o.getPriority());
    }
}
