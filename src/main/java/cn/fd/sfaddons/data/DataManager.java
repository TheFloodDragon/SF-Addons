package cn.fd.sfaddons.data;

import cn.fd.sfaddons.AddonsPlugin;
import cn.fd.sfaddons.points.PlayerData;
import cn.fd.sfaddons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

//
//        if (XConomyLoad.Config.SYNCDATA_TYPE.equals(SyncChannalType.REDIS)) {
//            if (RedisConnection.connectredis()) {
//                RedisThread rThread = new RedisThread();
//                rThread.start();
//            } else {
//                return false;
//            }
//        }

//        ImportData.isExitsFile();

        return true;
    }


    public static void deletePlayerData(UUID u) {
        SQL.deletePlayerData(u.toString());
    }

    public static void save(PlayerData pd, Boolean isAdd, BigDecimal amount) {
        SQL.save(pd);
    }

    public static <T> void getPlayerData(T key) {
        if (key instanceof UUID) {
            SQL.getPlayerData((UUID) key);
        } else if (key instanceof String) {
            SQL.getPlayerData((String) key);
        }
    }

    public static void saveAll(String targetType, BigDecimal amount) {
        Utils.runTaskAsynchronously(() -> {
            if (targetType.equalsIgnoreCase("all")) {
                SQL.saveAll(targetType, null, amount);
            } else if (targetType.equalsIgnoreCase("online")) {
                List<UUID> ol = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ol.add(player.getUniqueId());
                }
                SQL.saveAll(targetType, ol, amount);
            }
        });
    }


}
