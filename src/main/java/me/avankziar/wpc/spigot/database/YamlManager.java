package main.java.me.avankziar.wpc.spigot.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.wpc.spigot.assistance.SHA256Cryption;
import main.java.me.avankziar.wpc.spigot.database.Language.ISO639_2B;
import main.java.me.avankziar.wpc.spigot.permission.Bypass;

public class YamlManager
{
	private ISO639_2B languageType = ISO639_2B.GER;
	//The default language of your plugin. Mine is german.
	private ISO639_2B defaultLanguageType = ISO639_2B.GER;
	
	//Per Flatfile a linkedhashmap.
	private static LinkedHashMap<String, Language> configKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> commandsKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> languageKeys = new LinkedHashMap<>();
	/*
	 * Here are mutiplefiles in one "double" map. The first String key is the filename
	 * So all filename muss be predefine. For example in the config.
	 */
	private static LinkedHashMap<String, LinkedHashMap<String, Language>> guisKeys = new LinkedHashMap<>();
	
	public YamlManager()
	{
		initConfig();
		initCommands();
		initLanguage();
	}
	
	public ISO639_2B getLanguageType()
	{
		return languageType;
	}

	public void setLanguageType(ISO639_2B languageType)
	{
		this.languageType = languageType;
	}
	
	public ISO639_2B getDefaultLanguageType()
	{
		return defaultLanguageType;
	}
	
	public LinkedHashMap<String, Language> getConfigKey()
	{
		return configKeys;
	}
	
	public LinkedHashMap<String, Language> getCommandsKey()
	{
		return commandsKeys;
	}
	
	public LinkedHashMap<String, Language> getLanguageKey()
	{
		return languageKeys;
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, Language>> getGUIKey()
	{
		return guisKeys;
	}
	
	/*
	 * The main methode to set all paths in the yamls.
	 */
	public void setFileInput(YamlConfiguration yml, LinkedHashMap<String, Language> keyMap, String key, ISO639_2B languageType)
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(keyMap.get(key).languageValues.get(languageType).length == 1)
		{
			if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
			{
				yml.set(key, ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", ""));
			} else
			{
				yml.set(key, keyMap.get(key).languageValues.get(languageType)[0]);
			}
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add(((String) o).replace("\r\n", ""));
					} else
					{
						stringList.add(o.toString().replace("\r\n", ""));
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	public void initConfig() //INFO:Config
	{
		configKeys.put("Bungee"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configKeys.put("IsMainServer"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.Status"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.Host"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"127.0.0.1"}));
		configKeys.put("Mysql.Port"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				3306}));
		configKeys.put("Mysql.DatabaseName"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"mydatabase"}));
		configKeys.put("Mysql.SSLEnabled"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.AutoReconnect"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configKeys.put("Mysql.VerifyServerCertificate"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.User"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"admin"}));
		configKeys.put("Mysql.Password"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"not_0123456789"}));
		configKeys.put("DebugMode"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("CryptSalt"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				SHA256Cryption.generateSalt()}));
		configKeys.put("SendNewPlayerAInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configKeys.put("WebURL"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"https://yourWebPortal.php"}));
		configKeys.put("JavaTaskCheck"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				20}));
		
		configKeys.put("Identifier.Click"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"click"}));
		configKeys.put("Identifier.Hover"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"hover"}));
		configKeys.put("Seperator.BetweenFunction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"~"}));
		configKeys.put("Seperator.WhithinFuction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"@"}));
		configKeys.put("Seperator.Space"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"+"}));
		configKeys.put("Seperator.HoverNewLine"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"~!~"}));
	}
	
	@SuppressWarnings("unused") //INFO:Commands
	public void initCommands()
	{
		comBypass();
		String path = "wpc";
		commandsInput("wpc", "wpc", "wpc.command.wpc", 
				"/wpc [pagenumber]", "/wpc ",
				"&c/wpc &f| Infoseite für alle Befehle.",
				"&c/wpc &f| Info page for all commands.");
		String basePermission = "wpc.command.htr";
		argumentInput("base_argument", "argument", basePermission,
				"/wpc howtoregister", "/wpc howtoregister ",
				"&c/wpc howtoregister &f| Infobefehl, wie man sich für das WebPortal regestrieren kann.",
				"&c/wpc howtoregister &f| Info command, how to register for the WebPortal.");
	}
	
	private void comBypass() //INFO:ComBypass
	{
		List<Bypass.Permission> list = new ArrayList<Bypass.Permission>(EnumSet.allOf(Bypass.Permission.class));
		for(Bypass.Permission ept : list)
		{
			commandsKeys.put("Bypass."+ept.toString().replace("_", ".")
					, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
					"mim."+ept.toString().toLowerCase().replace("_", ".")}));
		}
	}
	
	private void commandsInput(String path, String name, String basePermission, 
			String suggestion, String commandString,
			String helpInfoGerman, String helpInfoEnglish)
	{
		commandsKeys.put(path+".Name"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				name}));
		commandsKeys.put(path+".Permission"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				basePermission}));
		commandsKeys.put(path+".Suggestion"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".CommandString"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
	}
	
	private void argumentInput(String path, String argument, String basePermission, 
			String suggestion, String commandString,
			String helpInfoGerman, String helpInfoEnglish)
	{
		commandsKeys.put(path+".Argument"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				argument}));
		commandsKeys.put(path+".Permission"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				basePermission+"."+argument}));
		commandsKeys.put(path+".Suggestion"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".CommandString"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
	}
	
	public void initLanguage() //INFO:Languages
	{
		languageKeys.put("InputIsWrong",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDeine Eingabe ist fehlerhaft! Klicke hier auf den Text, um weitere Infos zu bekommen!",
						"&cYour input is incorrect! Click here on the text to get more information!"}));
		languageKeys.put("NoPermission", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast dafür keine Rechte!",
						"&cYou dont have the rights!"}));
		languageKeys.put("NoBoolean", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cEin Argument ist kein Boolean(true/false)!",
						"&cA argument is no boolean(true/false)!"}));
		languageKeys.put("NewPlayerInfoMessage",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e.:!|=== &fWebportal Info &e===|!:.",
						"&fOh? Du bist noch garnicht in unserem Webportal registriert.",
						"&fMit &cdiesem+Befehl~click@RUN_COMMAND@/web+howtoregister &ferfährst du alles zur Regestrierung.",
						"&fZum Webportal >> &6Klicke+hier~click@OPEN_URL@%url%",
						"&e.:!|==========================|!:.",
						
						"&e.:!|=== &fWebportal Info &e===|!:.",
						"&fOh? You are not registered in our web portal yet.",
						"&cThis+command~click@RUN_COMMAND@/web+howtoregister &fwill tell you everything about the registry.",
						"&fTo the web portal >> &6Click+here~click@OPEN_URL@%url%",
						"&e.:!|==========================|!:."}));
		languageKeys.put("CmdWpc.Headline", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e=====&7[&6BungeeTeleportManager&7]&e=====",
						"&e=====&7[&6BungeeTeleportManager&7]&e====="}));
		languageKeys.put("CmdWpc.Next", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e&nnächste Seite &e==>",
						"&e&nnext page &e==>"}));
		languageKeys.put("CmdWpc.Past", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e<== &nvorherige Seite",
						"&e<== &nprevious page"}));
		languageKeys.put("CmdWpc.Past", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e<== &nvorherige Seite",
						"&e<== &nprevious page"}));
		languageKeys.put("CmdWpc.HowToRegister.Array", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e.:!|=== &fWebportal HowToRegister &e===|!:.",
						"&fUm das Webportal benuzten zu können,",
						"&fmusst du Ingame dem Befehl &c%cmd%~click@RUN_COMMAND@%cmd% &fausführen.",
						"&fFalls du dein Passwort neu setzten willst oder nach langer Abwesenheit",
						"&fdein Zugang gelöscht wurde, kannst du dich mit dem gleichen Befehl",
						"&fneu regestrieren.",
						"&e.:!|==========================|!:.",
						"&e.:!|=== &fWebportal HowToRegister &e===|!:.",
						"&fTo use the web portal,",
						"&fyou must execute the &c%cmd%~click@RUN_COMMAND@%cmd% command in-game.",
						"&fIf you want to reset your password",
						"&for your account has been deleted after a long absence,",
						"&fyou can re-register with the same command.",
						"&e.:!|==========================|!:."}));		
		languageKeys.put("CmdWpc.Register.Insert", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast dich erfolgreich registriert! Mit &fdieser+URL~click@OPEN_URL@%url% &ekommst du zum WebPortal.",
						"&eYou have successfully registered! With &fthis+URL~click@OPEN_URL@%url% &eyou will get to the WebPortal."}));
		languageKeys.put("CmdWpc.Register.Update", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast erfolgreich dein Passwort geändert!",
						"&eYou have successfully changed your password!"}));
		/*languageKeys.put(""
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				"",
				""}))*/
	}
}
