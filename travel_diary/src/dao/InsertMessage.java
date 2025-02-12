
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
	bean.Messages messages = new bean.Messages();
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String CONN_STR = "jdbc:mysql://127.0.0.1:3306/traveldiary";
	private final String USER = "root";
	private final String PWD = messages.getPWD();
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

	public int queryDate(String sql) throws ClassNotFoundException{
		String str = "null";
		int n=0;
		try {
			getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				n = resultSet.getInt(1);
				//System.out.println(resultSet.getString(1) + " " +resultSet.getString(2)+ " " +resultSet.getString(3)+ " " +resultSet.getString(4)+ " " +resultSet.getString(5));
			}
			return n;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
	// 更新数据
	public int insert(String sql) {
		try {
			preStmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			int n=0;
			preStmt.executeUpdate();
			ResultSet rs=preStmt.getGeneratedKeys();
			 if (rs.next()) {
		            n = rs.getInt(1);
		        }
			if(n > 0 ) {
				preStmt.close();
				return n;
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
