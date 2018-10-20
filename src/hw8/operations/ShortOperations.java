package hw8.operations;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;


public class ShortOperations implements Operations<Short> {

    private final boolean CHECK = false;


    private void checkAdd(Short x, Short y) throws OverflowException {
        if (!CHECK){
            return;
        }
        if ((x > 0 && y > 0 && Short.MAX_VALUE - x < y) || (x < 0 && y < 0 && Short.MIN_VALUE - x > y)) {
            throw new OverflowException();
        }
    }

    private void checkDiv(Short x, Short y) throws DivisionByZeroException, OverflowException {
        if (y == 0) {
            throw new DivisionByZeroException();
        }
        if (x == Short.MIN_VALUE && y == -1) {
            if (!CHECK){
                return;
            }
            throw new OverflowException();
        }
    }

    private void checkLog(Short x, Short y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        if (y <= 0 || x <= 0) {
            throw new UnexpectedNegativeNumberException(Short.toString(y));
        } else if (y == 1) {
            throw new MathException("Try to log with 1");
        }
    }

    private void checkMul(Short x, Short y) throws OverflowException {
        if (!CHECK){
            return;
        }
        if (x > 0 && y > 0) {
            if (Short.MAX_VALUE / x < y) {
                throw new OverflowException();
            }
        } else if (x < 0 && y < 0) {
            if (Short.MAX_VALUE / x > y) {
                throw new OverflowException();
            }
        } else if (x != 0 && y != 0) {
            if (x < 0) {
                if (Short.MIN_VALUE / y > x) {
                    throw new OverflowException();
                }
            } else if (y < 0) {
                if (Short.MIN_VALUE / x > y) {
                    throw new OverflowException();
                }
            }
        }
    }

    private void checkNeg(Short x) throws OverflowException {
        if (!CHECK){
            return;
        }
        if (x == Short.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    private void checkSub(Short x, Short y) throws OverflowException {
        if (!CHECK){
            return;
        }
        if ((x >= 0 && y < 0 && -Short.MAX_VALUE + x > y) || (x <= 0 && y > 0 && (Short.MIN_VALUE - x) > -y)) {
            throw new OverflowException();
        }
    }

    @Override
    public Short add(Short x, Short y) throws OverflowException {

        checkAdd(x, y);
        return (short) (x + y);
    }

    @Override
    public Short sub(Short x, Short y) throws OverflowException {
        checkSub(x, y);
        return (short) (x - y);
    }

    @Override
    public Short mul(Short x, Short y) throws OverflowException {
        checkMul(x, y);
        return (short) (x * y);
    }

    @Override
    public Short div(Short x, Short y) throws DivisionByZeroException, OverflowException {
        checkDiv(x, y);
        return (short) (x / y);
    }

    @Override
    public Short neg(Short x) throws OverflowException {
        checkNeg(x);
        return (short) (-x);
    }

    @Override
    public Short cnt(Short x) throws OverflowException {
        return (short) Long.bitCount(Short.toUnsignedLong(x));
    }

    public Short parseNum(String s) throws NumberFormatException {
        return (short) Integer.parseInt(s);
    }

    @Override
    public Short min(Short x, Short y) throws OverflowException {
        return (short) Math.min(x, y);
    }

    @Override
    public Short max(Short x, Short y) throws OverflowException {
        return (short) Math.max(x, y);
    }
}
