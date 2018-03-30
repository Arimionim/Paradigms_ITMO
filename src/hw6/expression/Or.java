package hw6.expression;

public class Or extends AbstractBinaryOperator {

    public Or(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected int calculate(int x, int y) {
        return x | y;

    }
}