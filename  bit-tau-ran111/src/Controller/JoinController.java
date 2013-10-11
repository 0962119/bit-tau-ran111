/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.MainFrame;
import Utilities.ExtensionFileFilter;
import Utilities.Utils;
import Utilities.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author DoHongPhuc
 */
public class JoinController {

    private MainFrame frame;
    private String nameFileInput;
    private String nameFolderOutput = "";
    private String ext = ""; //phần đuổi định dạng của file nguồn có dạng : .001 hoặc .p1
    public joinProcess process;
    private boolean isDelete = false;

    public void setInput(String input) {
        this.nameFileInput = input;
    }

    public void setOutput(String output) {
        this.nameFolderOutput = output;
    }

    public JoinController(MainFrame frame) {
        this.frame = frame;
        initControler();
    }

    private void initControler() {
        /**
         * Chọn file nguồn để Join
         */
        frame.btnJoinSource.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                choiceJoinInput();
            }
        });
        /**
         * Chọn thư mục để chứa file đích khi Join
         */
        frame.btnJoinTarget.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                choiceJoinOutput();
            }
        });
        /**
         * Join button
         */
        frame.btnJoin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!validate()) {
                    return;
                }
                //frame.lblAbout.setVisible(false);
                frame.progessBar.setVisible(true);
                frame.btnCancel.setVisible(true);
                frame.btnJoin.setEnabled(false);
                process = new joinProcess(frame, nameFileInput, nameFolderOutput, ext, isDelete);
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
        /**
         * Có xóa file nguồn sau khi Split xong hay không
         */
        frame.cbxDelSourceJoin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (frame.cbxDelSourceJoin.isSelected()) {
                    isDelete = true;
                } else {
                    isDelete = false;
                }
            }
        });
    }

    /**
     * cancel process
     */
    public void cancelProcess() {
        process.cancel(true);
    }

    /**
     * Chọn file để gộp
     */
    public void choiceJoinInput() {
        JFileChooser fileChooser = new JFileChooser();

        ExtensionFileFilter filter = new ExtensionFileFilter("*.p1,*.001", new String[]{"p1", "001"});
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Chọn file để gộp");

        frame.getContentPane().add(fileChooser);
        int v = fileChooser.showOpenDialog(null);

        if (v == JFileChooser.CANCEL_OPTION) {
            return;
        }

        nameFileInput = fileChooser.getSelectedFile().getPath();
        frame.txtJoinSource.setText(nameFileInput);
        //name thu muc output
        nameFolderOutput = nameFileInput.substring(0, nameFileInput.lastIndexOf("\\"));
        frame.txtJoinTarget.setText(nameFolderOutput);
    }

    /**
     * Choice join output
     */
    public void choiceJoinOutput() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Chọn thư mục để lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//Chỉ được chọn thư mục

        frame.getContentPane().add(fileChooser);
        int v = fileChooser.showSaveDialog(frame);
        if (v == JFileChooser.CANCEL_OPTION) {
            return;
        }
        nameFolderOutput = fileChooser.getSelectedFile().getPath();
        frame.txtJoinTarget.setText(nameFolderOutput);
    }

    private boolean validate() {
        ext = Utils.getExt(nameFileInput);
        //Kiểm tra null
        if (!Validator.isNotNull(frame.txtJoinSource)) {
            JOptionPane.showMessageDialog(null, "Nhập đường dẫn cho file nguồn!");
            return false;
        }
        if (!Validator.isNotNull(frame.txtJoinTarget)) {
            JOptionPane.showMessageDialog(null, "Nhập đường dẫn cho thư mục đích!");
            return false;
        }

        //Kiểm tra xem file nguồn có tồn tại
        try {
            File f = new File(frame.txtJoinSource.getText());
            if (!f.exists()) {
                JOptionPane.showMessageDialog(null, "File nguồn không tồn tại!");
                return false;
            }
            if (!f.isFile()) {
                JOptionPane.showMessageDialog(null, "Kiểm tra lại đường dẫn!Cái bạn nhập vào ko fải là file :D");
                return false;
            }
        } catch (Exception e) {
        }
        //Kiểm tra xem thư mục đích có tồn tại
        try {
            File f = new File(frame.txtJoinTarget.getText());
            if (!f.isDirectory() || !f.exists()) {
                JOptionPane.showMessageDialog(null, "Thư mục nguồn không tồn tại!");
                return false;
            }
        } catch (Exception e) {
        }
        //Kiểm tra xem file đích có tồn tại không? nếu có thì return luôn
        try {
            String temp = Utils.getFileName(frame.txtJoinSource.getText());
            temp = temp.substring(0, temp.lastIndexOf("."));
            File f = new File(frame.txtJoinTarget.getText()
                    + "\\" + temp);
            if (f.exists()) {
                JOptionPane.showMessageDialog(null, "File \"" + temp + "\" đã tồn tại!");
                return false;
            }
        } catch (Exception e) {
        }
        //Kiểm tra xem đuôi của file nguồn có phải là .p1 hoặc .001 hay không
        //JOptionPane.showMessageDialog(null, Utils.getExt(nameFileInput));
        if ((ext.equals(".p1")) || (ext.equals(".001"))) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "File nguồn phải có dạng *.p1 hoặc *.001");
            return false;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="bỏ">
//    private void joinFile() {
//        FileOutputStream fout = null;
//        FileInputStream fin = null;
//        try {
//            int count = getCountFile(nameFileInput); // Tổng số lượng file cần gộp
//            //size của file nguồn
//            long size = (new File(nameFileInput)).length();
//            //tên file đích
//            fout = new FileOutputStream(new File(nameFolderOutput
//                    + nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.lastIndexOf("."))), true);
//            //xử lý từng file nguồn một
//            for (int i = 1; i < count; i++) {
//                System.out.println("Xử lý file thứ " + i);
//                fin = getInput(i);
//                //Nếu size nhỏ hơn max buffer
//                if (size < Utils.MAX_BUFFER) {
//                    byte[] buffer = new byte[(int) size];
//                    fin.read(buffer);
//                    fout.write(buffer);
//                    fout.flush();
//                    fin.close();
//                }//Nếu size lớn hơn max buffer
//                else {
//                    int numBuffer = (int) (size / Utils.MAX_BUFFER);
//                    for (int j = 0; j < numBuffer; j++) {
//                        byte[] buffer = new byte[Utils.MAX_BUFFER];
//                        fin.read(buffer);
//                        fout.write(buffer);
//                        fout.flush();
//                    }
//                    byte[] buffer = new byte[(int) (size - (numBuffer * Utils.MAX_BUFFER))];
//                    fin.read(buffer);
//                    fout.write(buffer);
//                    fout.flush();
//                    fin.close();
//                }
//            }
//            //xử lý file cuối cùng
//            fin = getInput(count);
//            int sizeLastFile = fin.available();
//            System.out.println("Xử lý file thứ " + count + ".Là file cuối cùng!");
//            if (sizeLastFile < Utils.MAX_BUFFER) {
//                byte[] buffer = new byte[(int) fin.available()];
//                fin.read(buffer);
//                fout.write(buffer);
//                fout.flush();
//                fin.close();
//            } else {
//                int numBuffer = (int) (sizeLastFile / Utils.MAX_BUFFER);
//                for (int j = 0; j < numBuffer; j++) {
//                    byte[] buffer = new byte[Utils.MAX_BUFFER];
//                    fin.read(buffer);
//                    fout.write(buffer);
//                    fout.flush();
//                }
//                byte[] buffer = new byte[(int) (sizeLastFile - (numBuffer * Utils.MAX_BUFFER))];
//                fin.read(buffer);
//                fout.write(buffer);
//                fout.flush();
//                fin.close();
//            }
//
//            fout.close();
//            JOptionPane.showMessageDialog(null, "Đã dồn xong file");
//        } catch (IOException ex) {
//            Logger.getLogger(JoinController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    // </editor-fold>
}

class joinProcess extends SwingWorker<Void, Void> {

    private MainFrame frame;
    private String nameFileInput;
    private String nameFolderOutput;
    private String ext; //phần đuổi định dạng của file nguồn có dạng : .001 hoặc .p1
    private boolean isDelete;
    int count; // Tổng số lượng file cần gộp

    public joinProcess(MainFrame frame, String nameFileInput, String nameFolderOutput, String ext, boolean isDelSource) {
        this.frame = frame;
        this.nameFileInput = nameFileInput;
        this.nameFolderOutput = nameFolderOutput;
        this.ext = ext;
        this.isDelete = isDelSource;
    }

    @Override
    protected Void doInBackground() throws Exception {
        int percent = 0;
        count = getCountFile(nameFileInput);
        setProgress(percent);
        percent = 3;
        setProgress(percent);
        FileOutputStream fout = null;
        FileInputStream fin = null;
        try {
            //size của file nguồn
            long size = (new File(nameFileInput)).length();
            //tên file đích
            fout = new FileOutputStream(new File(nameFolderOutput
                    + nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.lastIndexOf("."))), true);
            //xử lý từng file nguồn một
            for (int i = 1; i < count; i++) {
                percent += (100 / (count + 1));
                if (!isCancelled()) {
                    System.out.println("Xử lý file thứ " + i);
                    fin = getStreamInput(i);
                    //Nếu size nhỏ hơn max buffer
                    if (size < Utils.MAX_BUFFER) {
                        byte[] buffer = new byte[(int) size];
                        new WriterThread(fin, fout, buffer, false, true).run();
                    }//Nếu size lớn hơn max buffer
                    else {
                        int numBuffer = (int) (size / Utils.MAX_BUFFER);
                        for (int j = 0; j < numBuffer; j++) {
                            byte[] buffer = new byte[Utils.MAX_BUFFER];
                            new WriterThread(fin, fout, buffer, false, false).run();
                        }
                        byte[] buffer = new byte[(int) (size - (numBuffer * Utils.MAX_BUFFER))];
                        new WriterThread(fin, fout, buffer, false, true).run();
                    }
                    setProgress(percent);
                }
            }
            //xử lý file cuối cùng
            setProgress(99);
            fin = getStreamInput(count);
            int sizeLastFile = fin.available();
            System.out.println("Xử lý file thứ " + count + ".Là file cuối cùng!");
            if (sizeLastFile < Utils.MAX_BUFFER && !isCancelled()) {
                byte[] buffer = new byte[(int) fin.available()];
                new WriterThread(fin, fout, buffer, false, true).run();
            } else if (!isCancelled()) {
                int numBuffer = (int) (sizeLastFile / Utils.MAX_BUFFER);
                for (int j = 0; j < numBuffer; j++) {
                    byte[] buffer = new byte[Utils.MAX_BUFFER];
                    new WriterThread(fin, fout, buffer, false, false).run();
                }
                byte[] buffer = new byte[(int) (sizeLastFile - (numBuffer * Utils.MAX_BUFFER))];
                new WriterThread(fin, fout, buffer, false, true).run();
            }
            fout.close();
            JOptionPane.showMessageDialog(null, "Đã dồn xong " + count + " file!");
        } catch (IOException ex) {
            Logger.getLogger(JoinController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        setProgress(100);
        delSource();
        return null;
    }

    @Override
    protected void done() {
        if (isCancelled()) {
            JOptionPane.showMessageDialog(null, "Đã dừng xử lý!");
        }
        frame.btnCancel.setVisible(false);
        frame.progessBar.setVisible(false);
        //frame.lblAbout.setVisible(true);
        frame.btnJoin.setEnabled(true);
    }

    /**
     * lấy số lượng file cần join
     * @param name
     * @return
     */
    private int getCountFile(String name) {
        int countFile = 1;//Đếm số file cần dồn
        File fInput = new File(name);
        while (fInput.exists()) {
            countFile++;
            if (ext.equals(".p1")) {
                name = name.substring(0, name.lastIndexOf(".")) + ".p" + countFile;
            }
            if (ext.equals(".001")) {
                if (countFile <= 9) {
                    name = name.substring(0, name.lastIndexOf(".")) + ".00" + countFile;
                } else if (countFile <= 99) {
                    name = name.substring(0, name.lastIndexOf(".")) + ".0" + countFile;
                } else {
                    name = name.substring(0, name.lastIndexOf(".")) + "." + countFile;
                }
            }
            //System.out.println(name);
            fInput = new File(name);
            if (fInput.exists()) {
            } else {
                break;
            }
        }
        return countFile - 1;
    }

    private FileInputStream getStreamInput(int i) throws FileNotFoundException {
        String partNameInput = nameFileInput.substring(0, nameFileInput.lastIndexOf("."));
        FileInputStream fin = null;
        if (ext.equals(".p1")) {
            fin = new FileInputStream(partNameInput + ".p" + i);
        } else if (ext.equals(".001")) {
            if (i <= 9) {
                fin = new FileInputStream(partNameInput + ".00" + i);
            } else if (i <= 99) {
                fin = new FileInputStream(partNameInput + ".0" + i);
            } else {
                fin = new FileInputStream(partNameInput + "." + i);
            }
        }
        return fin;
    }

    private File getFileInput(int i) {
        String partNameInput = nameFileInput.substring(0, nameFileInput.lastIndexOf("."));
        File f = null;
        if (ext.equals(".p1")) {
            f = new File(partNameInput + ".p" + i);
        } else if (ext.equals(".001")) {
            if (i <= 9) {
                f = new File(partNameInput + ".00" + i);
            } else if (i <= 99) {
                f = new File(partNameInput + ".0" + i);
            } else {
                f = new File(partNameInput + "." + i);
            }
        }
        return f;
    }

    /**
     * Xóa file nguồn
     */
    private void delSource() {
        //Xóa nếu có yêu cầu xóa
        if (isDelete) {
            for (int i = 1; i <= count; i++) {
                File f = getFileInput(i);
                f.delete();
            }
        }
    }
}
