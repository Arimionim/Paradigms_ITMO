package hw6.expression;

public class Abs extends AbstractUnaryOperator{

    public Abs(TripleExpression x){
        super(x);
    }

    @Override
    protected int calculate(int x) {
        return Math.abs(x);
    }
}
