package cn.fd.sfaddons.listeners;

import cn.fd.sfaddons.ReachPoint.PlayerData;
import cn.fd.sfaddons.data.DataCon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerPreResearchEvent;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;

public class PlayerPreResearchListener implements Listener {

    @EventHandler
    public void onPlayerPreResearch(PlayerPreResearchEvent e) {
        e.setCancelled(true);
        PlayerData pd = DataCon.getPlayerData(e.getPlayer().getUniqueId());
        if (pd.getRepoint().compareTo(BigDecimal.valueOf(e.getResearch().getCost())) > -1) {
            DataCon.setData(e.getPlayer().getUniqueId(), pd.getRepoint().subtract(BigDecimal.valueOf(e.getResearch().getCost())));
            e.getResearch().unlock(e.getPlayer(), false);
        } else
            Slimefun.getLocalization().sendMessage(e.getPlayer(), "messages.not-enough-xp", true);
    }

}
