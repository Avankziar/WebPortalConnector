package main.java.me.avankziar.wpc.spigot.handler;

public class WebTaskHandler
{
	public WebTaskHandler(){}
	
	/*public void addPluginTables(String pluginname, String aliasname, LinkedHashMap<String, String> tableMap)
	{
		if(WebPortalConnector.getPlugin().getMysqlHandler().exist(Type.PLUGINS, "`pluginname` = ?", pluginname))
		{
			PluginObject pobject = (PluginObject) WebPortalConnector.getPlugin().getMysqlHandler().getData(Type.PLUGINS, "`pluginname` = ?", pluginname);
			if(pobject == null)
			{
				//Error?
				return;
			}
			if(tableMap == null)
			{
				pobject.setActive(false);
				WebPortalConnector.getPlugin().getMysqlHandler().updateData(Type.PLUGINS, pobject, "`pluginname` = ?", pluginname);
				WebPortalConnector.getPlugin().getLogger().info("Plugin "+pluginname+" as Alias " + aliasname + " detected! Table cannot found! Disable the plugin for the WebPortal!");
				return;
			}
			pobject.setPluginTables(TableWrapper.convertToTableObjectList(tableMap));
			final boolean boo = pobject.isActive();
			WebPortalConnector.getPlugin().getMysqlHandler().updateData(Type.PLUGINS, pobject, "`pluginname` = ?", pluginname);
			if(boo)
			{
				WebPortalConnector.getPlugin().getLogger().info("Plugin "+pluginname+" as Alias " + aliasname + " detected! Table updated! Plugin is activated.");
			} else
			{
				WebPortalConnector.getPlugin().getLogger().info("Plugin "+pluginname+" as Alias " + aliasname + " detected! Table updated! However, plugin is disabled.");
			}
		} else
		{
			if(tableMap == null)
			{
				return;
			}
			PluginObject pobject = new PluginObject(pluginname, aliasname, true, TableWrapper.convertToTableObjectList(tableMap));
			WebPortalConnector.getPlugin().getMysqlHandler().create(Type.PLUGINS, pobject);
			WebPortalConnector.getPlugin().getLogger().info("Plugin "+pluginname+" as Alias " + aliasname + " detected! Create Mysql entry as well as activate this plugin for the WebPortal");
		}
	}*/
}
