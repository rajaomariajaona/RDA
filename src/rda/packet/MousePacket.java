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
    private MouseAction mouseAction;

    public MousePacket(double x, double y, MouseAction mouseAction) throws Exception {
        this.x = x;
        this.y = y;
        this.mouseAction = mouseAction;
        serialize(this);
    }
}
