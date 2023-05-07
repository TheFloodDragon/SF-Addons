package cn.fd.sfaddons.listeners;

import cn.fd.sfaddons.ReachPoint.Action;
import cn.fd.sfaddons.ReachPoint.PointsManager;
import cn.fd.sfaddons.data.DataCon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import static cn.fd.sfaddons.utils.PWayUtils.getRepointM;

public class ActionListeners implements Listener {

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        double repoints = DataCon.getPlayerData(event.getPlayer().getUniqueId()).getRepoint()
                + getRepointM(PointsManager.pWayList, Action.GET_EXP) * event.getAmount();
        DataCon.setData(event.getPlayer().getUniqueId(), repoints);
    }
}
