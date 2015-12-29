package domain;

public class Execute extends Thread {

	private Task task;//Task类
	private String UserMailAccount;//所属用户邮箱
	private int taskIndex;//在该用户的TaskQueue中的下标
	private int executeIndex;//在系统的ExecuteQueue中的下标
	private boolean stop = false;

	public Execute(){
		task = null;
		UserMailAccount = null;
		taskIndex = -1;
		executeIndex = -1;
	}

	public int getTaskId(){
		return task.getTaskID();
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
		
		if(request.thisType != ThisType.OrderTime)
		{
			while(true)
			{
				if(stop)
					break;
				try{
					if(request.ifThis() == true)
					{
						if(stop){
							return;
						}
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
				if(request.ifThis() == true){
					if(stop){
						return;
					}
					goal.thenThat();
				}
				
			}
			catch(InterruptedException ex)
			{
				/* 中断异常处理 */
				ex.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println("任务运行失败！");
				e.printStackTrace();
			}
		}
	}
	
	/* 停止运行 */
	public void stopExecute(){
		stop = true;
	}
	
}