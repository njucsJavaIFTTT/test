package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/* this-定时任务 */
public class OrderTime extends Request {
	private MyDate date;
	private MyTime time;
	Timer timer;
	MyTimerTask myTT;
	
	public OrderTime() {
		date = new MyDate();
		time = new MyTime();
		timer = null;
		myTT = null;
		super.thisType = ThisType.OrderTime;
	}
	
	public OrderTime(MyDate d,MyTime t) {
		date = new MyDate(d);
		time = new MyTime(t);
		timer = null;
		myTT = null;
		super.thisType = ThisType.OrderTime;
	}

	public void SetDateAndTime(MyDate d,MyTime t)throws Exception
	{
		d = (MyDate)this.date.clone();
		t = (MyTime)this.time.clone();
	}
	
	public boolean ifThis() {
		timer = new Timer();
		myTT = new MyTimerTask(); //设置定时器进行定时
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tmp = null;
		try{
			tmp = df.parse(date.year+"-"+date.month+"-"+date.day+" "+time.hour+":"+time.minute+":"+time.second);
			timer.schedule(myTT, tmp);
		}
		catch(ParseException e){}
		try{
			while(true){
				Thread.sleep(1000);
				if(myTT.getReady() == true)
					break;
			}
		}
		catch(Exception e){return false;}
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		OrderTime orderTime = (OrderTime)super.clone();
		orderTime.date = (MyDate)this.date.clone();
		orderTime.time = (MyTime)this.time.clone();
		orderTime.thisType = ThisType.OrderTime;
		return orderTime;
	}
}
