package main.java.me.avankziar.wpc.spigot;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.ifh.spigot.administration.Administration;
import main.java.me.avankziar.wpc.spigot.assistance.BackgroundTask;
import main.java.me.avankziar.wpc.spigot.assistance.Utility;
import main.java.me.avankziar.wpc.spigot.cmd.TABCompletion;
import main.java.me.avankziar.wpc.spigot.cmd.WPCCommandExecutor;
import main.java.me.avankziar.wpc.spigot.cmd.wpc.ARGHowToRegister;
import main.java.me.avankziar.wpc.spigot.cmd.wpc.ARGRegister;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.wpc.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.CommandConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.CommandExecuteType;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.wpc.spigot.database.MysqlSetup;
import main.java.me.avankziar.wpc.spigot.database.YamlHandler;
import main.java.me.avankziar.wpc.spigot.database.YamlManager;
import main.java.me.avankziar.wpc.spigot.handler.ConfigHandler;
import main.java.me.avankziar.wpc.spigot.listener.AddPluginSupportListener;
import main.java.me.avankziar.wpc.spigot.listener.JavaPHPTaskEndListener;
import main.java.me.avankziar.wpc.spigot.listener.JoinListener;
import main.java.me.avankziar.wpc.spigot.metrics.Metrics;
import main.java.me.avankziar.wpc.spigot.objects.Parameter;
import main.java.me.avankziar.wpc.spigot.permission.Bypass;

public class WebPortalConnector extends JavaPlugin
{
	public static Logger log;
	private static WebPortalConnector plugin;
	public String pluginName = "WebPortalConnector";
	private YamlHandler yamlHandler;
	private YamlManager yamlManager;
	private MysqlSetup mysqlSetup;
	private MysqlHandler mysqlHandler;
	private Utility utility;
	private BackgroundTask backgroundTask;
	
	private ArrayList<BaseConstructor> helpList = new ArrayList<>();
	private ArrayList<CommandConstructor> commandTree = new ArrayList<>();
	private LinkedHashMap<String, ArgumentModule> argumentMap = new LinkedHashMap<>();
	private ArrayList<String> players = new ArrayList<>();
	
	public static String infoCommandPath = "CmdWpc";
	public static String infoCommand = "/";
	
	public Administration administrationConsumer;
	
	public void onEnable()
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=WPC
		log.info(" ██╗    ██╗██████╗  ██████╗ | API-Version: "+plugin.getDescription().getAPIVersion());
		log.info(" ██║    ██║██╔══██╗██╔════╝ | Author: "+plugin.getDescription().getAuthors().toString());
		log.info(" ██║ █╗ ██║██████╔╝██║      | Plugin Website: "+plugin.getDescription().getWebsite());
		log.info(" ██║███╗██║██╔═══╝ ██║      | Depend Plugins: "+plugin.getDescription().getDepend().toString());
		log.info(" ╚███╔███╔╝██║     ╚██████╗ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepend().toString());
		log.info("  ╚══╝╚══╝ ╚═╝      ╚═════╝ | LoadBefore: "+plugin.getDescription().getLoadBefore().toString());
		
		setupIFHAdministration();
		
		yamlHandler = new YamlHandler(plugin);
		
		String path = plugin.getYamlHandler().getConfig().getString("IFHAdministrationPath");
		boolean adm = plugin.getAdministration() != null 
				&& plugin.getYamlHandler().getConfig().getBoolean("useIFHAdministration")
				&& plugin.getAdministration().isMysqlPathActive(path);
		if(adm || yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlHandler = new MysqlHandler(plugin);
			mysqlSetup = new MysqlSetup(plugin, adm, path);
		} else
		{
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
			return;
		}
		
		utility = new Utility(plugin);
		backgroundTask = new BackgroundTask(plugin);
		
		setupBypassPerm();
		setupCommandTree();
		setupListeners();
		setupBstats();
		initParameters();
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(plugin);
		HandlerList.unregisterAll(plugin);
		log.info(pluginName + " is disabled!");
	}

	public static WebPortalConnector getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}

	public void setYamlManager(YamlManager yamlManager)
	{
		this.yamlManager = yamlManager;
	}
	
	public MysqlSetup getMysqlSetup() 
	{
		return mysqlSetup;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public BackgroundTask getBackgroundTask()
	{
		return backgroundTask;
	}
	
	private void setupCommandTree()
	{		
		infoCommand += plugin.getYamlHandler().getCommands().getString("wpc.Name");
		
		ArgumentConstructor htr = new ArgumentConstructor(CommandExecuteType.WPC_HOWTOREGISTER, "wpc_htr", 0, 0, 0, true, null);
		new ARGHowToRegister(plugin, htr);
		ArgumentConstructor register = new ArgumentConstructor(CommandExecuteType.WPC_REGISTER, "wpc_register", 0, 1, 2, false, null);
		new ARGRegister(plugin, register);
		
		CommandConstructor wpc = new CommandConstructor(CommandExecuteType.WPC, "wpc", false,
				htr, register);
		
		registerCommand(wpc.getPath(), wpc.getName());
		getCommand(wpc.getName()).setExecutor(new WPCCommandExecutor(plugin, wpc));
		getCommand(wpc.getName()).setTabCompleter(new TABCompletion(plugin));
	}
	
	public void setupBypassPerm()
	{
		String path = "Count.";
		for(Bypass.CountPermission bypass : new ArrayList<Bypass.CountPermission>(EnumSet.allOf(Bypass.CountPermission.class)))
		{
			Bypass.set(bypass, yamlHandler.getCommands().getString(path+bypass.toString()));
		}
		path = "Bypass.";
		for(Bypass.Permission bypass : new ArrayList<Bypass.Permission>(EnumSet.allOf(Bypass.Permission.class)))
		{
			Bypass.set(bypass, yamlHandler.getCommands().getString(path+bypass.toString()));
		}
	}
	
	public ArrayList<BaseConstructor> getCommandHelpList()
	{
		return helpList;
	}
	
	public void addingCommandHelps(BaseConstructor... objects)
	{
		for(BaseConstructor bc : objects)
		{
			helpList.add(bc);
		}
	}
	
	public ArrayList<CommandConstructor> getCommandTree()
	{
		return commandTree;
	}
	
	public CommandConstructor getCommandFromPath(String commandpath)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getPath().equalsIgnoreCase(commandpath))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public CommandConstructor getCommandFromCommandString(String command)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getName().equalsIgnoreCase(command))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public void registerCommand(String... aliases) 
	{
		PluginCommand command = getCommand(aliases[0], plugin);
	 
		command.setAliases(Arrays.asList(aliases));
		getCommandMap().register(plugin.getDescription().getName(), command);
	}
	 
	private static PluginCommand getCommand(String name, WebPortalConnector plugin) 
	{
		PluginCommand command = null;
	 
		try 
		{
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
	 
			command = c.newInstance(name, plugin);
		} catch (SecurityException e) 
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} catch (InstantiationException e) 
		{
			e.printStackTrace();
		} catch (InvocationTargetException e) 
		
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
	 
		return command;
	}
	 
	private static CommandMap getCommandMap() 
	{
		CommandMap commandMap = null;
	 
		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) 
			{
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);
	 
				commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
			}
		} catch (NoSuchFieldException e) 
		{
			e.printStackTrace();
		} catch (SecurityException e) 
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	 
		return commandMap;
	}
	
	public LinkedHashMap<String, ArgumentModule> getArgumentMap()
	{
		return argumentMap;
	}
	
	public ArrayList<String> getMysqlPlayers()
	{
		return players;
	}

	public void setMysqlPlayers(ArrayList<String> players)
	{
		this.players = players;
	}
	
	public void setupListeners()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new JoinListener(plugin), plugin);
		pm.registerEvents(new AddPluginSupportListener(), plugin);
		pm.registerEvents(new JavaPHPTaskEndListener(plugin), plugin);
	}
	
	public boolean existHook(String externPluginName)
	{
		if(plugin.getServer().getPluginManager().getPlugin(externPluginName) == null)
		{
			return false;
		}
		log.info(pluginName+" hook with "+externPluginName);
		return true;
	}
	
	public void setupBstats()
	{	
		int pluginId = 10344;
        new Metrics(this, pluginId);
	}
	
	private void setupIFHAdministration()
	{ 
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
		RegisteredServiceProvider<main.java.me.avankziar.ifh.spigot.administration.Administration> rsp = 
                getServer().getServicesManager().getRegistration(Administration.class);
		if (rsp == null) 
		{
		   return;
		}
		administrationConsumer = rsp.getProvider();
		log.info(pluginName + " detected InterfaceHub >>> Administration.class is consumed!");
	}
	
	public Administration getAdministration()
	{
		return administrationConsumer;
	}
	
	public void initParameters()
	{
		Parameter salt = new Parameter("salt", new ConfigHandler().getCryptSalt());
		if(plugin.getMysqlHandler().exist(Type.PARAMETER, "`parameters` = ?", salt.getKeyword()))
		{
			plugin.getMysqlHandler().updateData(Type.PARAMETER, salt, "`parameters` = ?", salt.getKeyword());
		} else
		{
			plugin.getMysqlHandler().create(Type.PARAMETER, salt);
		}
	}
}