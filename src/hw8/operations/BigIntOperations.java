package hw8.operations;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

import java.math.BigInteger;


public class BigIntOperations implements Operations<BigInteger> {
    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger div(BigInteger x, BigInteger y) throws DivisionByZeroException {
        if (y.equals(BigInteger.ZERO)){
            throw new DivisionByZeroException();
        }
        return x.divide(y);
    }

    @Override
    public BigInteger neg(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger cnt(BigInteger x) throws OverflowException {
        return  BigInteger.valueOf(x.bitCount());
    }

    public BigInteger parseNum(String s) throws NumberFormatException {
        return new BigInteger(s);
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) throws OverflowException {
        return x.min(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) throws OverflowException {
        return x.max(y);
    }
}
