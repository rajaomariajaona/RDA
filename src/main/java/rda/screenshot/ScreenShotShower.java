/***********************************************************************
 * Module:  ScreenShotShower.java
 * Author:  jane
 * Purpose: Defines the Class ScreenShotShower
 ***********************************************************************/

package rda.screenshot;

import rda.MainController;
import rda.packet.ImagePacket;

public class ScreenShotShower {
   public static void show(ImagePacket imagePacket) throws Exception {
       MainController.showImage(imagePacket.getImage());
   }

}