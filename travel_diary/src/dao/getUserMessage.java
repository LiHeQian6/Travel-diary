
    /**  
     * @Title: getUserMessage.java
     * @Package dao
     * @Description: 查询用户账号密码
     * @author 孙建旺
     * @date 2019年10月3日 下午4:24:29 
     * @version V1.0  
     */
    
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
     * @ClassName: getUserMessage
     * @Description: 查询信息
     * @author 孙建旺
     * @date 2019年10月3日
     *
     */

public class getUserMessage {
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String CONN_STR = "jdbc:mysql://127.0.0.1:3306/traveldiary";
	private final String USER = "root";
	private final String PWD = "";
	private Connection conn;
	Statement statement;
	ResultSet resultSet;
	PreparedStatement preStmt;

	
	// 获取连接对象
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		if (null == conn || conn.isClosed()) {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(CONN_STR, USER, PWD);
		}
		return conn;
	}

	// 查询数据
	public String queryDate(String sql) throws ClassNotFoundException{
		String str = "null";
		try {
			getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				//toString item = new toString(resultSet.getString(1),resultSet.getString(2));
//				if(str.length()>0)
//					str += ";"+item.toString1();
//				else {
//					str += item.toString1();
//				}
				str = resultSet.getString(2)+";"+resultSet.getString(3);
				//System.out.println(resultSet.getString(1) + " " +resultSet.getString(2)+ " " +resultSet.getString(3)+ " " +resultSet.getString(4)+ " " +resultSet.getString(5));
			}
			return str;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	// 更新数据
	public int updateDate(String sql) {
		try {
			preStmt = conn.prepareStatement(sql);
			int n = preStmt.executeUpdate();
			if(n > 0 ) {
				preStmt.close();
				return 1;
			} else {
				preStmt.close();
				return 0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}

	}

	// 关闭连接
	public void closeConnection() {
		try {
			if (null != conn && !conn.isClosed()) {
				statement.close();
				resultSet.close();
				conn.close();
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
