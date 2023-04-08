package cn.fd.sfaddons.listeners;

import cn.fd.sfaddons.ReachPoint.Action;
import cn.fd.sfaddons.ReachPoint.ConditionParser;
import cn.fd.sfaddons.ReachPoint.PWay;
import cn.fd.sfaddons.ReachPoint.ResearchPointManager;
import cn.fd.sfaddons.data.DataCon;
import cn.fd.sfaddons.utils.PWayUtils;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class ActionListeners implements Listener {

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        BigDecimal repoints = DataCon.getPlayerData(e.getPlayer().getUniqueId()).getRepoint().
                add(PWayUtils.getRepointF(ResearchPointManager.getPWayList(), Action.GET_EXP).multiply(BigDecimal.valueOf(e.getAmount())));
        DataCon.setData(e.getPlayer().getUniqueId(), repoints);
    }

    @EventHandler
    public void onMobDied(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Damageable))
            return; //实体不可被伤害

        Damageable entity = (Damageable) e.getEntity();

        if (entity.getHealth() > e.getDamage())
            return; //受到的伤害不足以致死

        if (e.getDamager().getType() != EntityType.PLAYER)
            return; //击杀者不是玩家

        Player player = (Player) e.getDamager();

        for (PWay pway : PWayUtils.getPways(Action.KILL_MOB)) {
            //解析附加条件
            if (!ConditionParser.parse(pway.getConditions(), player))
                return;

            HashMap<String, List<String>> kvMap = ConditionParser.getCdConfig(pway.getConditions());

            if (kvMap == null || !kvMap.containsKey("killed_mob") ||
                    !kvMap.get("killed_mob").contains(entity.getType().getKey().toString().toLowerCase())
            ) return; //如果有附加条件且不满足

            //增加研究点数
            BigDecimal repoints = DataCon.getPlayerData(player.getUniqueId()).getRepoint().
                    add(pway.getRepointM());
            DataCon.setData(player.getUniqueId(), repoints);
        }

    }

}
