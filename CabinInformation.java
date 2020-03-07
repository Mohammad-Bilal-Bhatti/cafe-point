import javax.swing.*;
interface CabinInformation
{
	public String getDuration();
	public String getStartTime();
	public String getEndTime();
	public String getDate();
	public JPanel getCabinReference();
	public static void setChargesPerHour(int rate){}
	public static int getChargesPerHour(){return 0;}
	public String getCabinName();
	public String getAmmount();
}