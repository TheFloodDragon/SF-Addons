package cn.fd.sfaddons.utils;

import cn.fd.sfaddons.ReachPoint.Action;
import cn.fd.sfaddons.ReachPoint.PWay;

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

    public static double getRepointM(List<PWay> ways, Action action) {
        for (PWay way : ways) {
            if (isHaveAction(way, action)) {
                return way.getRepointM();
            }
        }
        return 0.0;
    }


}
