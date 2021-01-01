package rda.event;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.robot.Robot;
import rda.packet.EventPacket;
import rda.packet.KeyboardEventPacket;
import rda.packet.MouseEventPacket;

public class EventExecutor implements Runnable {

    private static EventExecutor instance = null;
    private Robot robot;

    private EventExecutor() {
        eventPacket = new java.util.LinkedList<EventPacket>();
        Collections.synchronizedCollection(eventPacket);
        Platform.runLater(() -> {
            robot = new Robot();
        });
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
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            int x = Double.valueOf(mep.getX() * d.getWidth()).intValue();
            int y = Double.valueOf(mep.getY() * d.getHeight()).intValue();
            Platform.runLater(() -> {
                switch (mep.getEventType()) {
                    case PRESS:
                        robot.mouseMove(x, y);
                        robot.mousePress(mep.getMouseButton());
                        break;
                    case RELEASE:
                        robot.mouseMove(x, y);
                        robot.mouseRelease(mep.getMouseButton());
                        break;
                    case WHEEL:
                        robot.mouseWheel(mep.getValue());
                        break;
                    case MOVE:
                        robot.mouseMove(x, y);
                        break;
                    default:
                        robot.mouseMove(x, y);
                        break;
                }
            });
        }
        if (packet instanceof KeyboardEventPacket) {
            KeyboardEventPacket kep = (KeyboardEventPacket) packet;
            Platform.runLater(() -> {
                switch (kep.getEventType()) {
                    case PRESS:
                        robot.keyPress(kep.getKeyCode());
                        break;
                    case RELEASED:
                        robot.keyRelease(kep.getKeyCode());
                        break;
                }
            });
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
