package hw7.Main;

import exceptions.*;
import expression.TripleExpression;
import parser.ExpressionParser;
import parser.Parser;

public class Main {
    public static void main(String[] args) throws ParsingException, OverflowException, BracesException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException {
        Parser parser = new ExpressionParser();
        System.out.println(parser.parse("3").evaluate(0,0,0));
    }
}
