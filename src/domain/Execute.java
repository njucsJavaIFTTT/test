package domain;

public class Execute extends Thread {

	private Task task;//Task类
	private String UserMailAccount;//所属用户邮箱
	private int taskIndex;//在该用户的TaskQueue中的下标
	private int executeIndex;//在系统的ExecuteQueue中的下标
	private boolean finish = false, stop = false;
	
	public Execute(){
		task = null;
		UserMailAccount = null;
		taskIndex = -1;
		executeIndex = -1;
	}
	
	public Execute(Task t, String userMail, int index)
	{
		try{
			task = (Task)t.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		UserMailAccount = new String(userMail);
		taskIndex = index;
		executeIndex = -1;
	}
	
	public void SetExecuteIndex(int index)
	{
		executeIndex = index;
	}
	
	public void run(){
		Request request = task.getRequest();
		Goal goal = task.getGoal();
		
		/* 
		 * 对用户扣除运行费用
		 * Add Here
		 * */
		
		if(request.thisType != ThisType.OrderTime)
		{
			while(true)
			{
				try{
					if(request.ifThis() == true)
					{
						goal.thenThat();
					}
				}
				catch(InterruptedException ex)
				{
					/* 中断异常处理 */
				}
				catch(Exception e)
				{
					System.out.println("任务运行失败！");
					e.printStackTrace();
				}
			}
		}
		else {
			try{
				if(request.ifThis() == true)
					goal.thenThat();
			}
			catch(InterruptedException ex)
			{
				/* 中断异常处理 */
			}
			catch(Exception e)
			{
				System.out.println("任务运行失败！");
				e.printStackTrace();
			}
		}
	}
	
	/* 暂停运行 */
	/* 继续运行 */
	/* 重新运行 */
	
}

/* 运行任务队列类 */
class ExecuteQueue{
	private Execute executes[];//运行任务数组指针
	private int top;
	private int maxLength;//当前队列最大长度
	
	public ExecuteQueue(int len){
		maxLength = 10;
		executes = new Execute[maxLength];
		top = 0;
	}
	
	/* 向运行队列中添加任务,返回任务在运行队列中的下标 */
	public int addExecute(Execute execute) {
		if(top < maxLength && top >= 0)
		{
			executes[top] = execute;
			top ++;
		}
		else if (top < 0)
			return -1;
		else
		{
			maxLength += 10;
			Execute[] temp = new Execute[maxLength];
			for(int i = 0; i < top; i ++)
			{
				temp[i] = executes[i];
			}
			temp[top] = execute;
			top ++;
			executes = temp;
		}
		return (top - 1) ;
	}
	
	/* 从运行队列中删除任务 */
	public boolean deleteItem(int index){
		if(index >= top || index < 0)
			return false;
		executes[index] = null;
		for(int i=index; i<top-1; i++){
			executes[i] = executes[i+1];
		}
		top--;
		return true;
	}
}
