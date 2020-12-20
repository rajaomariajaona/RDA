/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet.handler;

import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 *
 * @author snowden
 */
public class ImageObservable extends Observable {

    private static ImageObservable instance = null;

    public static ImageObservable getInstance() {
        if (instance == null) {
            instance = new ImageObservable();
        }
        return instance;
    }

    public void setImage(BufferedImage image) {
        setChanged();
        notifyObservers(image);
    }
}
