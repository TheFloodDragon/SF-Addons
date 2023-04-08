package cn.fd.sfaddons.ReachPoint;

import org.bukkit.entity.Player;

import java.util.*;

public class ConditionParser {

    public static boolean parse(List<String> conditions, Player player) {
        if (!conditions.isEmpty()) {
            for (String condition : conditions) {
                if (!parse(condition, player)) return false;
            }
        }
        return true;
    }

    public static boolean parse(String condition, Player player) {
        //初始化条件配置
        if (!parsePerm(condition, player)) return false;

        return true;
    }


    private static boolean parsePerm(String condition, Player player) {
        if (condition.contains("perm")) {
            String[] perms = condition.split(" ");
            for (int i = 1; i < perms.length; i++) {
                if (!player.hasPermission(perms[i])) return false;
            }
        }
        return true;
    }

    public static HashMap<String, List<String>> getCdConfig(List<String> conditions) {
        if (!conditions.isEmpty()) {
            HashMap<String, List<String>> map = new HashMap<>();
            for (String condition : conditions) {
                KVPairList plist = getCdConfig(condition);
                if (plist != null)
                    map.put(plist.getKey(), plist.getValues());
            }
            return map;
        }
        return null;
    }

    public static KVPairList getCdConfig(String condition) {
        String[] cdC;
        if (condition.contains("is")) cdC = condition.split("is", 2);
        else if (condition.contains("=")) {
            cdC = condition.split("=", 2);
        } else return null;

        String key = cdC[0].replaceAll(" ", "").toLowerCase();
        String temp = cdC[1].replaceAll(" ", "").toLowerCase();
        List<String> values;

        if (temp.startsWith("[") && temp.endsWith("]")) {
            values = Collections.singletonList(temp);
        } else values = new ArrayList<>(Arrays.asList(temp.split(",")));

        return new KVPairList(key, values);
    }

    public static class KVPairList {
        private String key;
        private List<String> values;

        public KVPairList(String key, List<String> values) {
            this.key = key;
            this.values = values;
        }

        public List<String> getValues() {
            return values;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }

}
