package hw5.expression;

public class Add extends AbstractBinaryOperator {

    public Add(SomeExpression x, SomeExpression y) {
        super(x, y);
    }

    protected double calculate(double x, double y) {
        return x + y;

    }

    protected int calculate(int x, int y) {
        return x + y;

    }
}