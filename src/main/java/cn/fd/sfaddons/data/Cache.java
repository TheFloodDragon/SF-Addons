package cn.fd.sfaddons.data;


import cn.fd.sfaddons.ReachPoint.PlayerData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    public static final ConcurrentHashMap<UUID, PlayerData> pds = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, UUID> uuids = new ConcurrentHashMap<>();

    public static void insertIntoCache(final UUID uuid, final PlayerData pd) {
        if (pd != null) {
            if (pd.getName() != null && pd.getRepoint() != null) {
                pds.put(uuid, pd);
                uuids.put(pd.getName(), uuid);
            }
        }
    }

    public static <T> boolean CacheContainsKey(final T key) {
        if (key instanceof UUID) {
            return pds.containsKey((UUID) key);
        }
        return uuids.containsKey((String) key);
    }


    public static <T> PlayerData getDataFromCache(final T key) {
        UUID u;
        if (key instanceof UUID)
            u = (UUID) key;
        else
            u = uuids.get(((String) key));

        return pds.get(u);
    }

    public static void updateIntoCache(final PlayerData pd) {
        pds.put(pd.getUniqueId(), pd);
    }

    @SuppressWarnings("all")
    public static void removefromCache(final UUID uuid) {
        if (pds.containsKey(uuid)) {
            String name = pds.get(uuid).getName();
            pds.remove(uuid);
            uuids.remove(name);
        }
    }


    public static void clearCache() {
        pds.clear();
        uuids.clear();
    }


}