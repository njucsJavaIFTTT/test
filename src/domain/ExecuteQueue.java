package domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* 运行任务队列类 */
public class ExecuteQueue{
	private static Set<Execute> list = new HashSet<>();
	
	public static Execute findExecuteInList(int taskId){
		Iterator<Execute> iterator = list.iterator();
		while (iterator.hasNext()) {
			Execute execute = iterator.next();
			if(execute.getTaskId() == taskId){
				return execute;
			}
		}
		return null;
	};
	
	/* 向运行队列中添加任务 */
	public static void addExecute(Execute execute) {
		list.add(execute);
	}
	
	/* 从运行队列中删除任务 */
	public static boolean deleteExecuteInList(int taskId){
		Iterator<Execute> iterator = list.iterator();
		while (iterator.hasNext()) {
			Execute execute = iterator.next();
			if(execute.getTaskId() == taskId){
				list.remove(execute);
				return true;
			}
		}
		return false;
	}
}

