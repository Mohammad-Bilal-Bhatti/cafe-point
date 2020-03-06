import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
class Cabin implements CabinInformation
{
	static MyDataBase db=new MyDataBase();
	
	private static int count=0;
	private static int rate_per_hour=50;

	public static void resetCountToZero(){
		count=0;	//This is in Case of Restarting CafeFrame Class...
	}	
	//  Instance Initilizatin Block...
	{
		count++;
		cabin_name=count;
	}
	//*************************************************
	Calendar calendar;
	final int cabin_name;
	int start_h,start_m,start_s;
	int end_h,end_m,end_s;
	int dur_h,dur_m,dur_s;
	
	//**********************************************
	JPanel cabin;
	JPanel user;
	JLabel start_label;
	JLabel end_label;
	JLabel dur_label;
	JLabel date_label;
	JLabel total_label;
	JLabel status_label;
	
	
	JLabel start_time;
	JLabel end_time;
	JLabel dur_time;
	JLabel date_time;
	JLabel total_ammount;
	JLabel status;
	JLabel cab_name;

	JButton start_btn;
	JButton stop_btn;
/*
	start_label;
	end_label;
	dur_label;
	date_label;
	total_label;
	
	start_time;
	end_time;
	dur_time;
	date_time;
	total_ammount;
	
	start_btn;
	stop_btn;

*/	
	Thread dur_thread;
	
	Cabin()
	{
		Thread thread=new Thread(){
			public void run(){
				
				cabin=new JPanel(new BorderLayout());
				user=new JPanel(new GridLayout(7,2,10,10));
		
				cabin.add(cab_name=new JLabel(("Cabin no #     "+count)),BorderLayout.NORTH);
				cabin.add(user,BorderLayout.CENTER);
				
				user.add(start_label=new JLabel("Start Time:"));
				user.add(start_time=new JLabel("00:00:00"));
				user.add(end_label=new JLabel("End Time:"));
				user.add(end_time=new JLabel("00:00:00"));
				user.add(dur_label=new JLabel("Duration:"));
				user.add(dur_time=new JLabel("00:00:00"));
				user.add(date_label=new JLabel("Date:"));
				user.add(date_time=new JLabel("01/01/2018"));
				user.add(status_label=new JLabel("Status:"));
				user.add(status=new JLabel("Available"));
				user.add(start_btn=new JButton("Start"));
				user.add(stop_btn=new JButton("Stop"));
				user.add(total_label=new JLabel("Total:"));
				user.add(total_ammount=new JLabel("00.0 $"));
				stop_btn.setEnabled(false);
				
				cab_name.setForeground(Color.BLACK);
				cabin.setBackground(Color.YELLOW);
				user.setBackground(Color.ORANGE);
				start_time.setForeground(Color.RED);
				end_time.setForeground(Color.RED);
				dur_time.setForeground(Color.RED);
				date_time.setForeground(Color.RED);
				total_ammount.setForeground(Color.RED);
				start_btn.setBackground(Color.CYAN);
				stop_btn.setBackground(Color.RED);
				status.setForeground(Color.BLUE);
				setFont();
				
				start_btn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						reset();		//reset all parameters..
						start_time.setText(getCurrentTime());
						date_time.setText(getCurrentDate());
						dur_thread=new Thread(){
							int sec=0,min=0,hour=0;
							public void run(){
								while(true){
									try{
										dur_time.setText(hour+" : "+min+" : "+sec);
										this.sleep(1000); 		//sleeps for 1 second
										sec++;
										if(sec==60){
											min++;
											sec=0;
										}
										if(min==60){
											hour++;
											min=0;
										//	JOptionPane.showMessageDialog(user,("[ "+hour+" ]"+" Hour completed! of Cabin No: [ "+cabin_name+" ]"));
										String s="[ "+hour+" ]"+" Hour completed! of Cabin No: [ "+cabin_name+" ]";
										ShowTime.showTime(null,s);
										}
									}catch(InterruptedException e){}
								}
							}
						};
						dur_thread.setPriority(2);
						dur_thread.start();
						start_btn.setEnabled(false);
						status.setText("Busy");
						status.setForeground(Color.RED);
						stop_btn.setEnabled(true);
					}
				});
				stop_btn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						end_time.setText(getCurrentTime());
						dur_thread.stop();
						float time_in_min=calculateAmmount();	//return total Time consumed...
						float rpm=rate_per_hour/60.0f;
						if(time_in_min>=15 && time_in_min<60)
							time_in_min=60.0f;
						total_ammount.setText((rpm*time_in_min)+" $");
						
						start_btn.setEnabled(true);
						status.setText("Available");
						status.setForeground(Color.BLUE);
						stop_btn.setEnabled(false);
						//Add Record To the DataBase...
						db.addRecord(date_time.getText(),start_time.getText(),end_time.getText(),("Cabin# "+cabin_name),dur_time.getText(),total_ammount.getText(),(rate_per_hour+""));
					}
				});	
			}
		};
		thread.setDaemon(true);
		thread.start();
	}
	private void setFont(){
		Font s=new Font("Arial",Font.BOLD,15);
		Font l=new Font("Arial",Font.BOLD,20);

		cab_name.setFont(l);
		start_label.setFont(s);
		end_label.setFont(s);
		dur_label.setFont(s);
		date_label.setFont(s);
		total_label.setFont(s);
		status_label.setFont(s);

		start_time.setFont(l);
		end_time.setFont(l);
		dur_time.setFont(l);
		date_time.setFont(l);
		total_ammount.setFont(l);
		status.setFont(l);
	}
	private void reset(){
		start_time.setText("00:00:00");
		end_time.setText("00:00:00");
		dur_time.setText("00:00:00");
		total_ammount.setText("00.0 $");
	}
	private String getCurrentDate(){  //DD-MM-YYYY
		calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=(calendar.get(Calendar.MONTH)+1);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		return (day+" - "+month+" - "+year);
	}
	private String getCurrentTime(){
		calendar=Calendar.getInstance();
		int hour=calendar.get(Calendar.HOUR);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		return (hour+" : "+minute+" : "+second);
	}
	private float calculateAmmount(){
		String[] time=dur_time.getText().split(":");
		for(int i=0;i<time.length;i++)
			time[i]=time[i].trim();
		int hour=Integer.parseInt(time[0]);
		int min=Integer.parseInt(time[1]);
		int sec=Integer.parseInt(time[2]);
		float total_time=(hour*60.0f)+(min)+(sec/60.0f);	
		return total_time;
	}
	
	public String getDuration(){
		return (dur_time.getText());
	}
	public String getStartTime(){
		return (start_time.getText());
	}
	public String getEndTime(){
		return (end_time.getText());
	}
	public String getDate(){
		return (date_time.getText());
	}
	public String toString(){
		return ("Cabin no # "+cabin_name);
	}
	
	public JPanel getCabinReference(){
		return cabin;
	}
	public static int getChargesPerHour(){
		return rate_per_hour;
	}
	public static void setChargesPerHour(int rate){
		rate_per_hour=rate;
	}
	public String getCabinName(){
		return ("Cabin NO: "+cabin_name);
	}
	public String getAmmount(){
		return (total_ammount.getText());
	}	
}