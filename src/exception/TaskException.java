package exception;
//自定义异常：任务存在的异常
public class TaskException extends Exception{

	public TaskException() {
		super();
	}

	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskException(String message) {
		super(message);
	}

	public TaskException(Throwable cause) {
		super(cause);
	}
}
