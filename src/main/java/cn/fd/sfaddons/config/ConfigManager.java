package cn.fd.sfaddons.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager extends ConfigF {

    public ConfigManager() {
    }

    public boolean rePoints_enable;
    public List<String> rePoints_ways;
    public String storage_type;

    @Override
    public void initFormConfig(FileConfiguration config) {
        rePoints_enable = config.getBoolean("ResearchPoints.enable", true);
        rePoints_ways = config.getStringList("ResearchPoints.getWays");
        storage_type = config.getString("Database.StorageType");
    }

}