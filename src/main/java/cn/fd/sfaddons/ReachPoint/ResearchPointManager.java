package cn.fd.sfaddons.ReachPoint;

import cn.fd.sfaddons.AddonsPlugin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResearchPointManager {

    private static List<PWay> pWayList = new ArrayList<>();

    //加载所有方式
    public static void load() {
        if (!pWayList.isEmpty())
            pWayList.clear();
        for (String way : AddonsPlugin.conf.rePoints_ways) {
            PWay parsed = parseWay(way);
            if (parsed != null) {
                pWayList.add(parsed);
            }
        }
    }

    public static PWay parseWay(String ways) {
        String[] way = ways.split(";");
        Action action = null;
        List<String> conditions = new ArrayList<>();
        double points;
        try {
            points = Double.parseDouble(way[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //获取Action
        for (Action act : Action.values()) {
            if (way[0].toLowerCase().equalsIgnoreCase(act.getName())) {
                action = act;
                break;
            }
        }
        if (action == null)
            throw new IllegalStateException("Unexpected value: " + way[0]);

        //条件检测
        if (ways.length() == 3) {
            if (way[2].startsWith("[") && way[2].endsWith("]")) {
                conditions = Collections.singletonList(way[2]);
            } else conditions.addAll(Arrays.asList(way[2].split(",")));
        }

        return new PWay(action, BigDecimal.valueOf(points), conditions);
    }

    public static List<PWay> getPWayList() {
        return pWayList;
    }

}
