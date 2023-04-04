package cn.fd.sfaddons.data;

import cn.fd.sfaddons.ReachPoint.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.UUID;

import static cn.fd.sfaddons.utils.Utils.runTaskAsynchronously;

public class DataCon {

    public static PlayerData getPlayerData(UUID uuid) {
        return getPlayerDataI(uuid);
    }

    public static PlayerData getPlayerData(String username) {
        return getPlayerDataI(username);
    }

    private static <T> PlayerData getPlayerDataI(T u) {

        if (!Cache.CacheContainsKey(u)) {
            DataManager.getPlayerData(u);
        }

//        if (Bukkit.getOnlinePlayers().isEmpty()) {
//            Cache.clearCache();
//        }
        return Cache.getDataFromCache(u);
    }


    public static boolean setData(String name, BigDecimal amount) {
        return setDataI(name, amount);
    }

    public static boolean setData(UUID uuid, BigDecimal amount) {
        return setDataI(uuid, amount);
    }

    private static <T> boolean setDataI(T p, BigDecimal amount) {
        PlayerData pd;

//      我觉得我不会再碰这边的代码,所以不这么写
//        if (p instanceof UUID)
//            pd = getPlayerData((UUID) p);
//        else if (p instanceof String)
//            pd = getPlayerData((String) p);
//        else
//            pd = null;

        if (p instanceof UUID)
            pd = getPlayerData((UUID) p);
        else pd = getPlayerData((String) p);

        //修改点数后的新数据
        PlayerData newData = new PlayerData(pd.getUniqueId(), pd.getName(), amount);
        //载入缓存
        Cache.updateIntoCache(newData);

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            //如果玩家注册(进入)过服务器,则保存数据
            if (player.getUniqueId().equals(newData.getUniqueId())) {
                //运行保存命令
                if (Thread.currentThread().getName().equalsIgnoreCase("Server thread"))
                    runTaskAsynchronously(() -> DataManager.save(newData));
                else DataManager.save(newData);
                //执行成功
                return true;
            }
        }

        return false;
    }

}