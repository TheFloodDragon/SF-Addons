package cn.fd.sfaddons;

import cn.fd.sfaddons.ReachPoint.ResearchPointManager;
import cn.fd.sfaddons.config.ConfigManager;
import cn.fd.sfaddons.config.MessagesManager;
import cn.fd.sfaddons.data.DataManager;
import cn.fd.sfaddons.data.SQL;
import cn.fd.sfaddons.listeners.ListenerManager;
import org.bukkit.plugin.java.JavaPlugin;

import static cn.fd.sfaddons.utils.Utils.log;

public final class AddonsPlugin extends JavaPlugin {

    private static AddonsPlugin instance;

    public static ConfigManager conf = new ConfigManager();

    public static MessagesManager msgs = new MessagesManager();

    @Override
    public void onEnable() {
        instance = this;

        reload();

        //创建数据存储库
        if (DataManager.create()) log("插件加载成功");
        else {
            log("插件已卸载");
            this.getServer().getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        SQL.close();
    }

    public void reload() {
        //注册监听器
        ListenerManager.registerAll();
        //加载配置文件
        initConfigs();
        //加载研究点数获取方式
        ResearchPointManager.load();
    }

    public void initConfigs() {
        reloadConfig();

        //加载 config.yml
        conf.loadConfig("config.yml");

        //加载 messages.yml
        msgs.loadConfig("messages.yml");

    }

    public static AddonsPlugin getInstance() {
        return instance;
    }

}
