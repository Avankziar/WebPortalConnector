package main.java.me.avankziar.wpc.spigot.cmd.wpc;

import java.security.NoSuchAlgorithmException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.wpc.general.ChatApi;
import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.assistance.MatchApi;
import main.java.me.avankziar.wpc.spigot.assistance.SHA256Cryption;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.wpc.spigot.objects.PluginSettings;
import main.java.me.avankziar.wpc.spigot.objects.PluginUser;
import main.java.me.avankziar.wpc.spigot.permission.BypassPermission;

public class ARGRegister extends ArgumentModule
{
	private WebPortalConnector plugin;
	
	public ARGRegister(WebPortalConnector plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}
	
	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();
		String playername = player.getName();
		String password = args[1];
		String cryptPassword = "";
		try
		{
			cryptPassword = SHA256Cryption.encryptSHA256(password+PluginSettings.settings.getCryptSalt());
		} catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		boolean isadmin = false;
		if(args.length >= 3)
		{
			if(!player.hasPermission(BypassPermission.REGISTERADMIN))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoPermission")));
				return;
			}
			if(!MatchApi.isBoolean(args[2]))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoBoolean")));
				return;
			}
			isadmin = Boolean.parseBoolean(args[2]);
		}
		PluginUser user = new PluginUser(uuid, playername, cryptPassword, isadmin);
		if(plugin.getMysqlHandler().exist(Type.PLUGINUSER, "`player_uuid` = ?", uuid))
		{
			plugin.getMysqlHandler().updateData(Type.PLUGINUSER, user, "`player_uuid` = ?", uuid);
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdWpc.Register.Update")));
		} else
		{
			plugin.getMysqlHandler().create(Type.PLUGINUSER, user);
			player.spigot().sendMessage(ChatApi.generateTextComponent(plugin.getYamlHandler().getLang().getString("CmdWpc.Register.Insert")));
		}
	}
}