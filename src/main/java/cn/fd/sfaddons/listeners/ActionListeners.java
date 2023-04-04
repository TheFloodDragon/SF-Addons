package cn.fd.sfaddons.listeners;

import cn.fd.sfaddons.data.DataCon;
import cn.fd.sfaddons.ReachPoint.Action;
import cn.fd.sfaddons.ReachPoint.PointsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.math.BigDecimal;

import static cn.fd.sfaddons.utils.PWayUtils.getRepointM;

public class ActionListeners implements Listener {

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        BigDecimal repoints = DataCon.getPlayerData(event.getPlayer().getUniqueId()).getRepoint().
                add(getRepointM(PointsManager.pWayList, Action.GET_EXP).multiply(BigDecimal.valueOf(event.getAmount())));
        DataCon.setData(event.getPlayer().getUniqueId(), repoints);
    }
}
