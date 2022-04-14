package com.denfop.api.se;

public class NodeSEStats {

    protected double SEIn;
    protected double SE;

    public NodeSEStats(double SEIn, double SE) {
        this.SEIn = SEIn;
        this.SE = SE;
    }

    public double getSEIn() {
        return this.SEIn;
    }

    public double getSE() {
        return this.SE;
    }

    protected void set(double SEIn, double SE) {
        this.SEIn = SEIn;
        this.SE = SE;
    }

}
