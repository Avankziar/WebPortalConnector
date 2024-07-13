package main.java.me.avankziar.spigot.wpc.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import main.java.me.avankziar.spigot.wpc.database.MysqlHandable;
import main.java.me.avankziar.spigot.wpc.database.MysqlHandler;

public class Parameter implements MysqlHandable
{
	private String keyword;
	private String value;
	
	public Parameter() {}
	
	public Parameter(String keyword, String value)
	{
		setKeyword(keyword);
		setValue(value);
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	
	@Override
	public boolean create(Connection conn, String tablename)
	{
		try
		{
			String sql = "INSERT INTO `" + tablename
					+ "`(`keywords`, `parameters`) " 
					+ "VALUES(?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getKeyword());
	        ps.setString(2, getValue());
	        int i = ps.executeUpdate();
	        MysqlHandler.addRows(MysqlHandler.QueryType.INSERT, i);
	        return true;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not create a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return false;
	}

	@Override
	public boolean update(Connection conn, String tablename, String whereColumn, Object... whereObject)
	{
		try
		{
			String sql = "UPDATE `" + tablename
					+ "` SET `keywords` = ?, `parameters` = ?" 
					+ " WHERE "+whereColumn;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getKeyword());
	        ps.setString(2, getValue());
			int i = 3;
			for(Object o : whereObject)
			{
				ps.setObject(i, o);
				i++;
			}			
			int u = ps.executeUpdate();
			MysqlHandler.addRows(MysqlHandler.QueryType.UPDATE, u);
			return true;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not update a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return false;
	}

	@Override
	public ArrayList<Object> get(Connection conn, String tablename, String orderby, String limit, String whereColumn, Object... whereObject)
	{
		try
		{
			String sql = "SELECT * FROM `" + tablename
				+ "` WHERE "+whereColumn+" ORDER BY "+orderby+limit;
			PreparedStatement ps = conn.prepareStatement(sql);
			int i = 1;
			for(Object o : whereObject)
			{
				ps.setObject(i, o);
				i++;
			}
			
			ResultSet rs = ps.executeQuery();
			MysqlHandler.addRows(MysqlHandler.QueryType.READ, rs.getMetaData().getColumnCount());
			ArrayList<Object> al = new ArrayList<>();
			while (rs.next()) 
			{
				al.add(new Parameter(
	        			rs.getString("keywords"),
	        			rs.getString("parameters")));
			}
			return al;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not get a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return new ArrayList<>();
	}
	
	public static ArrayList<Parameter> convert(ArrayList<Object> arrayList)
	{
		ArrayList<Parameter> l = new ArrayList<>();
		for(Object o : arrayList)
		{
			if(o instanceof Parameter)
			{
				l.add((Parameter) o);
			}
		}
		return l;
	}
}