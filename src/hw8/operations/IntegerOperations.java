package hw8.operations;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;


public class IntegerOperations implements Operations<Integer> {

    private boolean check;

    public IntegerOperations(boolean check){
        this.check = check;
    }

    private void checkAdd(int x, int y) throws OverflowException {
        if (!check){
            return;
        }
        if ((x > 0 && y > 0 && Integer.MAX_VALUE - x < y) || (x < 0 && y < 0 && Integer.MIN_VALUE - x > y)) {
            throw new OverflowException();
        }
    }

    private void checkDiv(int x, int y) throws DivisionByZeroException, OverflowException {
        if (y == 0) {
            throw new DivisionByZeroException();
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            if (!check){
                return;
            }
            throw new OverflowException();
        }
    }

    private void checkLog(int x, int y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        if (y <= 0 || x <= 0) {
            throw new UnexpectedNegativeNumberException(y);
        } else if (y == 1) {
            throw new MathException("Try to log with 1");
        }
    }

    private void checkMul(int x, int y) throws OverflowException {
        if (!check){
            return;
        }
        if (x > 0 && y > 0) {
            if (Integer.MAX_VALUE / x < y) {
                throw new OverflowException();
            }
        } else if (x < 0 && y < 0) {
            if (Integer.MAX_VALUE / x > y) {
                throw new OverflowException();
            }
        } else if (x != 0 && y != 0) {
            if (x < 0) {
                if (Integer.MIN_VALUE / y > x) {
                    throw new OverflowException();
                }
            } else if (y < 0) {
                if (Integer.MIN_VALUE / x > y) {
                    throw new OverflowException();
                }
            }
        }
    }

    private void checkPow(int x, int y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        if (y < 0) {
            throw new UnexpectedNegativeNumberException(y);
        } else if (x == 0 && y == 0) {
            throw new MathException("try to power(0, 0)");
        }
    }

    private void checkNeg(int x) throws OverflowException {
        if (!check){
            return;
        }
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    private void checkSub(int x, int y) throws OverflowException {
        if (!check){
            return;
        }
        if ((x >= 0 && y < 0 && -Integer.MAX_VALUE + x > y) || (x <= 0 && y > 0 && (Integer.MIN_VALUE - x) > -y)) {
            throw new OverflowException();
        }
    }

    @Override
    public Integer add(Integer x, Integer y) throws OverflowException {

        checkAdd(x, y);
        return x + y;
    }

    @Override
    public Integer sub(Integer x, Integer y) throws OverflowException {
        checkSub(x, y);
        return x - y;
    }

    @Override
    public Integer mul(Integer x, Integer y) throws OverflowException {
        checkMul(x, y);
        return x * y;
    }

    @Override
    public Integer div(Integer x, Integer y) throws DivisionByZeroException, OverflowException {
        checkDiv(x, y);
        return x / y;
    }

    @Override
    public Integer neg(Integer x) throws OverflowException {
        checkNeg(x);
        return -x;
    }

    @Override
    public Integer cnt(Integer x) throws OverflowException {
        return Integer.bitCount(x);
    }

    public Integer parseNum(String s) throws NumberFormatException {
        return Integer.parseInt(s);
    }

    @Override
    public Integer min(Integer x, Integer y) throws OverflowException {
        return Math.min(x, y);
    }

    @Override
    public Integer max(Integer x, Integer y) throws OverflowException {
        return Math.max(x, y);
    }
}
