package cn.fd.sfaddons.points;

import java.util.List;

public class PWay {

    public PWay(Action action, double points, List<String> conditions) {
        this.action = action;
        this.points = points;
        this.conditions = conditions;
    }

    private final Action action;
    private final double points;
    private final List<String> conditions;

    public Action getAction() {
        return action;
    }

    public double getPoints() {
        return points;
    }

    public List<String> getConditions() {
        return conditions;
    }

}
