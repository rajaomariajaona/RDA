package rda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {

    public static String DEFAULT_PATH;

    static {
        File f = new File(System.getProperty("user.home"), ".rda");
        if (!f.exists()) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(f);
                pw.write(System.getProperty("user.desktop"));
                pw.flush();
                pw.close();
                DEFAULT_PATH = System.getProperty("user.desktop");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                DEFAULT_PATH = System.getProperty("user.desktop");
            }

        } else {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                DEFAULT_PATH = br.readLine();
            } catch (Exception ex) {
                DEFAULT_PATH = System.getProperty("user.desktop");
            }
        }
    }

    public static void setDefaultPath(String path) {
        File f = new File(System.getProperty("user.home"), ".rda");

        PrintWriter pw = null;
        try {
            FileOutputStream fos = new FileOutputStream(f);
            pw = new PrintWriter(fos);
            pw.write(path);
            pw.flush();
            pw.close();
            DEFAULT_PATH = path;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
