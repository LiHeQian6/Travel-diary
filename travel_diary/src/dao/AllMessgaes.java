
    /**  
     * @Title: AllMessgaes.java
     * @Package dao
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Administrator
     * @date 2019年10月6日 下午1:28:23 
     * @version V1.0  
     */
    
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.amap.api.maps.model.LatLng;


/**
     * @ClassName: AllMessgaes
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author Administrator
     * @date 2019年10月6日
     *
     */

public class AllMessgaes {
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

	// 查询数据
	public ArrayList queryDate(String sql) throws ClassNotFoundException{
		ArrayList list = new ArrayList();
		try {
			getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			System.out.println(sql);
			while(resultSet.next()) {
				bean.Messages messages = new bean.Messages(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getDouble(8),
						resultSet.getDouble(7),
						resultSet.getString(5),
						resultSet.getString(6),
						resultSet.getString(9),
						resultSet.getString(10),
						resultSet.getInt(4));
				list.add(messages);
				//System.out.println(resultSet.getString(1) + " " +resultSet.getString(2)+ " " +resultSet.getString(3)+ " " +resultSet.getString(4)+ " " +resultSet.getString(5));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
