import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class CafeFrame
{
	File file;

	private int rows;	
	private int cabins;
	
	Cabin[] array;

	JFrame frame;
	JMenuBar menu_bar;
	JMenu open;
		JMenuItem exit;
		JMenuItem restart;
	JMenu options;
		JMenuItem change_cabins;
		JMenu change_cabin_properties;
			JMenuItem change_bar_color;
			JMenuItem change_cabin_color;		
		JMenu change_frame_color;
			JCheckBoxMenuItem black;
			JCheckBoxMenuItem gray;		
			JCheckBoxMenuItem white;
			ButtonGroup group;
		JMenuItem show_report;
	JMenu help;
		JMenuItem about_aurthor;
		JMenuItem about_product;

	JScrollPane scroll;
	JPanel cabin_panel;
		
	ImageIcon icon=new ImageIcon("icons\\logo.png");

	//Initilization Block...
	{	
		setRowsAndRate();
	}
	
	CafeFrame()
	{
		frame=new JFrame("Multimedia Cafe Point");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,1370,730);
		frame.setLayout(new BorderLayout());
		frame.setIconImage(icon.getImage());

		setMenuComponents();
		setMenuEvents();
		cabin_panel=new JPanel(new GridLayout(rows,6,10,10));
		scroll=new JScrollPane(cabin_panel);
		array=new Cabin[cabins];		//total no of cabins
		for(int i=0;i<array.length;i++){
			try{
				Thread.sleep(100);
			array[i]=new Cabin();
				Thread.sleep(100);
			cabin_panel.add(array[i].getCabinReference());
			}catch(InterruptedException e){}
		}
		
	//	for(int i=0;i<cabins;i++){
	//		cabin[i]=new Cabin();
	//		cabin_panel.add(cabin[i].getCabinReference());
	//	}
		frame.add(scroll,BorderLayout.CENTER);
		frame.setJMenuBar(menu_bar);
		frame.revalidate();
	}
	private void setMenuComponents(){
		menu_bar=new JMenuBar();
		open=new JMenu("Open");
			open.add(exit=new JMenuItem("Close",new ImageIcon("icons\\close.png")));
			open.add(restart=new JMenuItem("Restert",new ImageIcon("icons\\restart.png")));
		options=new JMenu("Options");
			options.add(change_cabins=new JMenuItem("Change Number of Cabins",new ImageIcon("icons\\cabin.png")));
			options.add(change_cabin_properties=new JMenu("Change Cabin Properties"));
				change_cabin_properties.setIcon(new ImageIcon("icons\\properties.png"));
				change_cabin_properties.add(change_bar_color=new JMenuItem("Change Cabin's Bar Color",new ImageIcon("icons\\cabin bar.png")));
				change_cabin_properties.add(change_cabin_color=new JMenuItem("Change Cabin's Frame Color",new ImageIcon("icons\\cabin frame.png")));
			options.add(change_frame_color=new JMenu("Change Frame Color"));
					change_frame_color.setIcon(new ImageIcon("icons\\frame.png"));
				change_frame_color.add(black=new JCheckBoxMenuItem("Black",new ImageIcon("icons\\black.png")));
				change_frame_color.add(gray=new JCheckBoxMenuItem("Gray",new ImageIcon("icons\\gray.png")));
				change_frame_color.add(white=new JCheckBoxMenuItem("White",new ImageIcon("icons\\white.png")));
				group=new ButtonGroup();
				white.setSelected(true);
				group.add(black);
				group.add(gray);
				group.add(white);
			options.add(show_report=new JMenuItem("Show Reports",new ImageIcon("icons\\report.png")));
		help=new JMenu("Help");
			help.add(about_aurthor=new JMenuItem("About Aurthor",new ImageIcon("icons\\admin.png")));
			help.add(about_product=new JMenuItem("About Product",new ImageIcon("icons\\logo.png")));
		menu_bar.add(open);
		menu_bar.add(options);
		menu_bar.add(help);
	}
	private void setMenuEvents(){
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		black.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				frame.getContentPane().setBackground(Color.BLACK);
				frame.getContentPane().setForeground(Color.BLACK);
			}
		});
		white.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				frame.getContentPane().setBackground(Color.WHITE);
				frame.getContentPane().setForeground(Color.WHITE);
			}
		});
		
		gray.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				frame.getContentPane().setBackground(Color.GRAY);
				frame.getContentPane().setForeground(Color.GRAY);
			}
		});
		about_product.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(about_product,"<html>This Product is very simple.<br> It is designed for the cyber cafe owners,<br> for the accountibility of their product usage.<br> This app can record each activity of<br> time: when user time starts and when it ends.");
			}
		});
		about_aurthor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(about_aurthor,"<html>The Aurthor of this Application is Mohammad Bilal Bhatti<br> of Batch F16. He is currently enrolled in his studies at MUET Jamshoro(Sindh).<br> This application idea came in aurthor mind in his studies<br> in third semester. And have completed his project<br> before mids of third semester.");
			}
		});
		show_report.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ReportManager(frame);
			}
		});
		change_cabins.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int i;
				do{
					String s=JOptionPane.showInputDialog(change_cabins,"Input the number of cabins: ","Change Cabins Quantity",JOptionPane.QUESTION_MESSAGE);
					i=-1;
					try{
						i=Integer.parseInt(s);
					}catch(NumberFormatException ed){
						JOptionPane.showMessageDialog(change_cabins,"Input Integer numbers only");
					}
				}
				while(i==-1);
				JOptionPane.showMessageDialog(change_cabins,i);
				boolean what=setNewCabins(i);
				JOptionPane.showMessageDialog(change_cabins,what);
				frame.dispose();
				Cabin.resetCountToZero();
				new CafeFrame();
				System.gc();
				
			}
		});
		change_bar_color.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=JColorChooser.showDialog(change_bar_color,"Choose Bar Color",null);
				JOptionPane.showMessageDialog(change_bar_color,("Selected Color is: "+c));
			}
		});
		change_cabin_color.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=JColorChooser.showDialog(change_cabin_color,"Choose Cabin Frame Color",null);
				JOptionPane.showMessageDialog(change_cabin_color,("Selected Color is: "+c));
			}
		});
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
				Cabin.resetCountToZero();
				new CafeFrame();
			}
		});
		
	}
	private void setRowsAndRate(){			//read the file and set no of rows and Rate.
		try{
			file=new File("properties\\Cafe Properties.txt");
			BufferedReader br=new BufferedReader(new FileReader(file));
		
			String s=br.readLine();
			int values[]=new int[2];
			int indx=-1;
			while(s!=null){
				String[] temp=s.split(":");
				values[++indx]=Integer.parseInt(temp[1]);
				s=br.readLine();
			}
			br.close();
			cabins=values[0];
			rows=(int)(Math.ceil(cabins/6.0f));

		
			Cabin.setChargesPerHour(values[1]);
		}catch(FileNotFoundException e){ System.out.println("File not found...");
		}catch(IOException e){System.out.println("IO Exception");}
	}
	private boolean setNewCabins(int no){
		try{	
			file=new File("properties\\Cafe Properties.txt");
			BufferedReader br=new BufferedReader(new FileReader(file));
			String[] line=new String[2];
			String s=br.readLine();
			int indx=-1;
			while(s!=null){
				line[++indx]=s;
				s=br.readLine();
			}
			br.close();
			
			line[0]=("cabins:"+no);
			PrintWriter pw=new PrintWriter(file);
			for(int i=0;i<line.length;i++){
				pw.println(line[i]);
			}
			pw.close();
			return true;
		
		}catch(FileNotFoundException fex){
			System.out.println("File Not Found...");
			return false;
		}catch(IOException ioxc){
			System.out.println("IOException Occured...");
			return false;
		}
		
		
	}
	private boolean setNewRate(int rate){
		try{	
			file=new File("properties\\Cafe Properties.txt");
			BufferedReader br=new BufferedReader(new FileReader(file));
			String[] line=new String[2];
			String s=br.readLine();
			int indx=-1;
			while(s!=null){
				line[++indx]=s;
				s=br.readLine();
			}
			br.close();
			
			line[1]=("rate:"+rate);
			PrintWriter pw=new PrintWriter(file);
			for(int i=0;i<line.length;i++){
				pw.println(line[i]);
			}
			pw.close();
			return true;
		
		}catch(FileNotFoundException fex){
			System.out.println("File Not Found...");
			return false;
		}catch(IOException ioxc){
			System.out.println("IOException Occured...");
			return false;
		}
	}
}