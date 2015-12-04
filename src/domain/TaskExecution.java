package domain;

/* 任务执行实体类，多线程编程 */
public class TaskExecution extends Thread{
	private Request request;//that任务指针
	private Goal goal;//that任务指针
	private String UserMailAccount;//所属用户邮箱账号
	
	public void run() {
		
	}
}

/* 面向系统的任务执行队列实体类 */
class TaskExecutionQueue {
	private TaskExecution[] taskExecutions;
	int top;
}