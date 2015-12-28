package domain;

/* 任务实体类 */
public class Task implements Cloneable{
	private String TaskName;//任务名，对于同一用户不可重复
	private Request request;//this任务指针
	private Goal goal;//that任务指针
	private double expense;//费用
	private int taskID;//任务编号
	private static int totalTask = 0;//任务总数
	
	public Task(String name,Request r,Goal g) throws CloneNotSupportedException
	{
		TaskName = new String(name);
		request = (Request)r.clone();
		goal = (Goal)g.clone();
		taskID = totalTask;
		
		/* 收费方式 */
		expense = 0;
		switch (request.thisType) {
		case OrderTime:
			expense += 50;
			break;
		case RecvMail:
			expense += 100;
			break;
		case MonitorWeibo:
			expense += 150;
			break;
		default:
			break;
		}
		switch (goal.thatType) {
		case SendMail:
			expense += 50;
			break;
		case SendWeibo:
			expense += 100;
			break;
		default:
			break;
		}
	}
	
	public String getTaskName() {
		return TaskName;
	}

	public double getExpense() {
		return expense;
	}

	public Request getRequest() {
		Request r = null;
		try{
			r = (Request)request.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return r;
	}
	
	public Goal getGoal() {
		Goal g = null;
		try{
			g = (Goal)goal.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return g;
	}
	
	public int getTaskID() {
		return taskID;
	}
	
	public static int getTotalTask() {
		return totalTask;
	}
	
	public static void setTotalTask() {
		totalTask ++;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Task temp = (Task)super.clone();
		temp.TaskName = new String(this.TaskName);
		temp.request = (Request)this.request.clone();
		temp.goal = (Goal)this.goal.clone();
		temp.expense = this.expense;
		temp.taskID = this.taskID;
		return temp;
	}
}

/* 面向用户的任务序列实体类 */
class TaskQueue implements Cloneable{
	private String UserMailAccount;//所属用户的账号邮箱
	private Task[] tasks;//任务数组指针
	private int top;
	private int maxLength;//当前序列最大容量
	
	/* 创建任务时传入任务所属用户的邮箱 */
	public TaskQueue(String MailAccount) {
		UserMailAccount = new String(MailAccount);
		maxLength = 10;
		tasks = new Task[maxLength];
		top = 0;
	}
	
	/* 增加任务，返回值0-正确，1-任务名重复，2-错误 */
	public int addTask(Task t) throws Exception
	{
		boolean repetition = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(t.getTaskName()))
			{
				repetition = true;
				break;
			}
		}
		if(repetition)
			return 1;
		if(top < maxLength && top >= 0)
		{
			tasks[top] = (Task)t.clone();
			top ++;
		}
		else if (top < 0)
			return 2;
		else
		{
			maxLength += 10;
			Task[] temp = new Task[maxLength];
			for(int i = 0; i < top; i ++)
			{
				temp[i] = tasks[i];
			}
			temp[top] = (Task)t.clone();
			top ++;
			tasks = temp;
		}
		return 1;
	}
	
	/* 删除任务，按任务名（任务名唯一），返回值0-找到，1-找不到 */
	public int deleteTask(String name)
	{
		boolean find = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(name))
			{
				find = true;
				for(int j = i; j < top - 1; j ++)
				{
					tasks[j] = tasks[j+1];
				}
				top --;
				tasks[top] = null;
				break;
			}
		}
		if(find)
			return 0;
		else
			return 1;
	}
	
	/* 修改任务，按任务名，返回值0-成功，1-名字重复，2-未找到 */
	public int setTask(String name,Task t) throws Exception
	{
		boolean find = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(name))
			{
				find = true;
				boolean repetition = false;
				for(int j = 0; j < top; j ++)
				{
					if(tasks[j].getTaskName().equals(t.getTaskName()))
					{
						repetition = true;
						break;
					}
				}
				if(repetition)
					return 1;
				else
					tasks[i] = (Task)t.clone();
				break;
			}
		}
		if(find)
			return 0;
		else 
			return 2;
	}
	
	/* 深复制 */
	public Object clone()throws CloneNotSupportedException {
		TaskQueue temp = (TaskQueue)super.clone();
		temp.UserMailAccount = new String(this.UserMailAccount);
		temp.top = this.top;
		temp.maxLength = this.maxLength;
		temp.tasks = new Task[maxLength];
		for(int i = 0; i < this.top; i ++)
		{
			temp.tasks[i] = (Task)this.tasks[i].clone();
		}
		return temp;
	}
}
