package cn.fd.sfaddons.utils;

import cn.fd.sfaddons.ReachPoint.Action;
import cn.fd.sfaddons.ReachPoint.PWay;
import cn.fd.sfaddons.ReachPoint.ResearchPointManager;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public static BigDecimal getRepointF(List<PWay> ways, Action action) {
        for (PWay way : ways) {
            if (isHaveAction(way, action)) {
                return way.getRepointM();
            }
        }
        return BigDecimal.valueOf(0);
    }

    /**
     * 获取同一动作的研究点数获取方式
     * @param action 动作
     * @return 同一动作的研究点数获取方式
     */
    public static List<PWay> getPways(Action action) {
        List<PWay> out = new ArrayList<>();
        for (PWay pWay : ResearchPointManager.getPWayList()) {
            if (pWay.getAction() == action)
                out.add(pWay);
        }
        return out;
    }


}
