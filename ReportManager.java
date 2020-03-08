import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

class ReportManager
{
	JDialog frame;
	JPanel report_panel;
	
	JSplitPane list_report_split;
	JSplitPane table_lr_split;
	
	JTable table;
	DefaultTableModel dtm;
	JScrollPane table_sp;
	
	JList list;
	DefaultListModel dlm;
	JScrollPane list_sp;
	
	JButton show_report;
	JLabel total_income;
	JLabel total_hour_consumed;
	JLabel total_entries;
	
	
	
	ReportManager(JFrame invoker)
	{
		frame=new JDialog(invoker,"Daily Reports");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	frame.setLocation(200,100);
		frame.setSize(950,600);
		frame.setLayout(new BorderLayout());
		
		addReportsToDLM();  //   to be coded...
		list=new JList(dlm);
		list_sp=new JScrollPane(list);
		report_panel=new JPanel(new GridLayout(4,1,10,10));
		list_report_split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
		list_report_split.setTopComponent(list_sp);
		list_report_split.setBottomComponent(report_panel);
		
		report_panel.add(show_report=new JButton("Show Report"));
		report_panel.add(total_income=new JLabel("Total Income: "));
		report_panel.add(total_hour_consumed=new JLabel("Total Hour Utilized: "));
		report_panel.add(total_entries=new JLabel("Total Entries: "));
		
		total_income.setFont(new Font("Arial",Font.BOLD,17));
		total_hour_consumed.setFont(new Font("Arial",Font.BOLD,17));
		total_entries.setFont(new Font("Arial",Font.BOLD,17));

		
		
		addRecordsToDTM();	//to be coded...
		table=new JTable(dtm);
		table_sp=new JScrollPane(table);
		table_lr_split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
		table_lr_split.setLeftComponent(table_sp);
		table_lr_split.setRightComponent(list_report_split);
		
		list_report_split.setDividerSize(5);
		list_report_split.setDividerLocation(250);
		table_lr_split.setDividerSize(5);
		table_lr_split.setDividerLocation(690);

		
		list_sp.setMinimumSize(new Dimension(220,250));
		report_panel.setMinimumSize(new Dimension(220,200));
		table_sp.setMinimumSize(new Dimension(550,400));
		frame.setMinimumSize(new Dimension(600,400));
		
		show_report.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i=dtm.getRowCount()-1;i>=0;i--)
					dtm.removeRow(i);
				String path=list.getSelectedValue().toString();
				try{
					BufferedReader br=new BufferedReader(new FileReader(path));
					String s=br.readLine();
					while(s!=null){
						String[] atb=s.split(";");
						dtm.addRow(new Object[]{atb[0],atb[1],atb[2],atb[3],atb[4],atb[5],atb[6]});
						s=br.readLine();
					}
					br.close();
				//	total_income;			To be Coded...
				//	total_hour_consumed;
					calculate_Income_And_Hour();
					total_entries.setText("Total Entries: "+dtm.getRowCount());
					
				}catch(IOException ex){
					System.out.println(ex.getMessage());
				}
			}
		});
		
		table.setBackground(Color.ORANGE);
		table.setForeground(Color.BLACK);
		table.setSelectionBackground(Color.CYAN);
		table.setGridColor(Color.CYAN);
		table.setFont(new Font("Arial",Font.BOLD,14));
		
		
		list.setBackground(Color.LIGHT_GRAY);
		list.setSelectionBackground(Color.CYAN);
		
		show_report.setBackground(Color.GRAY);
		show_report.setFont(new Font("Arial",Font.BOLD,25));
		report_panel.setBackground(Color.LIGHT_GRAY);
		
		
		frame.add(table_lr_split);
		
		frame.revalidate();
	}	
	
	private void addReportsToDLM(){
		dlm=new DefaultListModel();
		
		File file=new File("Daily Reports\\");
		File[] report_files=file.listFiles();
		for(int i=0;i<report_files.length;i++)
			dlm.addElement(report_files[i]);
	}
	private void addRecordsToDTM(){
		dtm=new DefaultTableModel(new Object[]{"Date","From","To","Cabin #","Duration","Ammount","Cost per hour"},0);	
		try{
			list.setSelectedIndex(0);
			String path=list.getSelectedValue().toString();
			File file=new File(path);
			BufferedReader br=new BufferedReader(new FileReader(file));
			String s=br.readLine();
			while(s!=null){
				String[] atb=s.split(";");
				dtm.addRow(new Object[]{atb[0],atb[1],atb[2],atb[3],atb[4],atb[5],atb[6]});
				s=br.readLine();
			}
			br.close();
		//	total_income;			To Be Coded...
		//	total_hour_consumed;
		calculate_Income_And_Hour();
		
			total_entries.setText(("Total Entries: "+dtm.getRowCount()));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	private void calculate_Income_And_Hour(){
		float income=0;
		float total_min=0;
		for(int i=0;i<dtm.getRowCount();i++){
			try{
				String s1=dtm.getValueAt(i,4).toString();
				String[] time_split=s1.split(":");
				total_min+=(Float.parseFloat(time_split[0].trim())*60.0f+Float.parseFloat(time_split[1].trim())+Float.parseFloat(time_split[2].trim())/60.0f);
	
			
				s1=dtm.getValueAt(i,5).toString();
				String[] income_split=s1.split(" ");
				income+=Float.parseFloat(income_split[0].trim());
			}catch(IndexOutOfBoundsException ie){
				System.out.println(ie.getMessage());
			}
		}
		total_hour_consumed.setText("Total Hour Utilized: "+total_min/60.0f);
		total_income.setText("Total Income: "+income+" $");
	}
	
}