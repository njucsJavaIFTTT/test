package DB;

import java.sql.*;
import domain.UserAccount;

public class DBHelper {// 用于打开或关闭数据库
	/*
	 * public static final String url = "jdbc:mysql://localhost:3306/Users";
	 * public static final String name = "com.mysql.jdbc.Driver"; public static
	 * final String user = "root"; public static final String password = "";
	 * 
	 * public Connection conn = null; public PreparedStatement pst = null;
	 * 
	 * public DBHelper(String sql) { try { Class.forName(name);//指定连接类型 conn =
	 * DriverManager.getConnection(url, user, password);//获取连接 pst =
	 * conn.prepareStatement(sql);//准备执行语句 } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	Connection connect = null;
	static String sql = null;// sql是要执行的语句
	public PreparedStatement pst = null;
	static ResultSet ret = null;// 返回结果集

	public DBHelper() {
		// 加载MYSQL JDBC驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		// 连接具体的数据库
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Users", "root", "root");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码

			System.out.println("Success connect Mysql server!(db-Users)");
		} catch (Exception e) {
			System.out.print("connect Mysql server error!(db-Users)");
			e.printStackTrace();
		}
		/*
		 * //执行sql语句 try { pst = connect.prepareStatement(sql);//准备执行语句
		 * 
		 * System.out.println("Success do '"+sql+"'!(db-Users)"); } catch
		 * (Exception e) { System.out.print("Fail do '"+sql+"'!(db-Users)");
		 * e.printStackTrace(); }
		 */
	}

	public void insertUser(UserAccount user) {
		// 执行sql语句
		try {
			sql = "INSERT INTO UserAccount VALUES(?,?)";
			pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getMailAccount());
			pst.setDouble(4, user.getBalance());
			pst.setInt(5, user.getLevel());
			pst.setInt(6, user.getCredit());
			pst.setDouble(7, user.getDiscount());
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-Users)");
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-Users)");
			e.printStackTrace();
		}
	}

	public String findUser(String uName) {
		// 执行sql语句
		try {
			sql = "select * from UserAccount where username='" + uName + "'";
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			ret = pst.executeQuery();// 执行语句，得到结果集
			String uPwd = ret.getString("pwd");
			System.out.println("username:" + uName + "get pwd:" + uPwd);
			System.out.println("Success do '" + sql + "'!(db-Users)");
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-Users)");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.connect.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
