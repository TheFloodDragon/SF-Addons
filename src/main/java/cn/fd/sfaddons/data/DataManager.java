package cn.fd.sfaddons.data;

import cn.fd.sfaddons.AddonsPlugin;
import cn.fd.sfaddons.ReachPoint.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.UUID;

import static cn.fd.sfaddons.utils.Utils.log;

public class DataManager {

    public static boolean create() {

        if (AddonsPlugin.conf.storage_type.equalsIgnoreCase("SQLite")) {
            log("数据保存方式 - SQLite");

            File dataFolder = DatabaseConnection.dataFolder;
            if (!dataFolder.exists() && !dataFolder.mkdirs()) {
                log("文件夹创建异常", 1);
                return false;
            }
        }

        if (SQL.con()) {
            SQL.createTable();
            log("SQLite 连接正常");
        } else {
            log("SQLite 连接异常", 1);
            return false;
        }

        return true;
    }

    public static boolean save(PlayerData pd) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            //如果玩家注册(进入)过服务器,则保存数据
            if (player.getUniqueId().equals(pd.getUniqueId())) {
                SQL.saveData(pd);
                return true;
            }
        }
        return false;
    }

    public static <T> void getPlayerData(T key) {
        if (key instanceof UUID) {
            SQL.getPlayerData((UUID) key);
        } else if (key instanceof String) {
            SQL.getPlayerData((String) key);
        }
    }


}
