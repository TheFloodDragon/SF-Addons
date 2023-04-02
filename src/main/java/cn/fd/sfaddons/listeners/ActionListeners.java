package cn.fd.sfaddons.listeners;

import cn.fd.sfaddons.points.Action;
import cn.fd.sfaddons.points.PointsManager;
import cn.fd.sfaddons.points.ResearchPoints;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import static cn.fd.sfaddons.utils.PWayUtils.getPoints;

public class ActionListeners implements Listener {

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        ResearchPoints.add(event.getPlayer(),
                getPoints(PointsManager.pWayList, Action.GET_EXP) * event.getAmount());
    }
}
