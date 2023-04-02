package cn.fd.sfaddons.utils;

import cn.fd.sfaddons.points.Action;
import cn.fd.sfaddons.points.PWay;

import java.util.List;

public class PWayUtils {

    public static boolean isHaveAction(PWay way, Action action) {
        return way.getAction() == action;
    }

//    public static boolean isHaveAction(List<PWay> ways, Action action) {
//        for (PWay way : ways) {
//            if (way.getAction() == action)
//                return true;
//        }
//        return false;
//    }

    public static double getPoints(List<PWay> ways, Action action) {
        for (PWay way : ways) {
            if (isHaveAction(way, action)) {
                return way.getPoints();
            }
        }
        return 0;
    }


}
