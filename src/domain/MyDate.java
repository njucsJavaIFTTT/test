package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/* ������   */
public class MyDate implements Cloneable{
	public String year, month, day;
	
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

/* ʱ���� */
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

/* ��ʱ�������� */
class MyTimerTask extends TimerTask{
	boolean ready = false;
	public void run() {
		ready = true;
	}
	public boolean getReady(){
		return ready;
	}	
}

/* 微博调试用 */
class Log {
	
	static Logger log = Logger.getLogger(Log.class.getName());
	
    public static void logDebug(String message) {
			log.debug(message);
	}

	public static void logInfo(String message) {
			log.info(message);
	}
}
