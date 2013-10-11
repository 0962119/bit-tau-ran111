/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.MainFrame;
import Utilities.Utils;
import Utilities.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author DoHongPhuc
 */
public class ChecksumController {

    private MainFrame frame;
    private String nameMd5File;
    private String typeCheck = "MD5";
    checksumProcess process;

    public ChecksumController(MainFrame frame) {
        this.frame = frame;
        initController();
    }

    private void initController() {
        frame.btnChoiceCheckMd5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                choiceInput();
            }
        });
        frame.btnCheck.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!validate()) {
                    return;
                }
                //frame.lblAbout.setVisible(false);
                frame.progessBar.setVisible(true);
                frame.btnCheck.setEnabled(false);
                frame.txtCheckValue.setEnabled(false);
                frame.txtCheckValue.setText("");
                process = new checksumProcess(frame, typeCheck, nameMd5File);
                process.addPropertyChangeListener(new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("progress".equals(evt.getPropertyName())) {
                            Utils.setProgressValue(frame.progessBar, (Integer) evt.getNewValue());
                        }
                    }
                });
                process.execute();
            }
        });
        frame.cbxTypeCheck.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                typeCheck = frame.cbxTypeCheck.getSelectedItem().toString();
            }
        });
    }

    private void choiceInput() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Chá»�n file Ä‘á»ƒ cáº¯t");
        frame.getContentPane().add(fileChooser);
        int v = fileChooser.showDialog(frame, null);

        if (v == JFileChooser.CANCEL_OPTION) {
            return;
        }
        //GÃ¡n file nguá»“n Ä‘á»ƒ check MD5
        nameMd5File = fileChooser.getSelectedFile().getPath();
        frame.txtFileCheckMd5.setText(nameMd5File);
    }

    private boolean validate() {
        if (!Validator.isNotNull(frame.txtFileCheckMd5)) {
            JOptionPane.showMessageDialog(null, "Nháº­p Ä‘Æ°á»�ng dáº«n cho file cáº§n check MD5!");
            return false;
        }
        try {
            File f = new File(frame.txtFileCheckMd5.getText());
            if (!f.exists()) {
                JOptionPane.showMessageDialog(null, "File khÃ´ng tá»“n táº¡i!");
                return false;
            }
            if (!f.isFile()) {
                JOptionPane.showMessageDialog(null, "Kiá»ƒm tra láº¡i Ä‘Æ°á»�ng dáº«n!CÃ¡i báº¡n nháº­p vÃ o ko fáº£i lÃ  file :D");
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public void cancelProcess() {
        process.cancel(true);
    }
}

class checksumProcess extends SwingWorker<Void, Void> {

    private MainFrame frame;
    private String typeCheck;
    private String nameMd5File;

    public checksumProcess(MainFrame frame, String typeCheck, String nameMd5File) {
        this.frame = frame;
        this.typeCheck = typeCheck;
        this.nameMd5File = nameMd5File;
    }

    //Check md5 or sha-1,256,384,512
    private StringBuilder doCheckMD5SHA() {
        int percent = 0;
        setProgress(percent);
        percent = 3;
        setProgress(percent);
        long totalSize = new File(nameMd5File).length();
        StringBuilder str = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(typeCheck);
            BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(new File(nameMd5File)));

            //int theByte = 0;
            byte[] buffer;
            int avai = buffIn.available();
            long byteReader = 0;
            while (avai > 0) {
                if (avai > Utils.MAX_BUFFER) {
                    byteReader += Utils.MAX_BUFFER;
                    percent = (int) ((100 * byteReader) / totalSize);
                    setProgress(percent);
                    buffer = new byte[Utils.MAX_BUFFER];
                    buffIn.read(buffer);
                    md.update(buffer);
                    avai = buffIn.available();
                } else {
                    setProgress(99);
                    buffer = new byte[avai];
                    buffIn.read(buffer);
                    md.update(buffer);
                    avai = buffIn.available();
                }
            }
            buffIn.close();
            byte[] theDigest = md.digest();

            for (int i = 0; i < theDigest.length; i++) {
                str.append(Integer.toHexString((0x000000ff & theDigest[i]) | 0xffffff00).substring(6));
            }
        } catch (Exception e) {
        }
        return str;
    }

    //Check CRC
    private void doCheckCRC32() {
        int percent = 0;
        setProgress(percent);
        percent = 3;
        setProgress(percent);
        try {
            BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(new File(nameMd5File)));
            Checksum cs = new CRC32();
            //int theByte = 0;
            byte[] buffer;
            int avai = buffIn.available();
            int offset = 0;
            if (avai > Utils.MAX_BUFFER) {
                for (int i = 0; i < avai / Utils.MAX_BUFFER; i++) {
                    percent += 100 / (avai / (Utils.MAX_BUFFER + 1));
                    setProgress(percent);
                    buffer = new byte[Utils.MAX_BUFFER];
                    buffIn.read(buffer);
                    cs.update(buffer, offset * i, Utils.MAX_BUFFER);
                }
                setProgress(99);
                buffer = new byte[avai - (avai / Utils.MAX_BUFFER) * Utils.MAX_BUFFER];
                buffIn.read(buffer);
                cs.update(buffer, offset * (avai / Utils.MAX_BUFFER), avai - (avai / Utils.MAX_BUFFER) * Utils.MAX_BUFFER);
            } else {
                setProgress(99);
                buffer = new byte[avai];
                buffIn.read(buffer);
                cs.update(buffer, offset, avai);
            }
            setProgress(100);
            buffIn.close();
            frame.txtCheckValue.setText(Long.toHexString(cs.getValue()));
        } catch (Exception e) {
        }
    }

    //add '-' to string
    private StringBuilder addWord(int[] index, StringBuilder s, String c) {
        for (int i = 0; i < index.length; i++) {
            s.insert(index[i], c);
        }
        return s;
    }

    @Override
    protected Void doInBackground() throws Exception {
        if (typeCheck.equals("CRC32")) {
            doCheckCRC32();
            return null;
        } else {
            StringBuilder str = doCheckMD5SHA();
            if (typeCheck.equals("MD5")) {
                str = addWord(new int[]{8, 17, 26}, str, "-");
            } else if (typeCheck.equals("MD2")) {
                str = addWord(new int[]{8, 17, 26}, str, "-");
            } else if (typeCheck.equals("SHA-1")) {
                str = addWord(new int[]{8, 17, 26, 35}, str, "-");
                str = addWord(new int[]{35}, str, "\n");
            } else if (typeCheck.equals("SHA-256")) {
                str = addWord(new int[]{8, 17, 26, 35, 44, 53, 62}, str, "-");
                str = addWord(new int[]{35}, str, "\n");
            } else if (typeCheck.equals("SHA-384")) {
                str = addWord(new int[]{8, 17, 26, 35, 44, 53, 62, 71, 80, 89, 98}, str, "-");
                str = addWord(new int[]{35, 72}, str, "\n");
            } else if (typeCheck.equals("SHA-512")) {
                str = addWord(new int[]{8, 17, 26, 35, 44, 53, 62, 71, 80, 89, 98, 107, 116, 125, 134}, str, "-");
                str = addWord(new int[]{35, 72, 109}, str, "\n");
            } else {
                System.out.println(str);
            }
            setProgress(100);
            frame.txtCheckValue.setText(str.toString().toUpperCase());
        }
        return null;
    }

    @Override
    protected void done() {
        //frame.lblAbout.setVisible(true);
        frame.progessBar.setVisible(false);
        frame.btnCheck.setEnabled(true);
        frame.txtCheckValue.setEnabled(true);
    }
}
