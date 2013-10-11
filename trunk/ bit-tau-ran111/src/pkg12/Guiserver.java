package pkg12;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import GUI.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
class threadlissten implements Runnable
{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Server.openServer();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class Topology
{
    public String ipAddress;
    public String chunkName;
    public int flag;
};
class threadlisstenBroasCast implements Runnable
{

	@Override
	public void run() {
		Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
		discoveryThread.start();
	}
	
}

public class Guiserver extends JFrame {
	private JPanel contentPane;
	private static JTable table;
	static DefaultTableModel tableModel;
	static Thread th;
	static Thread brcThread;
	public static ArrayList<Topology> topology =new ArrayList<Topology>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guiserver frame = new Guiserver();
					String[]title={"Tên File","Ngày Tạo","Kiểu File", "Trạng Thái","Kích Thước", "Đường Dẫn"}; 
					tableModel=new DefaultTableModel(title,0);
					
					 th =new Thread(new threadlissten() );
					th.start();
					 brcThread = new Thread(new threadlisstenBroasCast());
					brcThread.start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static String ReadNumFile(String filePath, int index) {
		StringBuffer lines = new StringBuffer();
        try {
        	Scanner in = new Scanner(new FileInputStream(filePath), "UTF-8");
        	int dem =0;
        	while (in.hasNext())
        	{
        		lines = new StringBuffer();
        		lines.append(in.nextLine());
        		dem ++;
        		if(dem == index)
        			break;
        	}
        	in.close();
        	} catch (Exception e) {
        	// TODO: handle exception
        	} 
        
		return lines.toString();
	}
	public static void LoadFileChose(File theFile) throws Exception{
		
		final List<JProgressBar> bars = new ArrayList<JProgressBar>(); 
        final AbstractTableModel model = ((AbstractTableModel) table.getModel());
		
		 String fileName = theFile.getName().substring(0, theFile.getName().lastIndexOf("."));
		 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 String datemodifire = sdf.format(theFile.lastModified());
		 String typeFile = theFile.getName().substring(theFile.getName().lastIndexOf(".") );
		 double size = Double.parseDouble( ReadNumFile(theFile.getPath(), 2));
		 size = size/(1024);
		 String filePath = theFile.getPath();
		 File sourceFileIp = new File ("DataInput/"+ fileName);
		 String stt = "";
		 double ptram;
		 DecimalFormat df = new DecimalFormat("#.##");
		 if(!sourceFileIp.exists())
			 stt= "0";
		 else
		 {
			 File []countF = sourceFileIp.listFiles();
			 double dem =0;
			 for(File item : countF)
		        {
					if(item.getName().indexOf("torrent")<0){
					dem++;
					}
		        }
			double numfileTorrent = Double.parseDouble( ReadNumFile(theFile.getPath(), 1));
			ptram = (dem / numfileTorrent)*100;
			
			stt =  df.format(ptram);
			
		 }
		 
		 
		 tableModel.addRow(new Object[]{fileName ,datemodifire,typeFile, stt, df.format( size) + " KB", filePath});
		 
		 
		 table.setModel(tableModel); 
		 for (int i = 0; i < table.getRowCount(); i++) {
	            bars.add(new JProgressBar(0, 100));
	        }
		 TableColumnModel columnModel = table.getColumnModel();
	       
	        columnModel.getColumn(3).setCellRenderer(new TableCellRenderer() { //sets a progress bar as renderer
	            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	                JProgressBar bar = bars.get(row);
	                String vl = table.getValueAt(table.getRowCount()-1, 3).toString();
	                vl=vl.substring(0, vl.lastIndexOf("."));
	                Integer values =Integer.parseInt( vl); 
	                bar.setValue(values);
	                return bar;
	            }
	        });
	}
	/**
	 * Create the frame.
	 */
	public Guiserver() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				th.stop();
				brcThread.stop();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 463);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmOpenFileDownload = new JMenuItem("Open File Download (torrent file)");
		mntmOpenFileDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter fillter = new FileNameExtensionFilter(
						"choses type file .torrent", "torrent");
				fc.setFileFilter(fillter);
				int n = fc.showOpenDialog(getParent());
				if (n == JFileChooser.OPEN_DIALOG) {
					File input = fc.getSelectedFile();
					try {
						LoadFileChose(input);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		mntmOpenFileDownload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		mnMenu.add(mntmOpenFileDownload);
		
		JMenuItem mntmOpenFileUpload = new JMenuItem("Open File Upload");
		mnMenu.add(mntmOpenFileUpload);
		
		JMenuItem mntmCreateFiletorrent = new JMenuItem("Create File .torrent");
		mntmCreateFiletorrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				MainFrame chunkFile=new MainFrame();
				chunkFile.show();
			}
		});
		mntmCreateFiletorrent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		mnMenu.add(mntmCreateFiletorrent);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		JButton btnNewButton = new JButton("Save file");
		
		final JButton btnDownloadFile = new JButton("DownLoad File");
		btnDownloadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				String filePath=table.getValueAt(rows[0], 5).toString();
				try{
					FileInputStream fileInPutStream = new FileInputStream(filePath);
					Reader reader = new java.io.InputStreamReader(fileInPutStream, "UTF-8");
					BufferedReader buffReader = new BufferedReader(reader);
					//StringBuilder stringBuilder = new StringBuilder();
					String line = null;
					while ((line = buffReader.readLine()) != null)
					{
						//stringBuilder.append(line + "\n");
						if(line.length()>=40)
						{//line = Chrysanthemum.jpg.001 098bc4448e4860accde00b0c19eae19fab9e43b2
							if(!DiscoveryThread.CheckChunkExists(line.substring(0, line.lastIndexOf(" ")).trim()))
							{
								System.out.println("broadcasst client send filename to server: " + line);
								Thread clientimplementatio = new Thread(new Clientimplementation().getInstance(line));
								clientimplementatio.start();
							}
						}
					}
					reader.close();
					
					/*for (Topology i : topology) {
						String chunkN = i.chunkName.substring(0, i.chunkName.lastIndexOf(" ")).trim();
						if(!DiscoveryThread.CheckChunkExists(chunkN))
						{
							//download
							System.out.println("broadcasst client download file: "+ chunkN);
							String args[]=  {"get " + chunkN , i.ipAddress, chunkN};
							Client.main(args);
						}
					}*/
					
				}catch(Exception ec)
				{
					
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnDownloadFile)
					.addPreferredGap(ComponentPlacement.RELATED, 624, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnDownloadFile)))
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int[] rows = table.getSelectedRows();
				String sd = table.getModel().getValueAt(rows[0], 3).toString();
				Double b = Double.parseDouble(sd);
				if(b<=0)
					btnDownloadFile.setText("DownLoad File");
				else
					btnDownloadFile.setText("Resume DownLoad File");
			}
		});
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	public void writeFile(byte[] data, String fileName) throws IOException{
		  FileOutputStream out = new FileOutputStream(fileName);
		  out.write(data);
		  out.close();
		}
}
