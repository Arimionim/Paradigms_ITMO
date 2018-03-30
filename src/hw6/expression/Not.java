package hw6.expression;

public class Not extends AbstractUnaryOperator {

    public Not(TripleExpression x) {
        super(x);
    }

    @Override
    protected int calculate(int x) {
        return ~x;
    }
}
