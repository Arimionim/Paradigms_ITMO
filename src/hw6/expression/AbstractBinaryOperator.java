package hw6.expression;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private final TripleExpression firstExpression;
    private final TripleExpression secondExpression;

    AbstractBinaryOperator(TripleExpression x, TripleExpression y) {
        firstExpression = x;
        secondExpression = y;
    }

    protected abstract int calculate(int x, int y);

    public int evaluate(int x, int y, int z) {
        return calculate(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
}