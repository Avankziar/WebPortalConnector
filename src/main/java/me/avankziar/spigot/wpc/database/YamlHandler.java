package main.java.me.avankziar.spigot.wpc.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.spigot.wpc.WebPortalConnector;
import main.java.me.avankziar.spigot.wpc.database.Language.ISO639_2B;

public class YamlHandler
{
	private WebPortalConnector plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	
	private File commands = null;
	private YamlConfiguration com = new YamlConfiguration();
	
	private String languages;
	private File language = null;
	private YamlConfiguration lang = new YamlConfiguration();

	public YamlHandler(WebPortalConnector plugin)
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public YamlConfiguration getConfig()
	{
		return cfg;
	}
	
	public YamlConfiguration getCommands()
	{
		return com;
	}
	
	public YamlConfiguration getLang()
	{
		return lang;
	}
	
	private YamlConfiguration loadYamlTask(File file, YamlConfiguration yaml)
	{
		try 
		{
			yaml.load(file);
		} catch (IOException | InvalidConfigurationException e) 
		{
			WebPortalConnector.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", file.getName())
					+ e.getMessage());
			e.printStackTrace();
		}
		return yaml;
	}
	
	@SuppressWarnings("deprecation")
	private boolean writeFile(File file, YamlConfiguration yml, LinkedHashMap<String, Language> keyMap)
	{
		yml.options().header("For more explanation see \n https://www.spigotmc.org/resources/simple-chat-channels.35220/");
		for(String key : keyMap.keySet())
		{
			Language languageObject = keyMap.get(key);
			if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
			} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
			}
		}
		try
		{
			yml.save(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean loadYamlHandler()
	{
		/*
		 * Init all path from all yamls files
		 */
		plugin.setYamlManager(new YamlManager());
		/*
		 * Load all files, which are unique, for examples config.yml, commands.yml etc.
		 */
		if(!mkdirStaticFiles())
		{
			return false;
		}
		/*
		 * Load all files, which exist mutiple versions. Languages etc.
		 */
		if(!mkdirDynamicFiles())
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles()
	{
		/*
		 * Create the plugin general directory
		 */
		File directory = new File(plugin.getDataFolder()+"");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * Init config.yml
		 */
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			WebPortalConnector.log.info("Create config.yml...");
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, config.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		cfg = loadYamlTask(config, cfg);
        if(cfg == null)
        {
        	return false;
        }
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());
		/*
		 * Define the language
		 */
		languages = plugin.getAdministration() == null 
				? cfg.getString("Language", "ENG").toUpperCase() 
				: plugin.getAdministration().getLanguage();
		/*
		 * Repeat for all other single flatfiles.
		 */
		commands = new File(plugin.getDataFolder(), "commands.yml");
		if(!commands.exists()) 
		{
			WebPortalConnector.log.info("Create commands.yml...");
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, commands.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		com = loadYamlTask(commands, com);
        if(com == null)
        {
        	return false;
        }
		writeFile(commands, com, plugin.getYamlManager().getCommandsKey());
		return true;
	}
	
	private boolean mkdirDynamicFiles()
	{
		//Compare the using languages and set this as standard
		List<Language.ISO639_2B> types = new ArrayList<Language.ISO639_2B>(EnumSet.allOf(Language.ISO639_2B.class));
		ISO639_2B languageType = ISO639_2B.ENG;
		for(ISO639_2B type : types)
		{
			if(type.toString().equals(languages))
			{
				languageType = type;
				break;
			}
		}
		/*
		 * Set the standard languages
		 */
		plugin.getYamlManager().setLanguageType(languageType);
		/*
		 * Start to make the specific languagefile.
		 * Attention! Only one file will be created.
		 */
		if(!mkdirLanguage())
		{
			return false;
		}
		/*
		 * Here a example to create multiple flatfile for one purpose.
		 * This example is been using for inventory-guis.
		 */
		if(!mkdirGUIs())
		{
			return false;
		}
		return true;
	}
	
	private boolean mkdirLanguage()
	{
		/*
		 * Making the "prefix" thing for the filename.
		 */
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		/*
		 * Adding a new directory
		 */
		File directory = new File(plugin.getDataFolder()+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * The rest is equals part from the config.yml
		 */
		language = new File(directory.getPath(), languageString+".yml");
		if(!language.exists()) 
		{
			WebPortalConnector.log.info("Create %lang%.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, language.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		lang = loadYamlTask(language, lang);
        if(lang == null)
        {
        	return false;
        }
		writeFile(language, lang, plugin.getYamlManager().getLanguageKey());
		return true;
	}
	
	private boolean mkdirGUIs()
	{
		
		return true;
	}
}