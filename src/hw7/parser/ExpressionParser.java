package hw7.parser;

import exceptions.BracesException;
import exceptions.MissingOperandException;
import exceptions.OverflowException;
import exceptions.ParsingException;
import expression.*;

import java.util.EnumSet;


public class ExpressionParser implements Parser {

    private String expression;
    private int index;
    private int number;
    private Action curAction;
    private char variable;
    private int seqBalance;
    private EnumSet<Action> notToCloseBrace = EnumSet.of(Action.NEG, Action.ADD, Action.SUB, Action.MUL,
            Action.DIV, Action.XOR, Action.AND, Action.NOT,
            Action.OR, Action.COUNT, Action.OPEN_BRACE, Action.BEGIN,
            Action.POW, Action.LOG);

    public TripleExpression parse(String expression_) throws ParsingException, OverflowException, BracesException {
        expression = expression_;
        index = 0;
        seqBalance = 0;
        curAction = Action.BEGIN;
        return addSub();
    }

    private enum Action {
        BEGIN, NEG, MUL, DIV, ADD, SUB, VAR, NUM, OPEN_BRACE, CLOSE_BRACE, XOR, OR, AND, NOT, COUNT, POW, LOG, LOG10, POW10, END
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
                throw new BracesException(expression);
            }
            curAction = Action.END;
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
                        throw new MissingOperandException(expression, index);
                    }
                    if (Character.isDigit(expression.charAt(index))) {
                        String temp = getNum();
                        try {
                            number = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException NFE) {
                            throw new ParsingException("Illegal constant: " + temp + " at index: " + index + " expression " + expression);
                        }
                        curAction = Action.NUM;
                    } else {
                        curAction = Action.NEG;
                    }
                }
                return;
            case '(':
                if (curAction == Action.CLOSE_BRACE || curAction == Action.VAR || curAction == Action.NUM) {
                    throw new BracesException(expression);
                }
                seqBalance++;
                curAction = Action.OPEN_BRACE;
                return;
            case ')':
                if (notToCloseBrace.contains(curAction)) {
                    throw new BracesException(expression);
                }
                seqBalance--;
                if (seqBalance < 0) {
                    throw new BracesException(expression);
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
                throw new MissingOperandException(expression, index - 1);
            }
            String temp = getNum();
            try {
                number = Integer.parseInt(temp);
            } catch (NumberFormatException NFE) {
                throw new ParsingException("Illegal constant: " + temp + " at index: " + index + " expression " + expression);
            }
            curAction = Action.NUM;
        } else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
            variable = symbol;
            curAction = Action.VAR;
        } else if (index + 3 <= expression.length() && expression.substring(index - 1, index + 4).equals("count")) {
            curAction = Action.COUNT;
            index += 4;
        } else if (index + 3 <= expression.length() && expression.substring(index - 1, index + 4).equals("log10")) {
            curAction = Action.LOG10;
            index += 4;
        } else if (index + 3 <= expression.length() && expression.substring(index - 1, index + 4).equals("pow10")) {
            curAction = Action.POW10;
            index += 4;
        } else {
            throw new ParsingException("Unknown symbol at index: " + index + " expression " + expression);
        }

    }

    private String getNum() throws OverflowException {
        int beg = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        return expression.substring(beg, index);
    }

    private TripleExpression unary() throws ParsingException, OverflowException, BracesException {
        getToken();
        TripleExpression exp;
        switch (curAction) {
            case VAR:
                exp = new Variable(Character.toString(variable));
                getToken();
                break;
            case NUM:
                exp = new Const(number);
                getToken();
                break;
            case NEG:
                exp = new CheckedNegate(unary());
                break;
            case OPEN_BRACE:
                exp = addSub();
                getToken();
                break;
            case LOG10:
                exp = new CheckedLog(unary(), new Const(10));
                break;
            case POW10:
                exp = new CheckedPow(new Const(10), unary());
                break;
            default:
                throw new MissingOperandException(expression, index);
        }
        return exp;
    }


    private TripleExpression powLog() throws ParsingException, OverflowException, BracesException {
        TripleExpression left = unary();
        while (true) {
            switch (curAction) {
                case POW:
                    left = new CheckedPow(left, unary());
                    break;
                case LOG:
                    left = new CheckedLog(left, unary());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression mulDiv() throws ParsingException, OverflowException, BracesException {
        TripleExpression left = powLog();
        while (true) {
            switch (curAction) {
                case MUL:
                    left = new CheckedMultiply(left, powLog());
                    break;
                case DIV:
                    left = new CheckedDivide(left, powLog());
                    break;
                default:
                    return left;
            }
        }
    }


    private TripleExpression addSub() throws ParsingException, OverflowException, BracesException {
        TripleExpression left = mulDiv();
        while (true) {
            switch (curAction) {
                case ADD:
                    left = new CheckedAdd(left, mulDiv());
                    break;
                case SUB:
                    left = new CheckedSubtract(left, mulDiv());
                    break;
                default:
                    return left;
            }
        }
    }
}