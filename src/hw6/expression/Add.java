package hw6.expression;

public class Add extends AbstractBinaryOperator {

    public Add(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected int calculate(int x, int y) {
        return x + y;

    }
}