package cn.fd.sfaddons;

import cn.fd.sfaddons.config.ConfigManager;
import cn.fd.sfaddons.config.MessagesManager;
import cn.fd.sfaddons.data.DataManager;
import cn.fd.sfaddons.data.SQL;
import cn.fd.sfaddons.ReachPoint.PointsManager;
import org.bukkit.plugin.java.JavaPlugin;

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
        if (DataManager.create()) log("插件加载成功");
        else {
            log("插件已卸载");
            this.getServer().getPluginManager().disablePlugin(this);
        }


//        DataManager.save(new PlayerData(UUID.fromString("3c11403a-3a1a-386b-b72f-9b8826cb913e"), "MC_jiao_long", new BigDecimal(1)));
//        PlayerData pd = DataCon.getPlayerData("MC_jiao_long");
//        System.out.println(pd.getRepoint());
//        DataCon.setData(pd.getUniqueId(),new BigDecimal(114514));
//        pd = DataCon.getPlayerData("MC_jiao_long");
//        System.out.println(pd.getRepoint());
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

        //加载 config.yml
        conf.loadConfig("config.yml");

        //加载 messages.yml
        msgs.loadConfig("messages.yml");

    }

    public static AddonsPlugin getInstance() {
        return instance;
    }

}
