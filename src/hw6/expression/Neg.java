package hw6.expression;

public class Neg extends AbstractUnaryOperator {

    public Neg(TripleExpression x) {
        super(x);
    }

    @Override
    protected int calculate(int x) {
        return -x;
    }
}
