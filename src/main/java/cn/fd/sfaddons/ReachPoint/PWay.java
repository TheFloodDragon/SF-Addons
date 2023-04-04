package cn.fd.sfaddons.ReachPoint;

import java.math.BigDecimal;
import java.util.List;

public class PWay {

    public PWay(Action action, BigDecimal points, List<String> conditions) {
        this.action = action;
        this.repointM = points;
        this.conditions = conditions;
    }

    private final Action action;
    private final BigDecimal repointM;
    private final List<String> conditions;

    public Action getAction() {
        return action;
    }

    public BigDecimal getRepointM() {
        return repointM;
    }

    public List<String> getConditions() {
        return conditions;
    }

}
