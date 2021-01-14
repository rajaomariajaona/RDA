package rda;

import java.util.Locale;

public class AppLauncher{
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        App.launch(args);
    }

}