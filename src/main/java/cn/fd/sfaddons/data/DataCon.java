package cn.fd.sfaddons.data;

import cn.fd.sfaddons.points.PlayerData;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.util.UUID;

public class DataCon {

    public static PlayerData getPlayerData(UUID uuid) {
        return getPlayerDataI(uuid);
    }

    public static PlayerData getPlayerData(String username) {
        return getPlayerDataI(username);
    }

    private static <T> PlayerData getPlayerDataI(T u) {
        PlayerData pd = null;

        if (Cache.CacheContainsKey(u)) {
            pd = Cache.getDataFromCache(u);
        } else {
            DataManager.getPlayerData(u);
            if (Cache.CacheContainsKey(u)) {
                pd = Cache.getDataFromCache(u);
            }
        }
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            Cache.clearCache();
        }
        return pd;
    }

    public static void changeAllPlayerData(String targetType, BigDecimal amount) {
        Cache.clearCache();

        DataManager.saveAll(targetType, amount);
    }

}