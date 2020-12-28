package rda.event;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.packet.EventPacket;
import rda.packet.KeyboardEventPacket;
import rda.packet.MouseEventPacket;

public class EventExecutor implements Runnable {

    private static EventExecutor instance = null;
    private Robot robot;

    private EventExecutor() {
        eventPacket = new java.util.LinkedList<EventPacket>();
        Collections.synchronizedCollection(eventPacket);
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(EventExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public static EventExecutor getInstance() {
        if (instance == null) {
            instance = new EventExecutor();
        }
        return instance;
    }

    private java.util.Queue<EventPacket> eventPacket;

    public synchronized void pushEventPacket(EventPacket newEventPacket) {
        this.eventPacket.offer(newEventPacket);
        notify();
    }

    public EventPacket popEventPacket() {
        return this.eventPacket.poll();
    }

    public void removeAllEventPacket() {
        eventPacket.clear();
    }

    public void execute(EventPacket packet) {
        if (packet instanceof MouseEventPacket) {
            MouseEventPacket mep = (MouseEventPacket) packet;
            int v = mep.getValue();
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            int x = Double.valueOf(mep.getX() * d.getWidth()).intValue();
            int y = Double.valueOf(mep.getY() * d.getHeight()).intValue();
            switch (mep.getEventType()) {
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
        if (packet instanceof KeyboardEventPacket) {

        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                EventPacket p;
                synchronized (this) {
                    while (eventPacket.isEmpty()) {
                        wait();
                    }
                    p = this.popEventPacket();
                }
                execute(p);
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(EventExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
