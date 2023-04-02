package cn.fd.sfaddons.configs;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    public ConfigManager() {
    }

    public boolean rPoints_enable;
    public List<String> rPoints_ways;
    public String storage_type;

    public void loadConfig(FileConfiguration config) {
        rPoints_enable = config.getBoolean("ResearchPoints.enable",true);
        rPoints_ways = config.getStringList("ResearchPoints.getWays");
        storage_type = config.getString("Database.StorageType");
    }


}