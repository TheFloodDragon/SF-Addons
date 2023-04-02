package cn.fd.sfaddons.points;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerData {

    private final String name;
    private BigDecimal rpoints;
    private final UUID uuid;

    public PlayerData(UUID uuid, String name, BigDecimal points) {
        this.uuid = uuid;
        this.name = name;
        this.rpoints = points;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPoints() {
        return rpoints;
    }

    public void setPoints(BigDecimal points) {
        this.rpoints = points;
    }

    public void addPoints(BigDecimal points){
        this.rpoints.add(points);
    }

    public void removePoints(BigDecimal points){
        this.rpoints.subtract(points);
    }

    public UUID getUniqueId() {
        return uuid;
    }

}
