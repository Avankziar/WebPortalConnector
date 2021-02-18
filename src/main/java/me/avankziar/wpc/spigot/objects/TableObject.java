package main.java.me.avankziar.wpc.spigot.objects;

import com.google.gson.annotations.SerializedName;

public class TableObject
{
	@SerializedName("KEYWORD") private String keyword;
	@SerializedName("TABLENAME") private String tabelName;
	
	public TableObject(String keyword, String tableName)
	{
		setKeyword(keyword);
		setTabelName(tableName);
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getTabelName()
	{
		return tabelName;
	}

	public void setTabelName(String tabelName)
	{
		this.tabelName = tabelName;
	}
	
	public String toString()
	{
		return "TableObject["+this.keyword+","+this.tabelName+"]";
	}

}
