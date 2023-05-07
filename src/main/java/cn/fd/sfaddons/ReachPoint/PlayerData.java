package cn.fd.sfaddons.ReachPoint;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerData {

    private final String name;
    private final BigDecimal repoint;
    private final UUID uuid;

    public PlayerData(UUID uuid, String name, BigDecimal points) {
        this.uuid = uuid;
        this.name = name;
        this.repoint = points;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRepoint() {
        return repoint;
    }

//    public void setRepoint(BigDecimal points) {
//        this.repoint = points;
//    }

    public UUID getUniqueId() {
        return uuid;
    }

}
