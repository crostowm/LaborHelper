import io.HourlySalesReader;
import io.HourlySalesWriter;
import javaobj.HourlySales;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
        ArrayList<String> dows = new ArrayList<>();
        dows.add("Mon");
        dows.add("Tue");
        dows.add("Wed");
        dows.add("Thur");
        dows.add("Fri");
        dows.add("Sat");
        dows.add("Sun");
        int storeNum = 2048;
        int numWeeks = 4;

        HourlySalesReader hourlySalesReader = new HourlySalesReader();
        HourlySalesWriter writer = new HourlySalesWriter();

        for (String dow : dows) {
            HourlySales hourlySales = hourlySalesReader.readHourlySales(String.format("res/%d-%s-%d.csv", storeNum, dow, numWeeks), numWeeks);
            writer.write(storeNum, dow, numWeeks, hourlySales);
        }

    }
}
