package main.java.me.avankziar.wpc.spigot.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class TableWrapper
{
	@SerializedName("TABLEWRAPPER") private ArrayList<TableObject> tablelist;
	
	public TableWrapper(ArrayList<TableObject> tablelist)
	{
		setTablelist(tablelist);
	}

	public ArrayList<TableObject> getTablelist()
	{
		return tablelist;
	}

	public void setTablelist(ArrayList<TableObject> tablelist)
	{
		this.tablelist = tablelist;
	}
	
	public static void main(String[] args)
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("playeraccount", "secretcrafteconomyplayerdata");
		map.put("bankaccount", "secretcrafteconomybankdata");
		map.put("actionlog", "secretcrafteconomylogger");
		map.put("trendlog", "secretcrafteconomytrend");
		map.put("standingorder", "secretcrafteconomystandigorder");
		//map.put("loan", "secretcrafteconomy");
		//map.put("loggersettingspreset", "");
		/*ArrayList<TableObject> list = new ArrayList<>();
		list.add(new TableObject("playeraccount", "aepPlayeraccount"));
		list.add(new TableObject("bankaccount", "aepBankaccount"));
		list.add(new TableObject("actionlog", "aepActionLog"));*/
		String json = convertToJSON(map);
		System.out.println(json);
		List<TableObject> list2 = convertToList(json);
		for(TableObject t : list2)
		{
			System.out.println(t.toString());
		}
	}
	
	public static ArrayList<TableObject> convertToList(String jsonString)
	{
		Gson gson = new Gson();
		TableWrapper tables = gson.fromJson(jsonString, TableWrapper.class);
		return tables.getTablelist();
	}
	
	public static String convertToJSON(TableWrapper table) 
	{
	    Gson gson = new Gson();
	    String sgson = gson.toJson(table);
	    return sgson;
	}
	
	public static String convertToJSON(LinkedHashMap<String, String> map)
	{
		ArrayList<TableObject> list = new ArrayList<>();
		for(Entry<String, String> set : map.entrySet())
		{
			list.add(new TableObject(set.getKey(), set.getValue()));
		}
		return convertToJSON(new TableWrapper(list));
	}
	
	public static ArrayList<TableObject> convertToTableObjectList(LinkedHashMap<String, String> map)
	{
		ArrayList<TableObject> list = new ArrayList<>();
		for(Entry<String, String> set : map.entrySet())
		{
			list.add(new TableObject(set.getKey(), set.getValue()));
		}
		return list;
	}
}
