package cn.fd.sfaddons.data;


import cn.fd.sfaddons.points.PlayerData;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    public static final ConcurrentHashMap<UUID, PlayerData> pds = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, UUID> uuids = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<UUID, UUID> m_uuids = new ConcurrentHashMap<>();


    public static void insertIntoCache(final UUID uuid, final PlayerData pd) {
        if (pd != null) {
            if (pd.getName() != null && pd.getPoints() != null) {
                pds.put(uuid, pd);
                uuids.put(pd.getName(), uuid);
            }
        }
    }

    public static void insertIntoMultiUUIDCache(final UUID uuid, final UUID luuid) {
        if (uuid != null && luuid != null && !uuid.toString().equals(luuid.toString())) {
            m_uuids.put(luuid, uuid);
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


    public static UUID getMultiUUIDCache(final UUID luuid) {
        if (m_uuids.containsKey(luuid)) {
            return m_uuids.get(luuid);
        }
        return null;
    }

    public static void updateIntoCache(final UUID uuid, final PlayerData pd, final BigDecimal points) {
        pd.setPoints(points);
        pds.put(uuid, pd);
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
        m_uuids.clear();
    }


}