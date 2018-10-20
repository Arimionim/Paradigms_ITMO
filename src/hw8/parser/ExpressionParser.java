package hw8.parser;

import exceptions.BracesException;
import exceptions.MissingOperandException;
import exceptions.OverflowException;
import exceptions.ParsingException;
import expression.*;
import operations.Operations;

import java.util.EnumSet;


public class ExpressionParser<T> implements Parser<T> {

    private Operations<T> operation;
    private String expression;
    private int index;
    private T number;
    private Action curAction;
    private char variable;
    private int seqBalance;
    private EnumSet<Action> notToCloseBrace = EnumSet.of(Action.NEG, Action.ADD, Action.SUB, Action.MUL,
            Action.DIV, Action.XOR, Action.AND, Action.NOT,
            Action.OR, Action.COUNT, Action.OPEN_BRACE, Action.BEGIN,
            Action.POW, Action.LOG);

    public ExpressionParser(Operations<T> operation){
        this.operation = operation;
    }

    public TripleExpression<T> parse(String expression_) throws ParsingException, OverflowException, BracesException {
        expression = expression_;
        index = 0;
        seqBalance = 0;
        curAction = Action.BEGIN;
        return minMax();
    }

    private enum Action {
        BEGIN, NEG, MUL, DIV, ADD, SUB, VAR, NUM, OPEN_BRACE, CLOSE_BRACE, XOR, OR, AND, NOT, COUNT, POW, LOG, MIN, MAX
    }

    private void skipWhiteSpace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void getToken() throws ParsingException, OverflowException, BracesException {
        skipWhiteSpace();
        if (index >= expression.length()) {
            if (seqBalance != 0) {
                throw new BracesException();
            }
            return;
        }
        char symbol = expression.charAt(index);
        index++;
        switch (symbol) {
            case '*':
                if (index < expression.length() && expression.charAt(index) == '*') {
                    index++;
                    curAction = Action.POW;
                } else {
                    curAction = Action.MUL;
                }
                return;
            case '/':
                if (index < expression.length() && expression.charAt(index) == '/') {
                    index++;
                    curAction = Action.LOG;
                } else {
                    curAction = Action.DIV;
                }
                return;
            case '+':
                curAction = Action.ADD;
                return;
            case '-':
                if (curAction == Action.NUM || curAction == Action.VAR || curAction == Action.CLOSE_BRACE) {
                    curAction = Action.SUB;
                } else {
                    if (index >= expression.length()) {
                        throw new MissingOperandException(index);
                    }
                    if (Character.isDigit(expression.charAt(index))) {
                        String temp = getNum();
                        try {
                            number = operation.parseNum("-" + temp);
                        } catch (NumberFormatException NFE) {
                            throw new ParsingException("Illegal constant: " + temp + " at index: " + index);
                        }
                        curAction = Action.NUM;
                    } else {
                        curAction = Action.NEG;
                    }
                }
                return;
            case '(':
                if (curAction == Action.CLOSE_BRACE || curAction == Action.VAR || curAction == Action.NUM) {
                    throw new BracesException();
                }
                seqBalance++;
                curAction = Action.OPEN_BRACE;
                return;
            case ')':
                if (notToCloseBrace.contains(curAction)) {
                    throw new BracesException();
                }
                seqBalance--;
                if (seqBalance < 0) {
                    throw new BracesException();
                }
                curAction = Action.CLOSE_BRACE;
                return;
            case '&':
                curAction = Action.AND;
                return;
            case '|':
                curAction = Action.OR;
                return;
            case '^':
                curAction = Action.XOR;
                return;
            case '~':
                curAction = Action.NOT;
                return;
        }
        if (Character.isDigit(symbol)) {
            index--;
            if (curAction == Action.CLOSE_BRACE || curAction == Action.VAR || curAction == Action.NUM) {
                throw new MissingOperandException(index - 1);
            }
            String temp = getNum();
            try {
                number = operation.parseNum(temp);
            } catch (NumberFormatException NFE) {
                throw new OverflowException(index, temp);
            }
            curAction = Action.NUM;
        } else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
            variable = symbol;
            curAction = Action.VAR;
        } else if (checkSubString("count")) {
            curAction = Action.COUNT;
            index += 4;
        } else if (checkSubString("min")){
            curAction = Action.MIN;
            index += 2;
        } else if (checkSubString("max")){
            curAction = Action.MAX;
            index += 2;
        } else {
            throw new ParsingException("Unknown symbol at index: " + index);
        }
    }

    private boolean checkSubString(String word){
        int len = word.length();
        return index + len < expression.length() && expression.substring(index - 1, index + len - 1).equals(word);
    }

    private String getNum() throws OverflowException {
        int beg = index;

        while (index < expression.length() && (Character.isDigit(expression.charAt(index)) ||
                expression.charAt(index) == 'e' || expression.charAt(index) == 'E' ||
                expression.charAt(index) == '.' || ((expression.charAt(index) == '+' ||
                expression.charAt(index) == '-') && index != beg && (expression.charAt(index - 1) == 'e' ||
                expression.charAt(index - 1) == 'E')))) {
            index++;
        }
        return expression.substring(beg, index);
    }

    private TripleExpression<T> unary() throws ParsingException, OverflowException, BracesException {
        getToken();
        TripleExpression<T> exp;
        switch (curAction) {
            case VAR:
                exp = new Variable<T>(Character.toString(variable));
                getToken();
                break;
            case NUM:
                exp = new Const<T>(number);
                getToken();
                break;
            case NEG:
                exp = new Negate<T>(unary(), operation);
                break;
            case OPEN_BRACE:
                exp = minMax();
                getToken();
                break;
            case COUNT:
                exp = new Count<T>(unary(), operation);
                break;
            default:
                throw new MissingOperandException(index);
        }
        return exp;
    }


    private TripleExpression<T> mulDiv() throws ParsingException, OverflowException, BracesException {
        TripleExpression<T> left = unary();
        while (true) {
            switch (curAction) {
                case MUL:
                    left = new Multiply<T>(left, unary(), operation);
                    break;
                case DIV:
                    left = new Divide<T>(left, unary(), operation);
                    break;
                default:
                    return left;
            }
        }
    }


    private TripleExpression<T> addSub() throws ParsingException, OverflowException, BracesException {
        TripleExpression<T> left = mulDiv();
        while (true) {
            switch (curAction) {
                case ADD:
                    left = new Add<T>(left, mulDiv(), operation);
                    break;
                case SUB:
                    left = new Subtract<T>(left, mulDiv(), operation);
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression<T> minMax() throws ParsingException, OverflowException, BracesException {
        TripleExpression<T> left = addSub();
        while (true){
            switch (curAction) {
                case MIN:
                    left = new Min<T>(left, addSub(), operation);
                    break;
                case MAX:
                    left = new Max<T>(left, addSub(), operation);
                    break;
                default:
                    return left;
            }
        }
    }
}