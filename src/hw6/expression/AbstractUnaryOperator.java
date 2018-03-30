package hw6.expression;

public abstract class AbstractUnaryOperator implements TripleExpression {
    private final TripleExpression expression;

    AbstractUnaryOperator(TripleExpression x) {
        expression = x;
    }

    protected abstract int calculate(int x);

    public int evaluate(int x, int y, int z) {
        return calculate(expression.evaluate(x, y, z));
    }
}
