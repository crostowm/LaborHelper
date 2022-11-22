package io;

import javaobj.HourlySales;

import java.io.File;
import java.io.PrintWriter;

public class HourlySalesWriter {
    public void write(int storeNum, String dow, int numWeeks, HourlySales hourlySales)
    {
        try {
            PrintWriter writer = new PrintWriter("res/" + storeNum + "-" + dow + "-" + numWeeks);
            writer.println(String.format("Store: %d %s %d", storeNum, dow, numWeeks));
            writer.println(hourlySales.toString(false));
            writer.println();
            writer.println(hourlySales.toString(true));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
