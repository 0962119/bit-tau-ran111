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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pkg12.HashFunctionTest;

// <editor-fold defaultstate="collapsed" desc="ext class">
/**
 *
 * @author DoHongPhuc
 */
public class SplitController {

    private MainFrame frame;
    public static final int mode1 = 0x01;//Split thành các phần khác nhau
    public static final int mode2 = 0x02;//Split theo dung lượng
    private String nameFileInput;
    private String nameFolderOutput;
    private long sizeSplit; // Dung lượng file đích
    private int numberSplit = 2; // Số lượng file đích , default = 2
    private String sizeType = "MegaByte";//Kiểu dung lượng
    private boolean isDelete = false; //có delete source hay không? mặc định là không
    private int mode = mode2;//Các thức Split
    private String extType = "00";
    public splitProcess process;

    /**
     * Constructor
     * @param frame
     */
    public SplitController(MainFrame frame) {
        this.frame = frame;
        initController();
    }

    /**
     * Add các action listener
     */
    private void initController() {
        /**
         * Setvisiable default
         */
        frame.cbxShowText.setVisible(false);
        frame.txtPassword.setVisible(false);
        frame.cbxUsePass.setVisible(false);
        //frame.progessSplit.setVisible(false);
        /**
         * Chọn file nguồn để split
         */
        frame.btnSplitSource.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                choiceSplitInput();
            }
        });
        /**
         * Chọn thư mục để chứa file split
         */
        frame.btnSplitTarget.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                choiceSplitOutput();
            }
        });

        /**
         * Nếu chọn cách Split file theo số lượng
         */
        frame.rdaNumberSplit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                frame.spnNumberSplit.setEnabled(true);
                frame.spnSplitSize.setEnabled(false);
                frame.cbxSizeType.setEnabled(false);
                mode = mode1;
                numberSplit = Integer.parseInt(frame.spnNumberSplit.getValue().toString());
            }
        });
        /**
         * Spinner number split change
         */
        frame.spnNumberSplit.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                numberSplit = Integer.parseInt(frame.spnNumberSplit.getValue().toString());
            }
        });
        /**
         * Nếu chọn các Split file theo dung lượng
         */
        frame.rdaSizeSplit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                frame.spnNumberSplit.setEnabled(false);
                frame.spnSplitSize.setEnabled(true);
                frame.cbxSizeType.setEnabled(true);
                mode = mode2;
                sizeSplit = Integer.parseInt(frame.spnSplitSize.getValue().toString());
            }
        });
        /**
         * Spinner Split size change
         */
        frame.spnSplitSize.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                sizeSplit = Integer.parseInt(frame.spnSplitSize.getValue().toString());
            }
        });
        /**
         * Kiểu tính của dung lượng
         */
        frame.cbxSizeType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sizeType = frame.cbxSizeType.getSelectedItem().toString();
                System.out.println(frame.cbxSizeType.getSelectedItem().toString());
            }
        });
        /**
         * Có xóa file nguồn sau khi Split xong hay không
         */
        frame.cbxDelSplitSource.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (frame.cbxDelSplitSource.isSelected()) {
                    isDelete = true;
                } else {
                    isDelete = false;
                }
            }
        });
        /**
         * Xử lý kiểu đuôi của file đích
         */
        frame.cbxExtType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //System.out.println("ext 1 :" + extType);
                if (frame.cbxExtType.getSelectedItem().toString().equals("*.p1")) {
                    extType = "p";
                    frame.cbxUsePass.setVisible(true);
                } else {
                    if (frame.cbxExtType.getSelectedItem().toString().equals("*.001")) {
                        extType = "00";
                        frame.cbxUsePass.setVisible(false);
                        frame.txtPassword.setVisible(false);
                        frame.cbxShowText.setVisible(false);
                    }
                }
                //System.out.println("ext 2 :" + extType);
            }
        });
        /**
         * Using password
         */
        frame.cbxUsePass.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (frame.cbxUsePass.isSelected()) {
                    frame.txtPassword.setVisible(true);
                    frame.cbxShowText.setVisible(true);
                } else {
                    frame.txtPassword.setVisible(false);
                    frame.cbxShowText.setVisible(false);
                }
            }
        });
        /**
         * Split button
         * input : file input, file output, number split or capacity of file,delete mode,split mode
         * output: message => ok or faild
         */
        sizeSplit = Integer.parseInt(frame.spnSplitSize.getValue().toString());
        frame.btnSplit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!validate()) {
                    return;
                }
                //frame.lblAbout.setVisible(false);
                frame.progessBar.setVisible(true);
                frame.btnCancel.setVisible(true);
                frame.btnSplit.setEnabled(false);
                long sizeFileSplit = calculatorSize(sizeSplit, numberSplit, sizeType, mode);
                process = new splitProcess(frame, nameFileInput, nameFolderOutput,
                        isDelete, extType, sizeFileSplit);
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

    }

    /**
     * cancel process
     */
    public void cancelProcess() {
        process.cancel(true);
    }

    /**
     * Choice file to split
     */
    public void choiceSplitInput() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Chọn file để cắt");
        frame.getContentPane().add(fileChooser);
        int v = fileChooser.showDialog(frame, null);

        if (v == JFileChooser.CANCEL_OPTION) {
            return;
        }
        //Gán file nguồn
        nameFileInput = fileChooser.getSelectedFile().getPath();
        frame.txtSplitSource.setText(nameFileInput);

        //Gán thư mục đích 
        nameFolderOutput = nameFileInput.substring(0, nameFileInput.lastIndexOf("\\"));
        frame.txtSplitTarget.setText("Data/" + fileChooser.getSelectedFile().getName());
        File theDir = new File("Data/" + fileChooser.getSelectedFile().getName());
		if (!theDir.exists()) 
		{
		    boolean result = theDir.mkdir();  

		     if(result) {    
		       System.out.println("DIR created");  
		       nameFolderOutput = "Data/" + fileChooser.getSelectedFile().getName();
		     }
		     else
		    	 System.out.println("Error");   
		}
    }

    /**
     * Nếu người dùng chọn thư mục đích mới , không fải thư mục mặc định
     */
    public void choiceSplitOutput() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Chọn thư mục để lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//Chỉ được chọn thư mục
        frame.getContentPane().add(fileChooser);
        int v = fileChooser.showSaveDialog(frame);
        if (v == JFileChooser.CANCEL_OPTION) {
            return;
        }
        //Gán thư mục đích , và truyền cho controller
        nameFolderOutput = fileChooser.getSelectedFile().getPath();
        frame.txtSplitTarget.setText(nameFolderOutput);
    }

    /**
     * Tính dung lượng của file Split
     * @param s
     * @param numberSplit
     * @param sizeType
     * @param mode
     * @return
     */
    private long calculatorSize(long s, int numberSplit, String sizeType, int mode) {
        long size = 0;
        //Mode 1 : chia thành n phần bằng nhau
        if (mode == mode1) {
            File f = new File(nameFileInput);
            long fileSize = f.length();
            size = (fileSize / numberSplit) + numberSplit;//Thừa còn hơn thiếu =))
        }//Mode 2 : tính theo dung lượng
        else {
            if (sizeType.equals("MegaByte")) {
                size = s * 1048576;
            } else if (sizeType.equals("KiloByte")) {
                size = s * 1024;
            } else if (sizeType.equals("Byte")) {
                size = s;
            } else if (sizeType.equals("GigaByte")) {
                size = s * 1073741824;
            }
        }
        return size;
    }

    /**
     * Kiểm tra các tham số đầu vào trước khi cắt file
     */
    private boolean validate() {
        //Kiểm tra null
        if (!Validator.isNotNull(frame.txtSplitSource)) {
            JOptionPane.showMessageDialog(null, "Nhập đường dẫn cho file nguồn!");
            return false;
        }
        if (!Validator.isNotNull(frame.txtSplitTarget)) {
            JOptionPane.showMessageDialog(null, "Nhập đường dẫn cho thư mục đích!");
            return false;
        }
        //Kiểm tra xem file nguồn có tồn tại
        try {
            File f = new File(frame.txtSplitSource.getText());
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
            File f = new File(frame.txtSplitTarget.getText());
            if (!f.isDirectory() || !f.exists()) {
                JOptionPane.showMessageDialog(null, "Thư mục nguồn không tồn tại!");
                return false;
            }
        } catch (Exception e) {
        }
        //Kiểm tra xem file đích đã tồn tại chưa? nếu rồi thì báo lỗi và return
        try {
            String temp = Utils.getFileName(frame.txtSplitSource.getText()) + "." + extType + "1";
            File f = new File(frame.txtSplitTarget.getText()
                    + "\\" + temp);
            if (f.exists()) {
                JOptionPane.showMessageDialog(null, "File \"" + temp + "\" đã tồn tại!");
                return false;
            }
        } catch (Exception e) {
        }
        //Kiểm tra xem dung lượng file cắt có nhỏ hơn dung lượng file nguồn không
        File f = new File(nameFileInput);
        if (calculatorSize(sizeSplit, numberSplit, sizeType, mode) > f.length()) {
            JOptionPane.showMessageDialog(null, "Dung lượng các file cắt phải nhỏ hơn file nguồn!");
            return false;
        }
        return true;//Thỏa mãn hết các điều kiện
    }
}

class splitProcess extends SwingWorker<Void, Void> {

    private MainFrame frame;
    public static final int mode1 = 0x01;//Split thành các phần khác nhau
    public static final int mode2 = 0x02;//Split theo dung lượng
    private String nameFileInput;
    private String nameFolderOutput;
    private boolean isDelete;
    private String extType;
    long sizeFileSplit;

    public splitProcess(MainFrame frame, String nameFileInput, String nameFolderOutput,
            boolean isDelete, String extType, long sizeFileSplit) {
        this.frame = frame;
        this.nameFileInput = nameFileInput;
        this.nameFolderOutput = nameFolderOutput;
        this.isDelete = isDelete;
        this.extType = extType;
        this.sizeFileSplit = sizeFileSplit;
    }

    /**
     * Xử lý cắt file
     * @return
     * @throws Exception
     */
    @Override
    protected Void doInBackground() throws Exception {
        //Xử lý việc cắt file ở đây.
        //Mỗi khi cắt xong sẽ change progress để hiển thị cho Jprogressbar
        int percent = 0;
        setProgress(percent);
        percent = 3;
        setProgress(percent);
        File f = new File(nameFileInput);//File Input
        FileInputStream fin = null;
        FileOutputStream fout = null;
        int numFile = 0;
        long sizeTotal = 0;
        String namefile = nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.length());
        try {
            fin = new FileInputStream(nameFileInput);//Total size : size of Source File
            sizeTotal = (f.length());//size: size of file split
            long size = sizeFileSplit;
            int count = 0;//biến count để đếm số file nguồn và để chỉnh tên file đích
            numFile = (int) (sizeTotal / size);//Số lượng file sẽ cắt trọn vẹn
            int numBuffer = (int) (size / Utils.MAX_BUFFER);//Số lần lặp buffer
            
            nameFolderOutput = "Data" + namefile;
            System.out.println("Số lượng file lặp lại: " + numFile + " ------- Số lần lặp lại buffer: " + numBuffer);
            
            //BƯỚC 1.1 : ----------------------------------//Mỗi lần lặp là ghi ra một file .p1,hoặc .001
            for (int i = 0; i < numFile; i++) {
                if (!isCancelled()) {
                    percent += (100 / (numFile + 1));
                    count++; //Tăng biến count và tạo file mới để làm đích
                    System.out.println("Xử lý file thứ : " + count);
                    fout = createOutput(count);
                    //Mỗi lần lặp là một lần đọc ghi buffer
                    for (int j = 0; j < numBuffer; j++) {
                        byte[] buffer = new byte[Utils.MAX_BUFFER];
                        //writeBufferToFile(fin, fout, buffer, false);
                        new WriterThread(fin, fout, buffer, false, false).run();
                    }
                    //Phần thừa còn lại của file đang ghi sẽ xử lý ở đây.Mỗi quá trình này thì read next liên tục?
                    byte[] bffOld = new byte[(int) (size - numBuffer * Utils.MAX_BUFFER)];
                    //writeBufferToFile(fin, fout, bffOld, true);
                    new WriterThread(fin, fout, bffOld, true, false).run();
                    
                    setProgress(percent);
                }
            }
            
            

            //BƯỚC 1.2 : Xử lý file cuối cùng với dung lượng còn thừa.
            setProgress(99);
            System.out.println("Size thừa : " + (int) (sizeTotal - numFile * size));
            int buffer = (int) (sizeTotal - numFile * size);
            //Nếu size thừa không còn thì coi như xong luôn
            if (buffer <= 0 && !isCancelled()) {
                JOptionPane.showMessageDialog(null, "Đã cắt xong thành " + count + " file!");//-----------Kết thúc
            } else//Nếu phần dư này nhỏ hơn 50MB thì đọc ghi trực tiếp
            if (buffer < Utils.MAX_BUFFER && !isCancelled()) {
                byte[] bff = new byte[buffer];
                fout = createOutput(++count);
                //writeBufferToFile(fin, fout, bff, true);
                new WriterThread(fin, fout, bff, true, false).run();
                JOptionPane.showMessageDialog(null, "Đã cắt xong thành " + count + " file!");//-----------Kết thúc
            } else if (!isCancelled()) {
                fout = createOutput(++count);
                for (int j = 0; j < buffer / Utils.MAX_BUFFER; j++) {
                    byte[] bff = new byte[Utils.MAX_BUFFER]; //10MB
                    //writeBufferToFile(fin, fout, bff, false);
                    new WriterThread(fin, fout, bff, false, false).run();
                }
                //Phần thừa còn lại của file đang ghi sẽ xử lý ở đây.Mỗi quá trình này thì read next liên tục?
                byte[] bff = new byte[(int) (buffer - ((int) buffer / Utils.MAX_BUFFER) * Utils.MAX_BUFFER)];
                //writeBufferToFile(fin, fout, bff, true);
                new WriterThread(fin, fout, bff, true, false).run();
                JOptionPane.showMessageDialog(null, "Đã cắt xong thành " + count + " file!");//-----------Kết thúc
            }
            setProgress(100);
            fin.close();//fin close sau khi xử lý xong
        } catch (IOException ex) {
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        
        Writer out =  out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(nameFolderOutput+"/" + namefile + ".torrent"), "UTF-8"));
        out.write(Integer.toString( numFile + 1));
        ((BufferedWriter) out).newLine();
        
        out.write(Long.toString(sizeTotal));
        ((BufferedWriter) out).newLine();
        
        File d=new File(nameFolderOutput);
        
        File [] fileEntries = d.listFiles();
        for(File item : fileEntries)
        {
        	MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			HashFunctionTest hash=new HashFunctionTest();
			
			String s= item.getName()+" "+ hash.calculateHash(sha1,item.getPath());
			//ghi hash code
			if(s.indexOf("torrent")<0){
			out.write(s);
			((BufferedWriter) out).newLine();
			}
        }
        out.close();
        delSource(f);//Xóa sau khi xử lý xong!
        return null;
    }

    /**
     * Cắt file xong
     */
    @Override
    protected void done() {
        if (isCancelled()) {
            JOptionPane.showMessageDialog(null, "Đã dừng xử lý!");
        }
        frame.btnCancel.setVisible(false);
        frame.progessBar.setVisible(false);
        //frame.lblAbout.setVisible(true);
        frame.btnSplit.setEnabled(true);
    }

    /**
     * Xử lý việc tạo file Output
     */
    private FileOutputStream createOutput(int count) {
        FileOutputStream fout = null;
        String namefile = nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.length());
        nameFolderOutput = "Data" + namefile;
        try {
            //Đuôi kiểu *.p1
            if (extType.equals("p")) {
                fout = new FileOutputStream(nameFolderOutput
                        + nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.length()) + ".p" + count, true);
                return fout;
            }
            //Đuổi kiểu *.001
            if (extType.equals("00")) {
                if (count <= 9) {
                    fout = new FileOutputStream(nameFolderOutput
                            + nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.length()) + ".00" + count, true);
                    return fout;
                }
                if (count <= 99) {
                    fout = new FileOutputStream(nameFolderOutput
                            + nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.length()) + ".0" + count, true);
                    return fout;
                } else {
                    fout = new FileOutputStream(nameFolderOutput
                            + nameFileInput.substring(nameFileInput.lastIndexOf("\\"), nameFileInput.length()) + "." + count, true);
                    return fout;
                }//Chơi cả 4 chữ số luôn
            }
        } catch (Exception ex) {
        }
        return fout;
    }

    /**
     * Xóa file nguồn
     */
    private void delSource(File f) {
        //Xóa nếu có yêu cầu xóa
        if (isDelete) {
            f.delete();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Tạm bỏ">
//    private void writeBufferToFile(FileInputStream fin, FileOutputStream fout, byte[] buffer, boolean closeOutput) {
//        try {
//            fin.read(buffer);
//            fout.write(buffer);
//            fout.flush();
//            if (closeOutput) {
//                fout.close();
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(SplitController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    // </editor-fold>
}
