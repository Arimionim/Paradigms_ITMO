package hw5.expression;

public abstract class AbstractBinaryOperator implements SomeExpression {
    private final SomeExpression firstExpression;
    private final SomeExpression secondExpression;

    AbstractBinaryOperator(SomeExpression x, SomeExpression y) {
        firstExpression = x;
        secondExpression = y;
    }

    protected abstract double calculate(double x, double y);

    protected abstract int calculate(int x, int y);

    public double evaluate(double x) {
        return calculate(firstExpression.evaluate(x), secondExpression.evaluate(x));
    }

    public int evaluate(int x) {
        return calculate(firstExpression.evaluate(x), secondExpression.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return calculate(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
}