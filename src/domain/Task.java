package domain;

/* 任务实体类 */
public class Task implements Cloneable{
	private String TaskName;//任务名，对于同一用户不可重复
	private Request request;//that任务指针
	private Goal goal;//that任务指针
	
	public Object clone() throws CloneNotSupportedException {
		Task temp = (Task)super.clone();
		temp.TaskName = this.TaskName;
		temp.request = (Request)this.request.clone();
		temp.goal = (Goal)this.goal.clone();
		return temp;
	}
	
	/* 新建任务： 在页面显示可选的 THIS（ THAT）事件图标， 用户只需直接点击图标，
来完成 THIS（ THAT）事件的选择。 用户创建好之后，返回刚刚创建的任务信息给
用户。付款是采用扣除会员会费的方式。 */
	/* 查看、删除、修改任务： 任务查看功能可以显示用户已经创建的所有任务， 每条显
示内容必须包括任务的完整信息描述。 用户也可以选择删除已有的任务。 同时，用
户可以修改已有的任务。 */
	/* 开始、停止任务：用户可以手动开始、停止已有的任务。 任务的运行收费采用扣除
会员会费的方式，同时根据会员的等级来给予一定的折扣和积分。 */
}

/* 面向用户的任务序列实体类 */
class TaskQueue implements Cloneable{
	private String UserMailAccount;//所属用户的账号邮箱
	private Task[] tasks;//任务数组指针
	private int top;
	
	public Object clone()throws CloneNotSupportedException {
		TaskQueue temp = (TaskQueue)super.clone();
		temp.UserMailAccount = this.UserMailAccount;
		temp.top = this.top;
		temp.tasks = null;
		for(int i = 0; i < this.top; i ++)
		{
			temp.tasks[i] = (Task)this.tasks[i].clone();
		}
		return temp;
	}
}

enum ThisType {OrderTime,RecvMail,MonitorWeibo};
enum ThatType {SendWeibo,SendMail};

/* this任务抽象类 */
abstract class Request implements Cloneable{
	ThisType thisType;
	public abstract boolean ifThis() throws Exception;
	public abstract Object clone() throws CloneNotSupportedException;
}

/* that任务抽象类 */
abstract class Goal implements Cloneable{
	ThatType thatType;
	public abstract boolean thenThat() throws Exception;
	public abstract Object clone() throws CloneNotSupportedException;
}

/* this-定时任务 */
class OrderTime extends Request {
	public boolean ifThis() {
		return true;
	}
}

/* this-收到QQ邮件任务 */
class RecvMail extends Request {
	public boolean ifThis() {
		return true;
	}
}

/* this-监听微博任务 */
class MonitorWeibo extends Request {
	public boolean ifThis() {
		return true;
	}
}

/* that-发送微博任务 */
class SendWeibo extends Goal {
	public boolean thenThat() {
		return true;
	}
}

/* that-发送邮件任务 */
class SendMail extends Goal {
	public boolean thenThat() {
		return true;
	}
}

