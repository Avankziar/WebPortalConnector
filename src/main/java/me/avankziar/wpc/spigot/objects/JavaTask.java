package main.java.me.avankziar.wpc.spigot.objects;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;

public class JavaTask
{
	private int id;
	private long timeStamp;
	private String pluginName;
	private LinkedHashMap<String, Object> taskJson;
	private boolean isOpen;
	private boolean wasSuccessful;
	private String errorMessage;
	
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
	}

}
