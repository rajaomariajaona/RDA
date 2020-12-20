/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.other;

import java.awt.event.InputEvent;
import javafx.scene.input.MouseButton;

/**
 *
 * @author snowden
 */
public class MouseButtons {

    public static int fromMouseButtonJFX(MouseButton m) {
        switch (m) {
            case MIDDLE:
                return InputEvent.BUTTON2_DOWN_MASK;
            case PRIMARY:
                return InputEvent.BUTTON1_DOWN_MASK;
            case SECONDARY:
                return InputEvent.BUTTON3_DOWN_MASK;
            case NONE:
                return 0;
        }
        return 0;
    }
}
