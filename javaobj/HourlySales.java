package javaobj;

import java.util.HashMap;

public class HourlySales {

    public static final String TAKE_OUT = "Take Out", PICKUP = "Pickup", DELIVERY = "Delivery", EAT_IN = "Eat In", ONLINE_PICKUP = "Online Pickup",
            ONLINE_DELIVERY = "Online Delivery", COUNT = "count", VALUE = "value";
    private final HashMap<String, HashMap<String, HashMap<String, Double>>> hourlySalesMap;
    private final int numWeeks;
    public HourlySales(int numWeeks) {
        hourlySalesMap = new HashMap<>();
        this.numWeeks = numWeeks;
    }

    public static boolean isCategory(String cat) {
        return cat.equals(TAKE_OUT) || cat.equals(PICKUP) || cat.equals(DELIVERY) || cat.equals(EAT_IN) || cat.equals(ONLINE_PICKUP) || cat.equals(ONLINE_DELIVERY);
    }

    public void addToMap(String time, String category, double count, double val)
    {
        hourlySalesMap.computeIfAbsent(time, k -> new HashMap<>());
        hourlySalesMap.get(time).computeIfAbsent(category, k -> new HashMap<>());
        hourlySalesMap.get(time).get(category).put(COUNT, count);
        hourlySalesMap.get(time).get(category).put(VALUE, val);
    }

    public double getTotalCVForTime(String cv, String time)
    {
        double total = 0;
        for (String cat: hourlySalesMap.get(time).keySet())
        {
            total += hourlySalesMap.get(time).get(cat).get(cv);
        }
        return total;
    }

    public String toString(boolean isExtended) {
        StringBuilder ret = new StringBuilder();
        for (int ii = 0; ii < 24; ii++) {
            ret.append(timeToString(ii, isExtended));
        }
        ret.append(String.format("%sCount:%03.0f Value:$%.2f\n\n", "Total Day Average: ", getAverageDay(COUNT), getAverageDay(VALUE)));
        return ret.toString();
    }

    private String timeToString(int ii, boolean isExtended) {
        StringBuilder ret = new StringBuilder();
        String ampm = "AM";
        int hour = ii;
        if(ii == 0)
            hour = 12;
        if(hour > 12)
            hour -= 12;
        if(ii > 11)
            ampm = "PM";
        String time = hour + ":00 " + ampm;
        ret.append(time).append("\n");
        if(isExtended) {
            for (String cat : hourlySalesMap.get(time).keySet()) {
                ret.append(String.format("%15s: Count:%3.0f Value:$%.2f\n", cat, hourlySalesMap.get(time).get(cat).get(COUNT) / numWeeks, hourlySalesMap.get(time).get(cat).get(VALUE) / numWeeks));
            }
            //ret += String.format("%9sCount:%03.0f Value:$%.2f\n", "Total: ", getTotalCVForTime(COUNT, time), getTotalCVForTime(VALUE, time));
        }
        ret.append(String.format("%9sCount:%3.0f Value:$%.2f\n\n", "Average: ", (getTotalCVForTime(COUNT, time) / numWeeks), (getTotalCVForTime(VALUE, time) / numWeeks)));
        return ret.toString();
    }

    private double getAverageDay(String cv)
    {
        double ret = 0;
        for(String time: hourlySalesMap.keySet())
        {
            for(String cat: hourlySalesMap.get(time).keySet())
            {
                ret += hourlySalesMap.get(time).get(cat).get(cv);
            }
        }
        return ret / numWeeks;
    }
}
