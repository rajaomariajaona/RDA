/***********************************************************************
 * Module:  ScreenShotShower.java
 * Author:  jane
 * Purpose: Defines the Class ScreenShotShower
 ***********************************************************************/

package rda.screenshot;

import rda.FXMLDocumentController;
import rda.packet.ImagePacket;

public class ScreenShotShower {
   public static void show(ImagePacket imagePacket) throws Exception {
       FXMLDocumentController.showImage(imagePacket.getImage());
   }

}