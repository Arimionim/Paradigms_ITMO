package hw8.expression;

import exceptions.*;

public strictfp class Variable<T> implements TripleExpression<T> {
    private final String name;

    public Variable(String x) {
        name = x;
    }

    @Override
    public T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException, ParsingException {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new ParsingException("Unknown variable");
        }
    }
}