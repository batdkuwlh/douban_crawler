package com.ttky8.com.douban_crawler;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataBase {

	public static void InsertMovies(List<ShortMoiveModel> modelList) { 
	  try {
		  Connection conn = JDBCUtils.getConnection(); 
		  for (int i = 0; i < modelList.size(); i++) {
			  ShortMoiveModel model = modelList.get(i);
			  List<Object> params = new ArrayList<Object>();
			  params.add(model.getId());
			  Map<String, Object> m = JDBCUtils.findSimpleResult(conn, "select * from movie_list where douban_id = ?", params); 
			  if (!m.isEmpty()) {
				  continue;
			  }
			  String sql = "insert into movie_list (directors,rate,cover_x,star, url,casts,cover,douban_id,cover_y, title) values(?,?,?,?,?,?,?,?,?,?)"; 
			  PreparedStatement pstmt; 	  
			  pstmt = (PreparedStatement) conn.prepareStatement(sql); 
			  pstmt.setString(1, model.getDirectors()); 
			  pstmt.setString(2, model.getRate());
			  pstmt.setInt(3, model.getCover_x()); 
			  pstmt.setString(4, model.getStar());
			  pstmt.setString(5, model.getUrl()); 
			  pstmt.setString(6, model.getCasts());
			  pstmt.setString(7, model.getCover()); 
			  pstmt.setString(8, model.getId());
			  pstmt.setInt(9, model.getCover_y()); 
			  pstmt.setString(10, model.getTitle()); 
			  int result = pstmt.executeUpdate(); 
			  
			  pstmt.close(); 
		  }
		  conn.close(); 
	   } catch (SQLException e){ 
		  e.printStackTrace(); 
		} 
	  }
	
	public static Map<String, Object> get(String doubanId) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			List<Object> params = new ArrayList<Object>();
			params.add(doubanId);
			Map<String, Object> m = JDBCUtils.findSimpleResult(conn, "select * from movie where douban_id = ?", params); 
		    if (m.isEmpty()) {
			    return null;
		    }
		    return m;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) 
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			} 
		}
		return null;
	}
	
	public static boolean saveMovie(MovieModel model) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			List<Object> params = new ArrayList<Object>();
			params.add(model.doubanId);
			Map<String, Object> m = JDBCUtils.findSimpleResult(conn, "select * from movie where douban_id = ?", params); 
		    if (!m.isEmpty()) {
			    return false;
		    }
			String sql = "insert into movie (directors,scriptwriter,actors,type, region,lang,"
					+ "publish_date,duration,alias, imdb,score,votes,summary,year,comment1,"
					+ "comment2,comment3, douban_id, single_duration, episodes, cover, title) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(model.directors);
			list.add(model.scriptwriter);
			list.add(model.actors);
			list.add(model.type);
			list.add(model.region);
			list.add(model.lang);
			list.add(model.publishDate);
			list.add(model.duration);
			list.add(model.alias);
			list.add(model.imdb);
			list.add(model.score);
			list.add(model.votes);
			list.add(model.summary);
			list.add(model.year);
			list.add(model.comment1);
			list.add(model.comment2);
			list.add(model.comment3);
			list.add(model.doubanId);
			list.add(model.singleDuration);
			list.add(model.episodes);
			list.add(model.cover);
			list.add(model.title);
			JDBCUtils.updateByPreparedStatement(conn, sql, list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) 
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			} 
		}
		return true;
	}

}


final class JDBCUtils {
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/douban?useUnicode=true&characterEncoding=utf8";
	static String username = "root";
	static String password = "";
    
    private JDBCUtils(){}
    
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }    
    }
    
 
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * 增加、删除、改
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static boolean updateByPreparedStatement(Connection conn, String sql, List<Object>params)throws SQLException{
        boolean flag = false;
        int result = -1;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        //System.out.println(pstmt.toString());
        result = pstmt.executeUpdate();
        pstmt.close();
        flag = result > 0 ? true : false;
        return flag;
    }
 
    /**
     * 查询单条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static Map<String, Object> findSimpleResult(Connection conn, String sql, List<Object> params) throws SQLException{
        Map<String, Object> map = new HashMap<String, Object>();
        int index  = 1;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();//返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i=0; i<col_len; i++ ){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        pstmt.close();
        resultSet.close();
        return map;
    }
 
    /**查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> findModeResult(Connection conn, String sql, List<Object> params) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
 
        return list;
    }
 
    /**通过反射机制查询单条记录
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T findSimpleRefResult(Connection conn, String sql, List<Object> params,
                                     Class<T> cls )throws Exception{
        T resultObject = null;
        int index = 1;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            //通过反射机制创建一个实例
            resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
        }
        return resultObject;
 
    }
 
    /**通过反射机制查询多条记录
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> findMoreRefResult(Connection conn, String sql, List<Object> params,
                                         Class<T> cls )throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            //通过反射机制创建一个实例
            T resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;
    }
    
   
    public static void colseResource(Connection conn,Statement st,ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }
    
  
    public static void closeConnection(Connection conn) {
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn = null;
    }
    
    public static void closeStatement(Statement st) {
        if(st !=null) {
            try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        st = null;
    }
    
    public static void closeResultSet(ResultSet rs) {
        if(rs !=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rs = null;
    }
}