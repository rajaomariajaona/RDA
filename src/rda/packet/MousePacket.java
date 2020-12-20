/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet;

import rda.other.MouseAction;

/**
 *
 * @author snowden
 */
public class MousePacket extends Packet {

    private double x, y;
    private int value;
    private MouseAction mouseAction;

    public MousePacket(double x, double y, MouseAction mouseAction, int value) throws Exception {
        this.x = x;
        this.y = y;
        this.value = value;
        this.mouseAction = mouseAction;
        serialize(this);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public MouseAction getMouseAction() {
        return mouseAction;
    }

    public void setMouseAction(MouseAction mouseAction) {
        this.mouseAction = mouseAction;
    }

}
