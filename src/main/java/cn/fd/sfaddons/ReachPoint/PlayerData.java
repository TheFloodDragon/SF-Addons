package cn.fd.sfaddons.ReachPoint;

import java.util.UUID;

public class PlayerData {

    private final String name;
    private final double repoint;
    private final UUID uuid;

    public PlayerData(UUID uuid, String name, double points) {
        this.uuid = uuid;
        this.name = name;
        this.repoint = points;
    }

    public String getName() {
        return name;
    }

    public double getRepoint() {
        return repoint;
    }

//    public void setRepoint(BigDecimal points) {
//        this.repoint = points;
//    }

    public UUID getUniqueId() {
        return uuid;
    }

}
