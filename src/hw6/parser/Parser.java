package hw6.parser;

import hw6.expression.TripleExpression;

public interface Parser {
    TripleExpression parse(String expression);
}
