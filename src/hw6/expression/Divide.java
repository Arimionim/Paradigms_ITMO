package hw6.expression;

public strictfp class Divide extends AbstractBinaryOperator {
    public Divide(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected int calculate(int x, int y) {
        return x / y;
    }
}