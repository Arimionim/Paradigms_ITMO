package hw6.expression;

public class Xor extends AbstractBinaryOperator {

    public Xor(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected int calculate(int x, int y) {
        return x ^ y;

    }
}