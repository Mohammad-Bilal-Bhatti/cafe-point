import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ShowTime
{
	static JDialog dialog;	//This is seted static because I Can call it my Its Class Name...
	static JButton ok;
	
	public static void showTime(Frame parent,String cabin_info)
	{
		dialog=new JDialog(parent,"Message",false);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setBounds(450,400,500,100);
		dialog.setLayout(new FlowLayout());
		dialog.setResizable(false);
		
		dialog.add(new JLabel(new ImageIcon("icons\\info.png")));
		dialog.add(new JLabel(cabin_info));
		dialog.add(ok=new JButton("OK"));
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		
		dialog.revalidate();		
	}
}