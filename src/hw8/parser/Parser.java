package hw8.parser;

import exceptions.BracesException;
import exceptions.MissingOperandException;
import exceptions.OverflowException;
import exceptions.ParsingException;
import expression.TripleExpression;

public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException, OverflowException, BracesException;
}
