package cn.fd.sfaddons.listeners;

import cn.fd.sfaddons.AddonsPlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class ListenerManager {

    private static AddonsPlugin plugin;

    public static void register(Listener listener) {
        if (plugin == null)
            plugin = AddonsPlugin.getInstance();
        plugin.getServer().getPluginManager()
                .registerEvents(listener, AddonsPlugin.getInstance());
    }

    public static void unregister(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public static void registerAll() {
        register(new ActionListeners());
        register(new PlayerPreResearchListener());
    }

    public static void unregisterAll() {
        HandlerList.unregisterAll(plugin);
    }

}
