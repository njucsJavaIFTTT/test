package domain;

/* ����ִ��ʵ���࣬���̱߳�� */
public class TaskExecution extends Thread{
	private Request request;//that����ָ��
	private Goal goal;//that����ָ��
	private String UserMailAccount;//�����û������˺�
	
	public void run() {
		
	}
}

/* ����ϵͳ������ִ�ж���ʵ���� */
class TaskExecutionQueue {
	private TaskExecution[] taskExecutions;
	int top;
}