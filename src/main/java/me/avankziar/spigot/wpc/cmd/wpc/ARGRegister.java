package main.java.me.avankziar.spigot.wpc.cmd.wpc;

import java.security.NoSuchAlgorithmException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.general.wpc.ChatApi;
import main.java.me.avankziar.spigot.wpc.WebPortalConnector;
import main.java.me.avankziar.spigot.wpc.assistance.MatchApi;
import main.java.me.avankziar.spigot.wpc.assistance.SHA256Cryption;
import main.java.me.avankziar.spigot.wpc.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.spigot.wpc.cmdtree.ArgumentModule;
import main.java.me.avankziar.spigot.wpc.database.MysqlHandler.Type;
import main.java.me.avankziar.spigot.wpc.handler.ConfigHandler;
import main.java.me.avankziar.spigot.wpc.objects.WebPortalUser;
import main.java.me.avankziar.spigot.wpc.permission.Bypass;

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
			cryptPassword = SHA256Cryption.encryptSHA256(password+new ConfigHandler().getCryptSalt());
		} catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		boolean isadmin = false;
		if(args.length >= 3)
		{
			if(!player.hasPermission(Bypass.get(Bypass.Permission.REGISTER_ADMIN)))
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
		WebPortalUser user = new WebPortalUser(uuid, playername, cryptPassword, isadmin);
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