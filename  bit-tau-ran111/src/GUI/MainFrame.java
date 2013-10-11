/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 *
 * Created on May 3, 2010, 5:43:45 PM
 */
package GUI;

import Controller.JoinController;
import Controller.ChecksumController;
import Controller.SplitController;
import Utilities.Utils;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author DoHongPhuc
 */
public class MainFrame extends javax.swing.JFrame {

    /** Creates new form Main */
    public MainFrame() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new File("D:\\MyLabs\\Project\\Psplitter\\Icon.png").getCanonicalPath()));
        } catch (IOException ex) {
        }
        this.setLocationRelativeTo(null);
        initMyCom();
    }

    private void initMyCom() {
        final SplitController splitController = new SplitController(this);
        final JoinController joinController = new JoinController(this);
        final ChecksumController checksumController = new ChecksumController(this);
        menuExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

//        lblAbout.setVisible(false);
        progessBar.setVisible(false);
        btnCancel.setVisible(false);
        //Cancel process
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //lblAbout.setVisible(true);
                progessBar.setVisible(false);
                btnCancel.setVisible(false);
                if (panelSplit.isShowing()) {
                    splitController.cancelProcess();
                    btnSplit.setEnabled(true);
                }
                if (panelJoin.isShowing()) {
                    joinController.cancelProcess();
                    btnJoin.setEnabled(true);
                }
                if(panelChecksum.isShowing()){
                    btnCheck.setVisible(true);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        groupEncrypt = new javax.swing.ButtonGroup();
        typeSplit = new javax.swing.ButtonGroup();
        panelMain = new javax.swing.JTabbedPane();
        panelJoin = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtJoinSource = new javax.swing.JTextField();
        btnJoinSource = new javax.swing.JButton();
        btnJoinTarget = new javax.swing.JButton();
        txtJoinTarget = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbxDelSourceJoin = new javax.swing.JCheckBox();
        btnJoin = new javax.swing.JButton();
        panelChecksum = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtFileCheckMd5 = new javax.swing.JTextField();
        btnChoiceCheckMd5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnCheck = new javax.swing.JButton();
        cbxTypeCheck = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCheckValue = new javax.swing.JTextArea();
        panelBoot = new javax.swing.JPanel();
        progessBar = new javax.swing.JProgressBar();
        btnCancel = new javax.swing.JButton();
        btnCancel.setEnabled(false);
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        menuEnglish = new javax.swing.JMenuItem();
        menuVietNanamese = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PSplitter 1.0");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        panelMain.setBackground(new java.awt.Color(255, 255, 255));
        panelSplit = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSplitSource = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setVisible(false);
        txtSplitTarget = new javax.swing.JTextField();
        txtSplitTarget.setEnabled(false);
        btnSplitSource = new javax.swing.JButton();
        btnSplitTarget = new javax.swing.JButton();
        btnSplitTarget.setEnabled(false);
        btnSplit = new javax.swing.JButton();
        btnSplit.setText("TẠO");
        cbxDelSplitSource = new javax.swing.JCheckBox();
        cbxDelSplitSource.setVisible(false);
        cbxDelSplitSource.setEnabled(false);
        rdaNumberSplit = new javax.swing.JRadioButton();
        rdaNumberSplit.setVisible(false);
        rdaNumberSplit.setEnabled(false);
        rdaSizeSplit = new javax.swing.JRadioButton();
        rdaSizeSplit.setEnabled(false);
        spnNumberSplit = new javax.swing.JSpinner();
        spnNumberSplit.setVisible(false);
        cbxSizeType = new javax.swing.JComboBox();
        spnSplitSize = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        cbxExtType = new javax.swing.JComboBox();
        cbxExtType.setEnabled(false);
        txtPassword = new javax.swing.JPasswordField();
        txtPassword.setVisible(false);
        txtPassword.setEditable(false);
        cbxShowText = new javax.swing.JCheckBox();
        cbxShowText.setVisible(false);
        cbxShowText.setEnabled(false);
        cbxUsePass = new javax.swing.JCheckBox();
        cbxUsePass.setVisible(false);
        cbxUsePass.setEnabled(false);
        
                panelSplit.setBackground(java.awt.Color.white);
                panelSplit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                panelSplit.setPreferredSize(new java.awt.Dimension(517, 200));
                
                        jLabel1.setText("File nguồn");
                        
                                jLabel2.setText("Thư mục đích");
                                
                                        btnSplitSource.setBackground(java.awt.Color.white);
                                        btnSplitSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choicefile.png"))); // NOI18N
                                        btnSplitSource.setText("Chọn file");
                                        
                                                btnSplitTarget.setBackground(java.awt.Color.white);
                                                btnSplitTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choicefolder.png"))); // NOI18N
                                                btnSplitTarget.setText("Thư mục");
                                                
                                                        btnSplit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
                                                        btnSplit.setForeground(new java.awt.Color(255, 153, 51));
                                                        btnSplit.setIcon(null); // NOI18N
                                                        
                                                                cbxDelSplitSource.setBackground(java.awt.Color.white);
                                                                cbxDelSplitSource.setText("Xóa file nguồn sau khi cắt xong");
                                                                
                                                                        rdaNumberSplit.setBackground(java.awt.Color.white);
                                                                        typeSplit.add(rdaNumberSplit);
                                                                        rdaNumberSplit.setText("Cắt theo số lượng");
                                                                        
                                                                                rdaSizeSplit.setBackground(java.awt.Color.white);
                                                                                typeSplit.add(rdaSizeSplit);
                                                                                rdaSizeSplit.setSelected(true);
                                                                                rdaSizeSplit.setText("Tạo chunk theo dung lượng");
                                                                                
                                                                                        spnNumberSplit.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(2), null, Integer.valueOf(1)));
                                                                                        spnNumberSplit.setEditor(new javax.swing.JSpinner.NumberEditor(spnNumberSplit, ""));
                                                                                        spnNumberSplit.setEnabled(false);
                                                                                        
                                                                                                cbxSizeType.setModel(new DefaultComboBoxModel(new String[] {"Byte", "KiloByte", "MegaByte", "GigaByte"}));
                                                                                                cbxSizeType.setSelectedIndex(2);
                                                                                                
                                                                                                        spnSplitSize.setModel(new SpinnerNumberModel(new Integer(512), new Integer(1), null, new Integer(10)));
                                                                                                        
                                                                                                                jLabel6.setText("Kiểu đuôi");
                                                                                                                
                                                                                                                        cbxExtType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*.001" }));
                                                                                                                        cbxExtType.setPreferredSize(new java.awt.Dimension(53, 22));
                                                                                                                        
                                                                                                                                cbxShowText.setBackground(java.awt.Color.white);
                                                                                                                                cbxShowText.setText("Hiện text");
                                                                                                                                
                                                                                                                                        cbxUsePass.setBackground(java.awt.Color.white);
                                                                                                                                        cbxUsePass.setText("Đặt pass");
                                                                                                                                        
                                                                                                                                                javax.swing.GroupLayout panelSplitLayout = new javax.swing.GroupLayout(panelSplit);
                                                                                                                                                panelSplitLayout.setHorizontalGroup(
                                                                                                                                                	panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                		.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                			.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                				.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addGap(10)
                                                                                                                                                					.addComponent(jLabel1)
                                                                                                                                                					.addGap(32)
                                                                                                                                                					.addComponent(txtSplitSource, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                					.addGap(6)
                                                                                                                                                					.addComponent(btnSplitSource, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                				.addGroup(panelSplitLayout.createParallelGroup(Alignment.TRAILING)
                                                                                                                                                					.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                						.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                							.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                								.addGap(10)
                                                                                                                                                								.addComponent(jLabel6)
                                                                                                                                                								.addGap(38)
                                                                                                                                                								.addComponent(cbxExtType, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                								.addGap(6)
                                                                                                                                                								.addComponent(cbxUsePass)
                                                                                                                                                								.addPreferredGap(ComponentPlacement.RELATED)
                                                                                                                                                								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                							.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                								.addGap(91)
                                                                                                                                                								.addGroup(panelSplitLayout.createParallelGroup(Alignment.TRAILING)
                                                                                                                                                									.addComponent(spnNumberSplit, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                									.addComponent(cbxDelSplitSource)))
                                                                                                                                                							.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                								.addGap(22)
                                                                                                                                                								.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                									.addComponent(rdaSizeSplit)
                                                                                                                                                									.addComponent(rdaNumberSplit))
                                                                                                                                                								.addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                                                                                								.addComponent(spnSplitSize, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                								.addPreferredGap(ComponentPlacement.RELATED)
                                                                                                                                                								.addComponent(cbxSizeType, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)))
                                                                                                                                                						.addGap(12)
                                                                                                                                                						.addGroup(panelSplitLayout.createParallelGroup(Alignment.TRAILING)
                                                                                                                                                							.addComponent(cbxShowText)
                                                                                                                                                							.addComponent(btnSplit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                                                                                                                                                					.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                						.addGap(10)
                                                                                                                                                						.addComponent(jLabel2)
                                                                                                                                                						.addGap(18)
                                                                                                                                                						.addComponent(txtSplitTarget, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                						.addGap(6)
                                                                                                                                                						.addComponent(btnSplitTarget, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
                                                                                                                                                			.addContainerGap(1, Short.MAX_VALUE))
                                                                                                                                                );
                                                                                                                                                panelSplitLayout.setVerticalGroup(
                                                                                                                                                	panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                		.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                			.addGap(11)
                                                                                                                                                			.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                				.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addGap(4)
                                                                                                                                                					.addComponent(jLabel1))
                                                                                                                                                				.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addGap(1)
                                                                                                                                                					.addComponent(txtSplitSource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                				.addComponent(btnSplitSource))
                                                                                                                                                			.addGap(6)
                                                                                                                                                			.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                				.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addGap(4)
                                                                                                                                                					.addComponent(jLabel2))
                                                                                                                                                				.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addGap(1)
                                                                                                                                                					.addComponent(txtSplitTarget, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                				.addComponent(btnSplitTarget))
                                                                                                                                                			.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING, false)
                                                                                                                                                				.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addGap(5)
                                                                                                                                                					.addGroup(panelSplitLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                                                						.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                							.addGap(4)
                                                                                                                                                							.addComponent(jLabel6))
                                                                                                                                                						.addGroup(panelSplitLayout.createSequentialGroup()
                                                                                                                                                							.addGap(1)
                                                                                                                                                							.addComponent(cbxExtType, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                						.addGroup(panelSplitLayout.createParallelGroup(Alignment.BASELINE)
                                                                                                                                                							.addComponent(cbxUsePass)
                                                                                                                                                							.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                						.addComponent(cbxShowText))
                                                                                                                                                					.addGap(6)
                                                                                                                                                					.addComponent(cbxDelSplitSource)
                                                                                                                                                					.addGap(8)
                                                                                                                                                					.addGroup(panelSplitLayout.createParallelGroup(Alignment.BASELINE)
                                                                                                                                                						.addComponent(rdaNumberSplit)
                                                                                                                                                						.addComponent(spnNumberSplit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                					.addGap(11)
                                                                                                                                                					.addGroup(panelSplitLayout.createParallelGroup(Alignment.BASELINE)
                                                                                                                                                						.addComponent(rdaSizeSplit)
                                                                                                                                                						.addComponent(spnSplitSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                						.addComponent(cbxSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                                                                                					.addGap(5))
                                                                                                                                                				.addGroup(Alignment.TRAILING, panelSplitLayout.createSequentialGroup()
                                                                                                                                                					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                                                					.addComponent(btnSplit, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                                                                                                                					.addGap(20))))
                                                                                                                                                );
                                                                                                                                                panelSplit.setLayout(panelSplitLayout);
                                                                                                                                                
                                                                                                                                                        panelMain.addTab("Tạo File Chunk", panelSplit); // NOI18N

        panelJoin.setBackground(java.awt.Color.white);

        jLabel3.setText("File Nguồn");

        btnJoinSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choicefile.png"))); // NOI18N
        btnJoinSource.setText("Chọn file");

        btnJoinTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choicefolder.png"))); // NOI18N
        btnJoinTarget.setText("Thư mục");

        jLabel4.setText("Thư mục đích");

        cbxDelSourceJoin.setBackground(java.awt.Color.white);
        cbxDelSourceJoin.setText("Xóa file sau khi dồn xong");

        btnJoin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnJoin.setForeground(new java.awt.Color(255, 153, 51));
        btnJoin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/join.png"))); // NOI18N

        javax.swing.GroupLayout panelJoinLayout = new javax.swing.GroupLayout(panelJoin);
        panelJoin.setLayout(panelJoinLayout);
        panelJoinLayout.setHorizontalGroup(
            panelJoinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJoinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelJoinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoinLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(txtJoinSource, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnJoinSource, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelJoinLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(txtJoinTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnJoinTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelJoinLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(cbxDelSourceJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJoinLayout.createSequentialGroup()
                .addContainerGap(408, Short.MAX_VALUE)
                .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelJoinLayout.setVerticalGroup(
            panelJoinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJoinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelJoinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoinLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(txtJoinSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJoinSource))
                .addGap(9, 9, 9)
                .addGroup(panelJoinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoinLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addGroup(panelJoinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtJoinTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnJoinTarget)))
                .addGap(6, 6, 6)
                .addComponent(cbxDelSourceJoin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        //panelMain.addTab("Gộp File", new javax.swing.ImageIcon(getClass().getResource("/images/join_s.png")), panelJoin); // NOI18N

        panelChecksum.setBackground(java.awt.Color.white);

        jLabel5.setText("File check");

        btnChoiceCheckMd5.setBackground(java.awt.Color.white);
        btnChoiceCheckMd5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choicefile.png"))); // NOI18N
        btnChoiceCheckMd5.setText("Chọn file");

        jLabel7.setText("Mã Hash");

        btnCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/check.png"))); // NOI18N
        btnCheck.setText("Check");

        cbxTypeCheck.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MD5", "MD2", "SHA-1", "SHA-256", "SHA-384", "SHA-512", "CRC32" }));

        txtCheckValue.setColumns(20);
        txtCheckValue.setRows(5);
        jScrollPane1.setViewportView(txtCheckValue);

        javax.swing.GroupLayout panelChecksumLayout = new javax.swing.GroupLayout(panelChecksum);
        panelChecksum.setLayout(panelChecksumLayout);
        panelChecksumLayout.setHorizontalGroup(
            panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChecksumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChecksumLayout.createSequentialGroup()
                        .addComponent(cbxTypeCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtFileCheckMd5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChoiceCheckMd5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        panelChecksumLayout.setVerticalGroup(
            panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChecksumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChecksumLayout.createSequentialGroup()
                        .addGroup(panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelChecksumLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel5))
                            .addGroup(panelChecksumLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtFileCheckMd5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(13, 13, 13)
                        .addGroup(panelChecksumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbxTypeCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnChoiceCheckMd5))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        //panelMain.addTab("MD5 & SHA-1", new javax.swing.ImageIcon(getClass().getResource("/images/checksum.png")), panelChecksum);

        panelBoot.setBackground(new java.awt.Color(255, 255, 255));
        panelBoot.setPreferredSize(new java.awt.Dimension(514, 25));

        progessBar.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        progessBar.setPreferredSize(new java.awt.Dimension(240, 23));
        panelBoot.add(progessBar);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(85, 23));
        panelBoot.add(btnCancel);

        jMenu3.setText("File");

        menuEnglish.setText("English");
        menuEnglish.setEnabled(false);
        jMenu3.add(menuEnglish);

        menuVietNanamese.setText("VietNamese");
        menuVietNanamese.setEnabled(false);
        jMenu3.add(menuVietNanamese);
        jMenu3.add(jSeparator1);

        menuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        menuExit.setText("Exit");
        jMenu3.add(menuExit);

        jMenuBar2.add(jMenu3);
        jMenuBar2.add(jMenu5);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelBoot, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(panelBoot, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Main
     * @param args
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    /// <editor-fold defaultstate="collapsed" desc="variable init">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnCheck;
    public javax.swing.JButton btnChoiceCheckMd5;
    public javax.swing.JButton btnJoin;
    public javax.swing.JButton btnJoinSource;
    public javax.swing.JButton btnJoinTarget;
    public javax.swing.JButton btnSplit;
    public javax.swing.JButton btnSplitSource;
    public javax.swing.JButton btnSplitTarget;
    public javax.swing.JCheckBox cbxDelSourceJoin;
    public javax.swing.JCheckBox cbxDelSplitSource;
    public javax.swing.JComboBox cbxExtType;
    public javax.swing.JCheckBox cbxShowText;
    public javax.swing.JComboBox cbxSizeType;
    public javax.swing.JComboBox cbxTypeCheck;
    public javax.swing.JCheckBox cbxUsePass;
    public javax.swing.ButtonGroup groupEncrypt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    public javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem menuEnglish;
    public javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuVietNanamese;
    private javax.swing.JPanel panelBoot;
    private javax.swing.JPanel panelChecksum;
    private javax.swing.JPanel panelJoin;
    private javax.swing.JTabbedPane panelMain;
    public javax.swing.JPanel panelSplit;
    public javax.swing.JProgressBar progessBar;
    public javax.swing.JRadioButton rdaNumberSplit;
    public javax.swing.JRadioButton rdaSizeSplit;
    public javax.swing.JSpinner spnNumberSplit;
    public javax.swing.JSpinner spnSplitSize;
    public javax.swing.JTextArea txtCheckValue;
    public javax.swing.JTextField txtFileCheckMd5;
    public javax.swing.JTextField txtJoinSource;
    public javax.swing.JTextField txtJoinTarget;
    public javax.swing.JPasswordField txtPassword;
    public javax.swing.JTextField txtSplitSource;
    public javax.swing.JTextField txtSplitTarget;
    public javax.swing.ButtonGroup typeSplit;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
