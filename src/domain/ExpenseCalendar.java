package domain;

import java.util.Date;
import java.sql.Timestamp;

/* 消费记录类 */
public class ExpenseCalendar {
	private Timestamp startDate;//点击运行任务的时间
	private int taskID;//任务编号
	private int num;//消费记录编号，根据date的先后来编号
	private String ownerMail;//用户mail
	private double payment;//本次任务消费金额
	
	//public ExpenseCalendar(){};
	
	public ExpenseCalendar(Timestamp startDate, int taskID, int num, String ownerMail,double payment) {
		super();
		this.startDate = startDate;
		this.taskID = taskID;
		this.num = num;
		this.ownerMail = ownerMail;
		this.payment=payment;
	}

	
	@Override
	public String toString() {
		return startDate.toString() + ", taskID=" + taskID + ", num=" + num + ", payment=" + payment;
	}


	public ExpenseCalendar getRecord(){
		return this;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOwnerMail() {
		return ownerMail;
	}

	public void setOwnerMail(String ownerMail) {
		this.ownerMail = ownerMail;
	}
	
}