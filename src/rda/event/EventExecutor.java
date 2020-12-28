package rda.event;

import java.awt.Robot;
import rda.packet.EventPacket;

public class EventExecutor {

    private static Robot robot;

    private java.util.List<EventPacket> eventPacket;

    public java.util.List<EventPacket> getEventPacket() {
        if (eventPacket == null) {
            eventPacket = new java.util.LinkedList<EventPacket>();
        }
        return eventPacket;
    }

    public java.util.Iterator getIteratorEventPacket() {
        if (eventPacket == null) {
            eventPacket = new java.util.LinkedList<EventPacket>();
        }
        return eventPacket.iterator();
    }

    public void setEventPacket(java.util.List<EventPacket> newEventPacket) {
        removeAllEventPacket();
        for (java.util.Iterator iter = newEventPacket.iterator(); iter.hasNext();) {
            addEventPacket((EventPacket) iter.next());
        }
    }

    public void addEventPacket(EventPacket newEventPacket) {
        if (newEventPacket == null) {
            return;
        }
        if (this.eventPacket == null) {
            this.eventPacket = new java.util.LinkedList<EventPacket>();
        }
        if (!this.eventPacket.contains(newEventPacket)) {
            this.eventPacket.add(newEventPacket);
        }
    }

    public void removeEventPacket(EventPacket oldEventPacket) {
        if (oldEventPacket == null) {
            return;
        }
        if (this.eventPacket != null) {
            if (this.eventPacket.contains(oldEventPacket)) {
                this.eventPacket.remove(oldEventPacket);
            }
        }
    }

    public void removeAllEventPacket() {
        if (eventPacket != null) {
            eventPacket.clear();
        }
    }

    public static void execute(EventPacket packet) {
        // TODO: implement
    }

}
