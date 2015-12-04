Java Resources -> src文件说明

包名				描述												所属层次
domain			存放系统的JavaBean类								【domain(域模型)层】
				(只包含简单的属性以及属性对应的get和set方法，
				不包含具体业务处理方法)
				提供给数据访问层、业务处理层、Web层使用
					 
dao				存放访问数据库的操作接口类						【数据访问层】
dao.impl		存放访问数据库的操作接口的实现类
service			存放处理系统业务接口类							【业务处理层】
service.impl	存放处理系统业务接口的实现类
web.controller	存放作为系统控制器的Servlet						【Web层(表现层)】
web.UI			存放为用户提供用户界面的servlet
web.filter		存放系统的用到的过滤器(Filter)
web.listener	存放系统的用到的监听器(Listener)
util			存放系统的通用工具类，
				提供给数据访问层、业务处理层、Web层来使用	 
junit.test		存放系统的测试类	 
