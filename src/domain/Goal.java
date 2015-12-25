package domain;

enum ThatType {SendWeibo,SendMail};

/* that任务抽象类 */
public abstract class Goal implements Cloneable{
	ThatType thatType;
	public abstract boolean thenThat() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Goal goal = (Goal)super.clone();
		return goal;
	}
}