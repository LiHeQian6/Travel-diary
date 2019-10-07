
    /**  
     * @Title: InsertMessage.java
     * @Package dao
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Administrator
     * @date 2019年10月7日 下午8:18:45 
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
     * @ClassName: InsertMessage
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author Administrator
     * @date 2019年10月7日
     *
     */

public class InsertMessage {
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

	// 更新数据
	public int insert(String sql) {
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
