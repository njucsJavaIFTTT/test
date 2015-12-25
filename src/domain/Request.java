package domain;

enum ThisType {OrderTime,RecvMail,MonitorWeibo,MonitorWeiboWithinLimitTime};

/* this任务抽象类 */
public abstract class Request implements Cloneable{
	ThisType thisType;
	public abstract boolean ifThis() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Request request = (Request)super.clone();
		return request;
	}
}