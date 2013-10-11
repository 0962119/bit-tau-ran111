/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DoHongPhuc
 */
public class WriterThread implements Runnable {

    private FileInputStream fin;
    private FileOutputStream fout;
    private byte[] buffer;
    private boolean closeOutput;
    private boolean closeInput;

    public WriterThread(FileInputStream fin, FileOutputStream fout, byte[] buffer, boolean closeOutput, boolean closeInput) {
        this.fin = fin;
        this.fout = fout;
        this.buffer = buffer;
        this.closeOutput = closeOutput;
        this.closeInput = closeInput;
    }

    public void run() {
        try {
            fin.read(buffer);
            fout.write(buffer);
            fout.flush();
            if (closeOutput) {
                fout.close();
            }
            if (closeInput) {
                fin.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SplitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
