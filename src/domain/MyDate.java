package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/* 日期类   */
public class MyDate implements Cloneable{
	String year, month, day;
	
	public MyDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String tmp = df.format(new Date());
		year = tmp.substring(0, 3);
		month = tmp.substring(5, 6);
		day = tmp.substring(8,9);
	}
	
	public MyDate(String y, String m ,String d){
		year = new String(y);
		month = new String(m);
		day = new String(d);
	}
	
	public MyDate(MyDate d){
		year = new String(d.year);
		month = new String(d.month);
		day = new String(d.day);
	}
	
	public Object clone() throws CloneNotSupportedException{
		MyDate newDate = new MyDate(this);
		return newDate;
	}
}

/* 时间类 */
class MyTime implements Cloneable{
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
