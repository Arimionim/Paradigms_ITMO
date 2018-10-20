package hw8.operations;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public interface Operations<T> {
    T add(final T x, final T y) throws OverflowException;

    T sub(final T x, final T y) throws OverflowException;

    T mul(final T x, final T y) throws OverflowException;

    T div(final T x, final T y) throws DivisionByZeroException, OverflowException;

    T neg(final T x) throws OverflowException;

    T cnt(final T x) throws OverflowException;

    T parseNum(String s) throws NumberFormatException;

    T min(final T x, final T y) throws OverflowException;

    T max(final T x, final T y) throws OverflowException;
}
