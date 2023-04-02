package cn.fd.sfaddons;

import cn.fd.sfaddons.configs.ConfigManager;
import cn.fd.sfaddons.configs.MessagesManager;
import cn.fd.sfaddons.data.DataCon;
import cn.fd.sfaddons.data.DataManager;
import cn.fd.sfaddons.data.SQL;
import cn.fd.sfaddons.points.PointsManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static cn.fd.sfaddons.utils.Utils.log;

public final class AddonsPlugin extends JavaPlugin {

    private static AddonsPlugin instance;

    public static ConfigManager conf = new ConfigManager();

    public static MessagesManager msgs = new MessagesManager();

    @Override
    public void onEnable() {
        instance = this;

        //注册监听器
        PointsManager.registerListeners();

        reload();

        //创建数据存储库
        if(DataManager.create())
            log("插件加载成功");
        else {
            log("插件已卸载");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        System.out.println(DataCon.getPlayerData("MC_jiao_long"));
        DataCon.getPlayerData("MC_jiao_long").setPoints(new BigDecimal(999));
        System.out.println(DataCon.getPlayerData("MC_jiao_long"));
    }

    @Override
    public void onDisable() {
        SQL.close();
    }

    public void reload() {
        //加载配置文件
        initConfigs();
        //加载研究点数获取方式
        PointsManager.loadWays();
    }

    public void initConfigs() {
        reloadConfig();
        try {
            //加载 config.yml
            InputStream resource_config = getResource("config.yml");
            if (resource_config == null) {
                getLogger().severe("Error. Missing config.yml inside the JAR file.");
                return;
            }
            FileConfiguration config = setupConfigFile(new File(getDataFolder(), "config.yml"), resource_config);
            conf.loadConfig(config);

            //加载 messages.yml
            InputStream resource_messages = getResource("messages.yml");
            if (resource_messages == null) {
                getLogger().severe("Error. Missing messages.yml inside the JAR file.");
                return;
            }
            msgs.loadMessages(setupConfigFile(new File(getDataFolder(), "messages.yml"), resource_messages));

        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("Error loading config.yml file.");
            e.printStackTrace();
        }
    }


    private FileConfiguration setupConfigFile(File configFile, InputStream configResource) throws IOException, InvalidConfigurationException {
        if (!configFile.exists())
            // 从 JAR 资源中加载默认文件
            FileUtils.copyInputStreamToFile(configResource, configFile);

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        // 添加缺少的属性
        FileConfiguration tmp = YamlConfiguration.loadConfiguration((new InputStreamReader(configResource)));
        for (String k : tmp.getKeys(true)) {
            if (!config.contains(k)) config.set(k, tmp.get(k));
        }
        config.save(configFile);

        //config.load(configFile);
        return config;
    }

    public static AddonsPlugin getInstance() {
        return instance;
    }

}
