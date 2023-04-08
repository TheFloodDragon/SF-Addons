package cn.fd.sfaddons.ReachPoint;

public enum Action {


    GET_EXP("get_exp"),
    KILL_MOB("kill_mob");


    private final String name;

    Action(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
