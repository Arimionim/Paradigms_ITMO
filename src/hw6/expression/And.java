package hw6.expression;

public class And extends AbstractBinaryOperator {

    public And(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected int calculate(int x, int y) {
        return x & y;

    }
}