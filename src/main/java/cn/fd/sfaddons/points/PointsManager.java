package cn.fd.sfaddons.points;

import cn.fd.sfaddons.AddonsPlugin;
import cn.fd.sfaddons.listeners.ActionListeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PointsManager {

    public static List<PWay> pWayList = new ArrayList<>();

    //加载所有方式
    public static void loadWays() {
        for (String way : AddonsPlugin.conf.rPoints_ways) {
            PWay parsed = parseWay(way);
            if (parsed != null) {
                pWayList.add(parsed);
            }
        }
    }

    public static PWay parseWay(String way) {
        String[] pway = way.split(";");
        Action action;
        List<String> conditions = new ArrayList<>();
        double points;
        try {
            points = Double.parseDouble(pway[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //获取Action
        switch (pway[0].toLowerCase()) {
            case "get_exp":
                action = Action.GET_EXP;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pway[0]);
        }
        //条件检测
        if (way.length() == 3) {
            if (!pway[2].startsWith("[") && !pway[2].endsWith("]")) {
                conditions.addAll(Arrays.asList(pway[2].split(",")));
            } else conditions = Collections.singletonList(pway[2]);
        }

        return new PWay(action, points, conditions);
    }

    public static void registerListeners() {
        AddonsPlugin.getInstance().getServer().getPluginManager()
                .registerEvents(new ActionListeners(), AddonsPlugin.getInstance());
    }


}
