package hw6.expression;

public class Count extends AbstractUnaryOperator {

    public Count(TripleExpression x) {
        super(x);
    }

    protected int calculate(int x) {
        return Integer.bitCount(x);
    }
}