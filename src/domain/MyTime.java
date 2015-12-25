package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime implements Cloneable{
	String hour, minute, second;
	
	public MyTime(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String tmp = df.format(new Date());
		hour = tmp.substring(0, 1);
		minute = tmp.substring(3,4);
		second = tmp.substring(6,7);
	}
	
	public MyTime(String h, String m, String s){
		hour = new String(h);
		minute = new String(m);
		second = new String(s);
	}
	
	public MyTime(MyTime t){
		hour = new String(t.hour);
		minute = new String(t.minute);
		second = new String(t.second);	
	}
	
	public Object clone() throws CloneNotSupportedException{
		MyTime newTime = new MyTime(this);
		return newTime;
	}
}
