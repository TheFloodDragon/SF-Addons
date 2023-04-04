package cn.fd.sfaddons.config;

import cn.fd.sfaddons.AddonsPlugin;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static cn.fd.sfaddons.utils.Utils.log;

public abstract class ConfigF {

    public abstract void initFormConfig(FileConfiguration config);


    public void loadConfig(File configFile, String resourcePath) {
        try {
            InputStream resource_config = AddonsPlugin.getInstance().getResource(resourcePath);
            if (resource_config == null) {
                log("Error. Missing config.yml inside the JAR file.", 2);
                return;
            }

            this.initFormConfig(setupConfigFile(configFile, resource_config));

            resource_config.close();
        } catch (IOException | InvalidConfigurationException e) {
            log("Error loading " + configFile.getName() + " file.", 2);
            e.printStackTrace();
        }
    }

    public void loadConfig(String configName, String resourcePath) {
        loadConfig(new File(AddonsPlugin.getInstance().getDataFolder(), configName), resourcePath);
    }

    public void loadConfig(String path) {
        loadConfig(path, path);
    }


    public FileConfiguration setupConfigFile(File configFile, InputStream configResource) throws IOException, InvalidConfigurationException {
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists())
            // 从 JAR 资源中加载默认文件
            FileUtils.copyInputStreamToFile(configResource, configFile);
        else {
            // 添加缺少的属性
            FileConfiguration tmp = YamlConfiguration.loadConfiguration((new InputStreamReader(configResource)));
            for (String k : tmp.getKeys(true)) {
                if (!config.contains(k)) config.set(k, tmp.get(k));
            }
            config.save(configFile);
        }

        config.load(configFile);
        return config;
    }


}
