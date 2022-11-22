package io;

import javaobj.HourlySales;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HourlySalesReader {

    public HourlySales readHourlySales(String fileName, int numWeeks)
    {
        HourlySales hourlySales = new HourlySales(numWeeks);
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                ArrayList<String> consolidatedList = getConsolidatedList(line);
                if (consolidatedList.size() >= 1) {
                    String category = consolidatedList.get(0);
                    if (HourlySales.isCategory(category)) {
                        for(int column = 7; column < consolidatedList.size(); column += 6) {
                            String time = consolidatedList.get(column);

                            System.out.println(consolidatedList.get(column + 1));
                            double sales = Double.parseDouble((removeComma(consolidatedList.get(column + 1).substring(1))));
                            int count = Integer.parseInt(consolidatedList.get(column + 2));
                            if(!time.equals("Total"))
                                hourlySales.addToMap(time, category, count, sales);
                        }
                    }
                }

            }
            scanner.close();
        } catch (

                Exception e) {
            e.printStackTrace();
        }
        return hourlySales;
    }

    private String removeComma(String s) {
        StringBuilder noComma = new StringBuilder();
        for(char c: s.toCharArray())
        {
            if(c != ',')
                noComma.append(c);
        }
        return noComma.toString();
    }

    private ArrayList<String> getConsolidatedList(String line)
    {
        ArrayList<String> consolidatedList = new ArrayList<>();
        String[] currentList = line.split(",");
        for(int ii = 0; ii < currentList.length; ii++)
        {
            String con = currentList[ii];
            if(con.charAt(0) == '"')
            {
                con = con.substring(1);
                String cat = currentList[ii + 1];
                con += cat.substring(0, cat.length() - 1);
                ii++;
            }
            consolidatedList.add(con);
        }
        return consolidatedList;
    }
}
