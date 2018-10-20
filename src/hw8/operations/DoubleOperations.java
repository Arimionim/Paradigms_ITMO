package hw8.operations;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;


public class DoubleOperations implements Operations<Double> {
    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) throws DivisionByZeroException {
        return x / y;
    }

    @Override
    public Double neg(Double x) {
        return -x;
    }

    @Override
    public Double cnt(Double x) throws OverflowException {
        return (double) Long.bitCount(Double.doubleToLongBits(x));
    }

    public Double parseNum(String s)  {
        return Double.parseDouble(s);
    }

    @Override
    public Double min(Double x, Double y) {
        return Math.min(x, y);
    }

    @Override
    public Double max(Double x, Double y) {
        return Math.max(x, y);
    }
}
