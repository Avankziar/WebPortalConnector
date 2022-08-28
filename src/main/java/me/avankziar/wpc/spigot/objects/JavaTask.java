package main.java.me.avankziar.wpc.spigot.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.google.gson.Gson;

import main.java.me.avankziar.wpc.spigot.database.MysqlHandable;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler;

public class JavaTask implements MysqlHandable
{
	private int id;
	private long timeStamp;
	private String pluginName;
	private LinkedHashMap<String, Object> taskJson;
	private boolean isOpen;
	private boolean wasSuccessful;
	private String errorMessage;
	
	public JavaTask() {}
	
	public JavaTask(int id, long timeStamp, String pluginName, LinkedHashMap<String, Object> taskJson,
			boolean isOpen, boolean wasSuccessful, String errorMessage)
	{
		setId(id);
		setTimeStamp(timeStamp);
		setPluginName(pluginName);
		setTaskJson(taskJson);
		setOpen(isOpen);
		setWasSuccessful(wasSuccessful);
		setErrormessage(errorMessage);
	}
	
	public static void main2(String[] args)
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("plugin", "aep");
		map.put("methode", "pay");
		map.put("amount", 50.0);
		String json = convertToJson(map);
		System.out.println(json);
		LinkedHashMap<String, Object> map2 = convertToMap(json);
		for(Entry<String, Object> set : map2.entrySet())
		{
			System.out.println(set.getKey()+": "+set.getValue()+" | "+(set.getValue() instanceof Double));
		}
	}
	
	public static String convertToJson(LinkedHashMap<String, Object> map)
	{
		Gson gson = new Gson();
		String json = gson.toJson(map, LinkedHashMap.class);
		return json;
	}
	
	public static LinkedHashMap<String, Object> convertToMap(String json)
	{
		Gson gson = new Gson();
		LinkedHashMap<String, Object> type = new LinkedHashMap<>();
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> map = gson.fromJson(json, type.getClass());
		return map;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public LinkedHashMap<String, Object> getTaskJson()
	{
		return taskJson;
	}

	public void setTaskJson(LinkedHashMap<String, Object> taskJson)
	{
		this.taskJson = taskJson;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	public boolean isWasSuccessful()
	{
		return wasSuccessful;
	}

	public void setWasSuccessful(boolean wasSuccessful)
	{
		this.wasSuccessful = wasSuccessful;
	}

	public String getErrormessage()
	{
		return errorMessage;
	}

	public void setErrormessage(String errormessage)
	{
		this.errorMessage = errormessage;
	}

	public String getPluginName()
	{
		return pluginName;
	}

	public void setPluginName(String pluginName)
	{
		this.pluginName = pluginName;
	}@Override
	public boolean create(Connection conn, String tablename)
	{
		try
		{
			String sql = "INSERT INTO `" + tablename
					+ "`(`timestamp_unix`, `pluginname`, `methodejson`, `isopen`, `wassuccessful`, `errormessage`) " 
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, getTimeStamp());
		    ps.setString(2, getPluginName());
		    ps.setString(3, JavaTask.convertToJson(getTaskJson()));
		    ps.setBoolean(4, isOpen());
		    ps.setBoolean(5, isWasSuccessful());
		    ps.setString(6, getErrormessage());
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
					+ "` SET `timestamp_unix` = ?, `pluginname` = ?, `methodejson` = ?,"
					+ " `isopen` = ?, `wassuccessful` = ?, `errormessage` = ?" 
					+ " WHERE "+whereColumn;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, getTimeStamp());
		    ps.setString(2, getPluginName());
		    ps.setString(3, JavaTask.convertToJson(getTaskJson()));
		    ps.setBoolean(4, isOpen());
		    ps.setBoolean(5, isWasSuccessful());
		    ps.setString(6, getErrormessage());
			int i = 7;
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
				al.add(new JavaTask(rs.getInt("id"),
	        			rs.getLong("timestamp_unix"),
	        			rs.getString("pluginname"),
	        			JavaTask.convertToMap(rs.getString("methodejson")),
	        			rs.getBoolean("isopen"),
	        			rs.getBoolean("wassuccessful"),
	        			rs.getString("errormessage")));
			}
			return al;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not get a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return new ArrayList<>();
	}
	
	public static ArrayList<JavaTask> convert(ArrayList<Object> arrayList)
	{
		ArrayList<JavaTask> l = new ArrayList<>();
		for(Object o : arrayList)
		{
			if(o instanceof JavaTask)
			{
				l.add((JavaTask) o);
			}
		}
		return l;
	}
}