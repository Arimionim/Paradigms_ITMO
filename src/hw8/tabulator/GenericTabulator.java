package hw8.tabulator;

import exceptions.ParsingException;
import expression.TripleExpression;
import operations.*;
import parser.ExpressionParser;
import parser.Parser;

public class GenericTabulator implements Tabulator {
    @Override
    public <T> Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Operations<?> operation;
        switch(mode){
            case "i":
                operation = new IntegerOperations(true);
                break;
            case "d":
                operation = new DoubleOperations();
                break;
            case "bi":
                operation = new BigIntOperations();
                break;
            case "u":
                operation = new IntegerOperations(false);
                break;
            case "s":
                operation = new ShortOperations();
                break;
            case "l":
                operation = new LongOperations();
                break;
            default:
                throw new ParsingException("Unknown type");
        }
        return makeTable(operation, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] makeTable(Operations<T> operation, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {

        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];

        Parser<T> parser = new ExpressionParser<>(operation);
        TripleExpression<T> parsedExpression = parser.parse(expression);
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = parsedExpression.evaluate(operation.parseNum(Integer.toString(i)), operation.parseNum(Integer.toString(j)), operation.parseNum(Integer.toString(k)));
                    }
                    catch (Exception e){
                        result[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}