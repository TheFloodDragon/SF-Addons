package cn.fd.sfaddons.ReachPoint;

import cn.fd.sfaddons.AddonsPlugin;
import cn.fd.sfaddons.listeners.ActionListeners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PointsManager {

    public static List<PWay> pWayList = new ArrayList<>();

    //加载所有方式
    public static void loadWays() {
        for (String way : AddonsPlugin.conf.rePoints_ways) {
            PWay parsed = parseWay(way);
            if (parsed != null) {
                pWayList.add(parsed);
            }
        }
    }

    public static PWay parseWay(String ways) {
        String[] way = ways.split(";");
        Action action;
        List<String> conditions = new ArrayList<>();
        double points;
        try {
            points = Double.parseDouble(way[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //获取Action
        switch (way[0].toLowerCase()) {
            case "get_exp":
                action = Action.GET_EXP;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + way[0]);
        }
        //条件检测
        if (ways.length() == 3) {
            if (!way[2].startsWith("[") && !way[2].endsWith("]")) {
                conditions.addAll(Arrays.asList(way[2].split(",")));
            } else conditions = Collections.singletonList(way[2]);
        }

        return new PWay(action, BigDecimal.valueOf(points), conditions);
    }

    public static void registerListeners() {
        AddonsPlugin.getInstance().getServer().getPluginManager()
                .registerEvents(new ActionListeners(), AddonsPlugin.getInstance());
    }


}
