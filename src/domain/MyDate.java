package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

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
