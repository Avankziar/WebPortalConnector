package main.java.me.avankziar.wpc.spigot;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.wpc.spigot.assistance.BackgroundTask;
import main.java.me.avankziar.wpc.spigot.assistance.Utility;
import main.java.me.avankziar.wpc.spigot.cmd.TABCompletion;
import main.java.me.avankziar.wpc.spigot.cmd.WPCCommandExecutor;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.wpc.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.CommandConstructor;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler;
import main.java.me.avankziar.wpc.spigot.database.MysqlSetup;
import main.java.me.avankziar.wpc.spigot.database.YamlHandler;
import main.java.me.avankziar.wpc.spigot.database.YamlManager;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.wpc.spigot.listener.AddPluginSupportListener;
import main.java.me.avankziar.wpc.spigot.listener.JavaPHPTaskEndListener;
import main.java.me.avankziar.wpc.spigot.listener.JoinListener;
import main.java.me.avankziar.wpc.spigot.metrics.Metrics;
import main.java.me.avankziar.wpc.spigot.objects.Parameter;
import main.java.me.avankziar.wpc.spigot.objects.PluginSettings;
import main.java.me.avankziar.wpc.spigot.permission.BypassPermission;
import main.java.me.avankziar.wpc.spigot.permission.KeyHandler;

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
	public static String baseCommandI = "wpc";
	
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
		
		yamlHandler = new YamlHandler(plugin);
		
		if (yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlHandler = new MysqlHandler(plugin);
			mysqlSetup = new MysqlSetup(plugin);
		} else
		{
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
			return;
		}
		
		PluginSettings.initSettings(plugin);
		
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
		if (yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			if (mysqlSetup.getConnection() != null) 
			{
				mysqlSetup.closeConnection();
			}
		}
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
		
		ArgumentConstructor htr = new ArgumentConstructor(baseCommandI+"_htr", 0, 0, 0, true, null);
		PluginSettings.settings.addCommands(KeyHandler.WPC_HTR, htr.getCommandString().trim());
		ArgumentConstructor register = new ArgumentConstructor(baseCommandI+"_register", 0, 1, 2, false, null);
		PluginSettings.settings.addCommands(KeyHandler.WPC_REGISTER, register.getCommandString().trim());
		
		CommandConstructor wpc = new CommandConstructor("wpc", false,
				htr);
		
		registerCommand(wpc.getPath(), wpc.getName());
		getCommand(wpc.getName()).setExecutor(new WPCCommandExecutor(plugin, wpc));
		getCommand(wpc.getName()).setTabCompleter(new TABCompletion(plugin));
		PluginSettings.settings.addCommands(KeyHandler.WPC, wpc.getCommandString().trim());
		
		addingHelps(wpc,
						htr, register);
	}
	
	public void setupBypassPerm()
	{
		BypassPermission.init(plugin);
	}
	
	public ArrayList<BaseConstructor> getHelpList()
	{
		return helpList;
	}
	
	public void addingHelps(BaseConstructor... objects)
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
	
	public boolean reload() throws IOException
	{
		if(!yamlHandler.loadYamlHandler())
		{
			return false;
		}
		if(yamlHandler.getConfig().getBoolean("Mysql.Status", false))
		{
			mysqlSetup.closeConnection();
			if(!mysqlSetup.loadMysqlSetup())
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
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
	
	public void initParameters()
	{
		Parameter salt = new Parameter("salt", PluginSettings.settings.getCryptSalt());
		if(plugin.getMysqlHandler().exist(Type.PARAMETER, "`parameters` = ?", salt.getKeyword()))
		{
			plugin.getMysqlHandler().updateData(Type.PARAMETER, salt, "`parameters` = ?", salt.getKeyword());
		} else
		{
			plugin.getMysqlHandler().create(Type.PARAMETER, salt);
		}
	}
}