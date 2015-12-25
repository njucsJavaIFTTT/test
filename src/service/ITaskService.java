package service;

import domain.Execute;
import domain.Task;
import exception.TaskException;
import web.formbean.CreateTaskFormBean;

public interface ITaskService {

	// 从数据库按TaskId查找TaskFormBean
	CreateTaskFormBean findTask(int taskId) throws TaskException;

	// 将任务添加到运行队列
	Execute addTaskIntoExecuteList(Task task) throws TaskException;
	
	// 按照TaskId从运行队列中找到运行任务
	Execute findExecuteInList(int taskId) throws TaskException;
	
	// 创建Task任务
	Task createTask(CreateTaskFormBean formBean) throws TaskException;
	
	// 将TaskFormBean存入到数据库中
	void storeTask(CreateTaskFormBean formBean)throws TaskException;
}
