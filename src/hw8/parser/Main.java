package hw8.parser;

import tabulator.GenericTabulator;
import tabulator.Tabulator;

public class Main {
    public static void main(String[] args) throws Exception {
        Tabulator tabulator = new GenericTabulator();
        Object[][][] res =  tabulator.tabulate("d", "1.3e+2", 1, 2, 1, 2, 1, 2);
        for (int i = 0; i < res.length; i++){
            for (int j = 0; j < res[i].length; j++){
                for (int k = 0; k < res[i][j].length; k++){
                    System.out.print(res[i][j][k] + " ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }
}
