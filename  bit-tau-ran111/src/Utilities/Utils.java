/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.File;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author DoHongPhuc
 */
public class Utils {
    public static final int MAX_BUFFER = 52428800 ;// Max-buffer for read and write data
    //50MB = 52428800
    //20MB = 20971520
    //10MB = 10485760
    //1MB  = 1048576

    static final String[] browsers = {"iexplore", "google-chrome", "firefox", "opera",
        "konqueror", "epiphany", "seamonkey", "galeon", "kazehakase", "mozilla"};
    static final String errMsg = "Error attempting to launch web browser";

    public static void openURL(String url) {
        try {
            //attempt to use Desktop library from JDK 1.6+ (even if on 1.5)
            Class<?> d = Class.forName("java.awt.Desktop");
            d.getDeclaredMethod("browse", new Class[]{java.net.URI.class}).invoke(d.getDeclaredMethod("getDesktop").invoke(null), new Object[]{java.net.URI.create(url)});
            //above code mimics:
            //java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception ignore) {
            //library not available or failed
            String osName = System.getProperty("os.name");
            try {
                if (osName.startsWith("Mac OS")) {
                    Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[]{String.class}).invoke(null, new Object[]{url});
                } else if (osName.startsWith("Windows")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } else {
                    //assume Unix or Linux
                    boolean found = false;
                    for (String browser : browsers) {
                        if (!found) {
                            found = Runtime.getRuntime().exec(new String[]{"which", browser}).waitFor() == 0;
                            if (found) {
                                Runtime.getRuntime().exec(new String[]{browser, url});
                            }
                        }
                    }
                    if (!found) {
                        throw new Exception(Arrays.toString(browsers));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, errMsg + "\n" + e.toString());
            }
        }
    }

    public static String getExt(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return ext;
    }

    public static String getFileName(String filePath) {
        String name = new File(filePath).getName();
        return name;
    }

    public static void setProgressValue(JProgressBar bar, int newValue) {
        bar.setStringPainted(true);
        bar.setString(Integer.toString(newValue) + "%");
        bar.setValue(newValue);
    }
}
