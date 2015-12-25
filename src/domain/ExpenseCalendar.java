package domain;

import java.util.Date;
import java.sql.Timestamp;

/* 消费记录类 */
public class ExpenseCalendar {
	private Timestamp startDate;//点击运行任务的时间
	private int taskID;//任务编号
	private int num;//消费记录编号，根据date的先后来编号
	
	public ExpenseCalendar(Timestamp t,int tID,int n){
		startDate= t;
		taskID = tID;
		num = n;
	}
	
	public ExpenseCalendar getRecord(){
		return this;
	}
}