package main.java.me.avankziar.wpc.spigot.objects;

public class Parameter
{
	private String keyword;
	private String value;
	
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

}
