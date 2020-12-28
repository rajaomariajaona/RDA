package rda.event;

import java.io.IOException;
import rda.connection.Connection;
import rda.packet.EventPacket;

public class EventPacketSender {
   private Connection connection;
   
   public EventPacketSender(Connection connection) {
      this.connection = connection;
   }
   
   public void send(EventPacket eventPacket) throws IOException {
       connection.sendPacket(eventPacket);
   }

}