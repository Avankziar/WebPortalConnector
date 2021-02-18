package main.java.me.avankziar.wpc.spigot.objects;

public class PluginUser
{
	private String uuid;
	private String name;
	private String password;
	private boolean admin;
	
	public PluginUser(String uuid, String name, String password, boolean admin)
	{
		setUUID(uuid);
		setName(name);
		setPassword(password);
		setAdmin(admin);
	}
	
	public String getUUID()
	{
		return uuid;
	}

	public void setUUID(String uuid)
	{
		this.uuid = uuid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isAdmin()
	{
		return admin;
	}

	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}
}
