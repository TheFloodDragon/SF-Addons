package cn.fd.sfaddons.points;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ResearchPoints {

    protected static HashMap<Player, Double> data = new HashMap<>();

    public static double get(Player player) {
        if (data.containsKey(player)) return data.get(player);
        return 0;
    }

    public static void set(Player player, double points) {
        data.put(player, points);
    }

    public static void add(Player player, double points) {
        set(player, get(player) + points);
    }

    public static void remove(Player player, double points) {
        set(player, get(player) - points);
    }

    public static void reset(Player player) {
        data.remove(player);
    }


}
