package hw6.expression;


public strictfp class Multiply extends AbstractBinaryOperator {
    public Multiply(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected double calculate(double x, double y) {
        return x * y;
    }

    protected int calculate(int x, int y) {
        return x * y;
    }
}