import java.io.*;
import java.util.Calendar;
class MyDataBase
{
	public static final String dir = "reports";
	File file;
	Calendar calendar;
	
	//When the object of the myDataBase will be Created the constructor of the current class will be called...
	//And it will do its work written below...
	//This will Checks it only once when its object is created only...


	private String getDate(){			//This will return the Current Date...in Format(DD-MM-YYYY)
		calendar=Calendar.getInstance();
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		int month=(calendar.get(Calendar.MONTH)+1);
		int year=calendar.get(Calendar.YEAR);
		String date=(day+"-"+month+"-"+year);
		calendar=null;
		System.gc();
		return date;
	}
	
	// Instance Initilization Block
	{
		// Checks if the directory exists or not?
		File d = new File(this.dir);
		if(!d.exists()){ // If not exists...
			d.mkdir(); // Make that directory
		}
	}

	
	MyDataBase()	//This Constructor will check either the file with current date exists or Not...
	{					//If file doesn't exist then it will create a new File with current Date...
		
		String path=(dir+"\\"+getDate()+".data");
		try{
			file=new File(path);
			if(! file.exists()){
				file.createNewFile();
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}



	
	//This method Will Append in a File using fileoutputStream and file Writer.
	//FileOutputStream will open the file in append mode and
	//FileWriter will take the reference of the FileOutputStream and write on the current file.
	//After each step of writing it will save a file due of chance of loss of information issue...
	
	public boolean addRecord(String date,String from,String to,String cabin_no,String total_hours,String ammount,String per_hour_cost)
	{
		try{
			FileOutputStream fout=new FileOutputStream(file,true);
			PrintWriter pw=new PrintWriter(fout);
			String query=(date+";"+from+";"+to+";"+cabin_no+";"+total_hours+";"+ammount+";"+per_hour_cost);
			pw.println(query);
			pw.close();
			fout.close();
			return true;
		}catch(FileNotFoundException fex){
			System.out.println("File Not Found Exception Occured...");
			return false;
		}catch(IOException ioex){
			System.out.println("IOException occured...");
			return false;
		}
	}
}