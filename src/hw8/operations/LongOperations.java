package hw8.operations;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;


public class LongOperations implements Operations<Long> {

    private final boolean CHECK = false;


    private void checkAdd(Long x, Long y) throws OverflowException {
        if (!CHECK){
            return;
        }
        if ((x > 0 && y > 0 && Long.MAX_VALUE - x < y) || (x < 0 && y < 0 && Long.MIN_VALUE - x > y)) {
            throw new OverflowException();
        }
    }

    private void checkDiv(Long x, Long y) throws DivisionByZeroException, OverflowException {
        if (y == 0) {
            throw new DivisionByZeroException();
        }
        if (x == Long.MIN_VALUE && y == -1) {
            if (!CHECK){
                return;
            }
            throw new OverflowException();
        }
    }

    private void checkLog(Long x, Long y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        if (y <= 0 || x <= 0) {
            throw new UnexpectedNegativeNumberException(Long.toString(y));
        } else if (y == 1) {
            throw new MathException("Try to log with 1");
        }
    }

    private void checkMul(Long x, Long y) throws OverflowException {
        if (!CHECK){
            return;
        }
        if (x > 0 && y > 0) {
            if (Long.MAX_VALUE / x < y) {
                throw new OverflowException();
            }
        } else if (x < 0 && y < 0) {
            if (Long.MAX_VALUE / x > y) {
                throw new OverflowException();
            }
        } else if (x != 0 && y != 0) {
            if (x < 0) {
                if (Long.MIN_VALUE / y > x) {
                    throw new OverflowException();
                }
            } else if (y < 0) {
                if (Long.MIN_VALUE / x > y) {
                    throw new OverflowException();
                }
            }
        }
    }

    private void checkNeg(Long x) throws OverflowException {
        if (!CHECK){
            return;
        }
        if (x == Long.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    private void checkSub(Long x, Long y) throws OverflowException {
        if (!CHECK){
            return;
        }
        if ((x >= 0 && y < 0 && -Long.MAX_VALUE + x > y) || (x <= 0 && y > 0 && (Long.MIN_VALUE - x) > -y)) {
            throw new OverflowException();
        }
    }

    @Override
    public Long add(Long x, Long y) throws OverflowException {

        checkAdd(x, y);
        return x + y;
    }

    @Override
    public Long sub(Long x, Long y) throws OverflowException {
        checkSub(x, y);
        return x - y;
    }

    @Override
    public Long mul(Long x, Long y) throws OverflowException {
        checkMul(x, y);
        return x * y;
    }

    @Override
    public Long div(Long x, Long y) throws DivisionByZeroException, OverflowException {
        checkDiv(x, y);
        return x / y;
    }

    @Override
    public Long neg(Long x) throws OverflowException {
        checkNeg(x);
        return -x;
    }

    @Override
    public Long cnt(Long x) throws OverflowException {
        return (long) Long.bitCount(x);
    }

    public Long parseNum(String s) throws NumberFormatException {
        return Long.parseLong(s);
    }

    @Override
    public Long min(Long x, Long y) throws OverflowException {
        return Math.min(x, y);
    }

    @Override
    public Long max(Long x, Long y) throws OverflowException {
        return Math.max(x, y);
    }
}
