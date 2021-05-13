package com.org.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.org.entity.newspaper;
import com.org.util.DatabaseUtil;

public class BaseDao {
	// private Connection conn;
	private Statement stmt = null;
	private Connection conn = null;

	public BaseDao(Connection conn) {
		try {
			this.conn = conn;
			this.stmt = conn.createStatement();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, null);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public <T> List<T> datasearch(String table, List<String> data, List<String> colname, int outputnum,
			Class<T> tClass) {
		String sql = "Select ";
		String ValueStr = " Where ";
		String NameStr = "*";
		ResultSet rs = null;
		T Dv = null;
		Class C_D = null;
		Field[] DF = null;
		try {
			Dv = tClass.newInstance();
			C_D = Dv.getClass();
			DF = tClass.getFields();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < colname.size(); i++) {
			if (i != data.size() - 1) {
				ValueStr += (colname.get(i) + " = '" + data.get(i) + "' and ");
				// NameStr += (colname.get(i) + ",");
				continue;
			}
			ValueStr += colname.get(i) + " = '" + data.get(i) + "';";
			// ameStr += colname.get(i);
		}
		sql += (NameStr + " From " + table + ValueStr);
		System.out.println("查询语句:" + sql);
		try {
			// System.out.println("执行到这了");
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtil.closeAll(conn, stmt, rs);
			e.printStackTrace();
		}

		List<T> t = new ArrayList<T>();
		Field nameField = null;
		int num = 0;
		int result_num = 0;
		// for(int i = 0; i < outputnum && !rs.isLast(); i++, rs.next()){//开始填数据
		try {
			rs.last();
			result_num = rs.getRow();
			rs.beforeFirst();// 移到初始位置
		} catch (SQLException e2) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 移到最后一行
		if (result_num == 0) {
			System.out.println("返回了0列");
			return null;
		}
		try {
			while (rs.next()) {
				Dv = tClass.newInstance();
				for (int j = 0; j < DF.length; j++) {
					// System.out.println(colname.get(j)+":"+rs.getString(colname.get(j)));
					nameField = C_D.getDeclaredField(DF[j].getName());
					nameField.set(Dv, rs.getString(DF[j].getName()));
				}
				t.add(Dv);
				num += 1;
				if (num == outputnum) {
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回" + t.size() + "列");
		return t;
	}
	public <T> List<T> datasearch(String table, List<String> data, List<String> colname, int outputnum,
			Class<T> tClass, String sort) {
		String sql = "Select ";
		String ValueStr = " Where ";
		String NameStr = "*";
		ResultSet rs = null;
		T Dv = null;
		Class C_D = null;
		Field[] DF = null;
		try {
			Dv = tClass.newInstance();
			C_D = Dv.getClass();
			DF = tClass.getFields();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < colname.size(); i++) {
			if (i != data.size() - 1) {
				ValueStr += (colname.get(i) + " = '" + data.get(i) + "' and ");
				// NameStr += (colname.get(i) + ",");
				continue;
			}
			ValueStr += colname.get(i) + " = '" + data.get(i) + "'";
			// ameStr += colname.get(i);
		}
		sql += (NameStr + " From " + table + ValueStr);
		sql += " ORDER BY " + sort + " DESC;";
		System.out.println("查询语句:" + sql);
		try {
			// System.out.println("执行到这了");
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtil.closeAll(conn, stmt, rs);
			e.printStackTrace();
		}

		List<T> t = new ArrayList<T>();
		Field nameField = null;
		int num = 0;
		int result_num = 0;
		// for(int i = 0; i < outputnum && !rs.isLast(); i++, rs.next()){//开始填数据
		try {
			rs.last();
			result_num = rs.getRow();
			rs.beforeFirst();// 移到初始位置
		} catch (SQLException e2) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 移到最后一行
		if (result_num == 0) {
			System.out.println("返回了0列");
			return null;
		}
		try {
			while (rs.next()) {
				Dv = tClass.newInstance();
				for (int j = 0; j < DF.length; j++) {
					// System.out.println(colname.get(j)+":"+rs.getString(colname.get(j)));
					nameField = C_D.getDeclaredField(DF[j].getName());
					nameField.set(Dv, rs.getString(DF[j].getName()));
				}
				t.add(Dv);
				num += 1;
				if (num == outputnum) {
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回" + t.size() + "列");
		return t;
	}
	public <T> List<T> datasearch(String table, String data, String colname, int outputnum, Class<T> tClass) {
		String sql = null;
		ResultSet rs = null;
		T Dv = null;
		Class C_D = null;
		Field[] DF = null;
		try {
			Dv = tClass.newInstance();
			C_D = Dv.getClass();
			DF = tClass.getFields();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql = "Select * From " + table + " Where " + colname + "='" + data + "';";
		System.out.println("查询语句:" + sql);
		try {
			// System.out.println("执行到这了");
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtil.closeAll(conn, stmt, rs);
			e.printStackTrace();
		}

		List<T> t = new ArrayList<T>();
		Field nameField = null;
		int num = 0;
		int result_num = 0;
		// for(int i = 0; i < outputnum && !rs.isLast(); i++, rs.next()){//开始填数据
		try {
			rs.last();
			result_num = rs.getRow();
			rs.beforeFirst();// 移到初始位置
		} catch (SQLException e2) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 移到最后一行
		if (result_num == 0) {
			System.out.println("返回了0行");
			return null;
		}
		try {
			while (rs.next()) {
				Dv = tClass.newInstance();
				for (int j = 0; j < DF.length; j++) {
					// System.out.println(colname.get(j)+":"+rs.getString(colname.get(j)));
					nameField = C_D.getDeclaredField(DF[j].getName());
					nameField.set(Dv, rs.getString(DF[j].getName()));
				}
				t.add(Dv);
				num += 1;
				if (num == outputnum) {
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回" + t.size() + "列");
		return t;
	}
	public <T> List<T> datasearch(String table, String data, String colname, int outputnum, Class<T> tClass, String sort) {
		String sql = null;
		ResultSet rs = null;
		T Dv = null;
		Class C_D = null;
		Field[] DF = null;
		try {
			Dv = tClass.newInstance();
			C_D = Dv.getClass();
			DF = tClass.getFields();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql = "Select * From " + table + " Where " + colname + "='" + data + "'";
		sql += " ORDER BY " + sort + " DESC;";
		System.out.println("查询语句:" + sql);
		try {
			// System.out.println("执行到这了");
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtil.closeAll(conn, stmt, rs);
			e.printStackTrace();
		}

		List<T> t = new ArrayList<T>();
		Field nameField = null;
		int num = 0;
		int result_num = 0;
		// for(int i = 0; i < outputnum && !rs.isLast(); i++, rs.next()){//开始填数据
		try {
			rs.last();
			result_num = rs.getRow();
			rs.beforeFirst();// 移到初始位置
		} catch (SQLException e2) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 移到最后一行
		if (result_num == 0) {
			System.out.println("返回了0行");
			return null;
		}
		try {
			while (rs.next()) {
				Dv = tClass.newInstance();
				for (int j = 0; j < DF.length; j++) {
					// System.out.println(colname.get(j)+":"+rs.getString(colname.get(j)));
					nameField = C_D.getDeclaredField(DF[j].getName());
					nameField.set(Dv, rs.getString(DF[j].getName()));
				}
				t.add(Dv);
				num += 1;
				if (num == outputnum) {
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回" + t.size() + "列");
		return t;
	}
	public <T> List<T> datalikesearch(String table, String data, String colname, int outputnum, Class<T> tClass) {
		String sql = null;
		ResultSet rs = null;
		T Dv = null;
		Class C_D = null;
		Field[] DF = null;
		try {
			Dv = tClass.newInstance();
			C_D = Dv.getClass();
			DF = tClass.getFields();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql = "Select * From " + table + " Where " + colname + " like '%" + data + "%';";
		System.out.println("查询语句:" + sql);
		try {
			// System.out.println("执行到这了");
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtil.closeAll(conn, stmt, rs);
			e.printStackTrace();
		}

		List<T> t = new ArrayList<T>();
		Field nameField = null;
		int num = 0;
		int result_num = 0;
		// for(int i = 0; i < outputnum && !rs.isLast(); i++, rs.next()){//开始填数据
		try {
			rs.last();
			result_num = rs.getRow();
			rs.beforeFirst();// 移到初始位置
		} catch (SQLException e2) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 移到最后一行
		if (result_num == 0) {
			System.out.println("返回了0行");
			return null;
		}
		try {
			while (rs.next()) {
				Dv = tClass.newInstance();
				for (int j = 0; j < DF.length; j++) {
					// System.out.println(colname.get(j)+":"+rs.getString(colname.get(j)));
					nameField = C_D.getDeclaredField(DF[j].getName());
					nameField.set(Dv, rs.getString(DF[j].getName()));
				}
				t.add(Dv);
				num += 1;
				if (num == outputnum) {
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回" + t.size() + "列");
		return t;
	}
	public <T> List<T> datalikesearch(String table, String data, String colname, int outputnum, Class<T> tClass, String sort) {
		String sql = null;
		ResultSet rs = null;
		T Dv = null;
		Class C_D = null;
		Field[] DF = null;
		try {
			Dv = tClass.newInstance();
			C_D = Dv.getClass();
			DF = tClass.getFields();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql = "Select * From " + table + " Where " + colname + " like '%" + data + "%'";
		sql += " ORDER BY " + sort + " DESC;";
		System.out.println("查询语句:" + sql);
		try {
			// System.out.println("执行到这了");
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DatabaseUtil.closeAll(conn, stmt, rs);
			e.printStackTrace();
		}

		List<T> t = new ArrayList<T>();
		Field nameField = null;
		int num = 0;
		int result_num = 0;
		// for(int i = 0; i < outputnum && !rs.isLast(); i++, rs.next()){//开始填数据
		try {
			rs.last();
			result_num = rs.getRow();
			rs.beforeFirst();// 移到初始位置
		} catch (SQLException e2) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 移到最后一行
		if (result_num == 0) {
			System.out.println("返回了0行");
			return null;
		}
		try {
			while (rs.next()) {
				Dv = tClass.newInstance();
				for (int j = 0; j < DF.length; j++) {
					// System.out.println(colname.get(j)+":"+rs.getString(colname.get(j)));
					nameField = C_D.getDeclaredField(DF[j].getName());
					nameField.set(Dv, rs.getString(DF[j].getName()));
				}
				t.add(Dv);
				num += 1;
				if (num == outputnum) {
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, stmt, rs);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回" + t.size() + "列");
		return t;
	}

	// 增加数据0表示数据库中有重复数据，无法添加
	// 1表示添加成功
	// -1表示添加发生未知错误
	public <T> boolean Adddata(String table, List<String> data, List<String> colname, Class<T> tClass) {
		/*
		 * INSERT INTO table_name ( field1, field2,...fieldN ) VALUES ( value1,
		 * value2,...valueN );
		 */
		String NameStr = "";
		String ValueStr = "";
		String sql = "";
		List<T> result = new ArrayList<T>();
		int rs;
		for (int i = 0; i < data.size(); i++) {
			if (i != data.size() - 1) {
				ValueStr += ("'" + data.get(i) + "',");
				NameStr += (colname.get(i) + ",");
				continue;
			}
			ValueStr += "'" + data.get(i) + "'";
			NameStr += colname.get(i);
		}
		result = datasearch(table, data, colname, 1, tClass);
		if (result!=null) {
			return false;// 0表示数据库中存在重复数据
		}
		else if(result==null){
			System.out.println("查询长度: 0");
		}
			sql += "INSERT INTO " + table + " (" + NameStr + ")VALUES(" + ValueStr + " );";
			System.out.println("添加语句:"+sql);
			try {
				rs = this.stmt.executeUpdate(sql);
				return true;// 返回改动的行数
			} catch (SQLException e) {
				DatabaseUtil.closeAll(conn, stmt, null);
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}

	public <T> boolean deletedata(String table, List<String> data, List<String> colname, Class<T> tClass) {// 1成功0失败
		String sql = "DELETE FROM " + table + " WHERE ";
		String ValueStr = "";
		int rs = 0;
		if (datasearch(table, data, colname, 1, tClass)!=null) {// 搜索到了删除信息的数据库位置
			for (int i = 0; i < data.size(); i++) {
				if (i != data.size() - 1)
					ValueStr += colname.get(i) + " = '" + data.get(i) + "' and ";
				else {
					ValueStr += colname.get(i) + " = '" + data.get(i) + "';";
				}
			}
			sql += ValueStr;
			System.out.println("删除语句:"+sql);
			try {
				rs = this.stmt.executeUpdate(sql);
				return rs != 0;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DatabaseUtil.closeAll(conn, stmt, null);
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T> boolean deletedata(String table, String data, String colname, Class<T> tClass) {// 1成功0失败
		String sql = "DELETE FROM " + table + " WHERE " + colname + " = '" + data + "';";
		System.out.println("删除语句:"+sql);
		int rs = 0;
		if (datasearch(table, data, colname, 1, tClass)!=null) {// 搜索到了删除信息的数据库位置
			try {
				rs = this.stmt.executeUpdate(sql);
				return true;
			} catch (SQLException e) {
				DatabaseUtil.closeAll(conn, stmt, null);
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public <T> boolean editdata(String table, List<String> odata, List<String> ocolname, List<String> data,
			List<String> colname, Class<T> tClass) {
		// odata ocolname用来检索数据库要修改数据的提示，用来从数据库里找到要修改的条目
		// UPDATE cs_user SET age = 18 , gender = '女' WHERE id = 4
		int rs = 0;// 默认修改失败
		String sql = "UPDATE " + table + " SET ";
		String ValueStr = "";
		String WValueStr = "";
		if (datasearch(table, odata, ocolname, 1, tClass)!=null) {// 找到了要编辑的位置
			for (int i = 0; i < data.size(); i++) {
				if (i != data.size() - 1) {
					ValueStr += (colname.get(i) + " = '" + data.get(i) + "' , ");
					WValueStr += (ocolname.get(i) + " = '" + odata.get(i) + "' and ");
				} else {
					ValueStr += (colname.get(i) + " = '" + data.get(i) + "' ");
					WValueStr += (ocolname.get(i) + " = '" + odata.get(i) + "';");
				}
			}
			sql += ValueStr + " WHERE " + WValueStr;
			System.out.println("编辑语句：" + sql);
			try {
				rs = this.stmt.executeUpdate(sql);
				return rs != 0;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DatabaseUtil.closeAll(conn, stmt, null);
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T> boolean editdata(String table, String odata, String ocolname, List<String> data, List<String> colname,
			Class<T> tClass) {
		// odata,ocolname用来检索数据库要修改数据的提示，用来从数据库里找到要修改的条目
		// UPDATE cs_user SET age = 18 , gender = '女' WHERE id = 4
		int rs = 0;// 默认修改失败
		String sql = "UPDATE " + table + " SET ";
		String ValueStr = "";
		String WValueStr = "";
		if (datasearch(table, odata, ocolname, 1, tClass) != null) {// 找到了要编辑的位置
			for (int i = 0; i < data.size(); i++) {
				if (i != data.size() - 1) {
					ValueStr += (colname.get(i) + " = '" + data.get(i) + "' , ");
				} else {
					ValueStr += (colname.get(i) + " = '" + data.get(i) + "' ");
				}
			}
			sql += ValueStr + " WHERE " + ocolname + "='" + odata + "';";
			System.out.println("编辑语句：" + sql);
			try {
				rs = this.stmt.executeUpdate(sql);
				return rs != 0;
			} catch (SQLException e) {
				DatabaseUtil.closeAll(conn, stmt, null);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 测试search
		DatabaseUtil D1 = new DatabaseUtil();
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = D1.getConnection();
		} catch (SQLException e) {
			DatabaseUtil.closeAll(conn, null, null);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BaseDao m1 = new BaseDao(conn);
		List<String> colname = new ArrayList<String>();
		List<newspaper> getdata = new ArrayList<newspaper>();
		colname.add("username");
		// colname.add("newsid");
		List<String> data = new ArrayList<String>();
		data.add("网易");
		// data.add("wyylFE1EHGHJ00038FO9");
		int outputnum = 10;
		Class tClass = newspaper.class;
		getdata = m1.datasearch("newspaper", data, colname, outputnum, tClass);
		if (getdata != null) {
			for (int i = 0; i < getdata.size(); i++) {
				System.out.println("第" + i + "个数据为: " + getdata.get(i));
			}
		} else {
			System.out.println("拦截成功");
		}

		// 测试add
		/*
		 * BaseDao m1 = new BaseDao(); int istrue = 0; List<String> colname =
		 * new ArrayList<String>(); List<String> data = new ArrayList<String>();
		 * colname.add("newsid"); colname.add("authorid");
		 * colname.add("newstitle"); colname.add("newstheme");
		 * colname.add("hidename"); colname.add("newscollect");
		 * colname.add("username"); colname.add("news_content_path");
		 * colname.add("newslike"); colname.add("newstime");
		 * colname.add("newsOurl"); colname.add("newsviewsnum");
		 * colname.add("imageurl"); data.add("newsidtest");
		 * data.add("authoridtest"); data.add("titletest");
		 * data.add("newstheme"); data.add("1"); data.add("collecttest");
		 * data.add("usernametest"); data.add("pathtest"); data.add("liketest");
		 * data.add("timetest"); data.add("urltest"); data.add("viewtest");
		 * data.add("imagetest"); istrue = m1.Adddata("newspaper", data,
		 * colname, newspaper.class); if(istrue==1){ System.out.println("成功");
		 * }else if(istrue != 1) { System.out.println(""+istrue+"失败"); }
		 */
		// 测试编辑
		/*
		 * BaseDao m1 = new BaseDao(); int editnum; List<String> ocolname = new
		 * ArrayList<String>(); List<String> odata = new ArrayList<String>();
		 * List<String> colname = new ArrayList<String>(); List<String> data =
		 * new ArrayList<String>(); Class tClass = newspaper.class;
		 * ocolname.add("newsid"); ocolname.add("authorid");
		 * ocolname.add("newstitle"); ocolname.add("newstheme");
		 * ocolname.add("hidename"); ocolname.add("newscollect");
		 * ocolname.add("username"); ocolname.add("news_content_path");
		 * ocolname.add("newslike"); ocolname.add("newstime");
		 * ocolname.add("newsOurl"); ocolname.add("newsviewsnum");
		 * ocolname.add("imageurl"); odata.add("newsidtest");
		 * odata.add("authoridtest"); odata.add("titletest");
		 * odata.add("newstheme"); odata.add("1"); odata.add("collecttest");
		 * odata.add("usernametest"); odata.add("pathtest");
		 * odata.add("liketest"); odata.add("timetest"); odata.add("urltest");
		 * odata.add("viewtest"); odata.add("imagetest"); colname.add("newsid");
		 * data.add("editnewsid"); editnum = m1.editdata("newspaper", odata,
		 * ocolname, data, colname, tClass); if(editnum!=0){
		 * System.out.println("修改成功"); }else if(editnum!=1){
		 * System.out.println("修改失败"); }
		 */
		// 删除测试
		/*
		 * BaseDao m1 = new BaseDao(); int delenum; List<String> colname = new
		 * ArrayList<String>(); List<String> data = new ArrayList<String>();
		 * Class tClass = newspaper.class; colname.add("newsid");
		 * colname.add("authorid"); colname.add("newstitle");
		 * colname.add("newstheme"); colname.add("hidename");
		 * colname.add("newscollect"); colname.add("username");
		 * colname.add("news_content_path"); colname.add("newslike");
		 * colname.add("newstime"); colname.add("newsOurl");
		 * colname.add("newsviewsnum"); colname.add("imageurl");
		 * data.add("editnewsid"); data.add("authoridtest");
		 * data.add("titletest"); data.add("newstheme"); data.add("1");
		 * data.add("collecttest"); data.add("usernametest");
		 * data.add("pathtest"); data.add("liketest"); data.add("timetest");
		 * data.add("urltest"); data.add("viewtest"); data.add("imagetest");
		 * delenum = m1.deletedata("newspaper", data, colname, newspaper.class);
		 * if(delenum!=0){ System.out.println("删除成功"); }else if(delenum==0){
		 * System.out.println("删除失败"); }
		 */
	}
}
