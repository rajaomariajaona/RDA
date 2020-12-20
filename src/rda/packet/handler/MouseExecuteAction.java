/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet.handler;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.packet.MousePacket;

/**
 *
 * @author snowden
 */
public class MouseExecuteAction {

    private Robot robot;
    private MousePacket mousePacket;

    public MouseExecuteAction(MousePacket mousePacket) {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MouseExecuteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.mousePacket = mousePacket;
        execute();
    }

    private void execute() {
        int v = this.mousePacket.getValue();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = Double.valueOf(this.mousePacket.getX() * d.getWidth()).intValue();
        int y = Double.valueOf(this.mousePacket.getY() * d.getHeight()).intValue();
        switch (this.mousePacket.getMouseAction()) {
            case PRESS:
                robot.mousePress(v);
                robot.mouseMove(x, y);
                break;
            case RELEASE:
                robot.mouseRelease(v);
                robot.mouseMove(x, y);
                break;
            case WHEEL:
                robot.mouseWheel(v);
                robot.mouseMove(x, y);
                break;
            case MOVE:
                robot.mouseMove(x, y);
                break;
            default:
                robot.mouseMove(x, y);
                break;
        }
    }

}
