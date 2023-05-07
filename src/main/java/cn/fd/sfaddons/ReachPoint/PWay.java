package cn.fd.sfaddons.ReachPoint;

import java.util.List;

public class PWay {

    public PWay(Action action, double points, List<String> conditions) {
        this.action = action;
        this.repointM = points;
        this.conditions = conditions;
    }

    private final Action action;
    private final double repointM;
    private final List<String> conditions;

    public Action getAction() {
        return action;
    }

    public double getRepointM() {
        return repointM;
    }

    public List<String> getConditions() {
        return conditions;
    }

}
