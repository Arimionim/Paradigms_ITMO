package hw6.parser;

import hw6.expression.*;


public class ExpressionParser implements Parser {

    private String expression;
    private int index;
    private int number;
    private Action curAction;
    private char variable;

    public TripleExpression parse(String expression_) {
        expression = expression_;
        index = 0;
        curAction = Action.BEGIN;
        return or();
    }


    private enum Action {
        BEGIN, NEG, MUL, DIV, ADD, SUB, VAR, NUM, OPEN_BRACE, CLOSE_BRACE, XOR, OR, AND, NOT, COUNT
    }

    private void skipWhiteSpace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void getToken() {
        skipWhiteSpace();
        if (index >= expression.length()) {
            return;
        }
        char symbol = expression.charAt(index);
        index++;
        switch (symbol) {
            case '*':
                curAction = Action.MUL;
                return;
            case '/':
                curAction = Action.DIV;
                return;
            case '+':
                curAction = Action.ADD;
                return;
            case '-':
                if (curAction == Action.NUM || curAction == Action.CLOSE_BRACE || curAction == Action.VAR) {
                    curAction = Action.SUB;
                } else {
                    curAction = Action.NEG;
                }
                return;
            case '(':
                curAction = Action.OPEN_BRACE;
                return;
            case ')':
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
            curAction = Action.NUM;
            index--;
            number = getNum();
        } else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
            variable = symbol;
            curAction = Action.VAR;
        } else if (expression.substring(index - 1, index + 4).equals("count")) {
            curAction = Action.COUNT;
            index += 4;
        }
    }

    private int getNum() {
        int beg = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        return Integer.parseUnsignedInt(expression.substring(beg, index));
    }

    private TripleExpression unary() {
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
                exp = new Neg(unary());
                break;
            case NOT:
                exp = new Not(unary());
                break;
            case OPEN_BRACE:
                exp = or();
                getToken();
                break;
            case COUNT:
                exp = new Count(unary());
                break;
            default:
                exp = null;
                break;
        }
        return exp;
    }


    private TripleExpression mulDiv() {
        TripleExpression left = unary();
        while (true) {
            switch (curAction) {
                case MUL:
                    left = new Multiply(left, unary());
                    break;
                case DIV:
                    left = new Divide(left, unary());
                    break;
                default:
                    return left;
            }
        }
    }


    private TripleExpression addSub() {
        TripleExpression left = mulDiv();
        while (true) {
            switch (curAction) {
                case ADD:
                    left = new Add(left, mulDiv());
                    break;
                case SUB:
                    left = new Subtract(left, mulDiv());
                    break;
                default:
                    return left;
            }
        }
    }


    private TripleExpression and() {
        TripleExpression left = addSub();
        while (true) {
            switch (curAction) {
                case AND:
                    left = new And(left, addSub());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression xor() {
        TripleExpression left = and();
        while (true) {
            switch (curAction) {
                case XOR:
                    left = new Xor(left, and());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression or() {
        TripleExpression left = xor();
        while (true) {
            switch (curAction) {
                case OR:
                    left = new Or(left, xor());
                    break;
                default:
                    return left;
            }
        }
    }
}