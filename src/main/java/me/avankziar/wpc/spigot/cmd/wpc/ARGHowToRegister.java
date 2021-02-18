package main.java.me.avankziar.wpc.spigot.cmd.wpc;

import java.util.List;

import org.bukkit.command.CommandSender;

import main.java.me.avankziar.wpc.general.ChatApi;
import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.wpc.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.wpc.spigot.objects.PluginSettings;
import main.java.me.avankziar.wpc.spigot.permission.KeyHandler;

public class ARGHowToRegister extends ArgumentModule
{
	private WebPortalConnector plugin;
	
	public ARGHowToRegister(WebPortalConnector plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}
	
	@Override
	public void run(CommandSender sender, String[] args)
	{
		List<String> msgs = plugin.getYamlHandler().getLang().getStringList("CmdWpc.HowToRegister.Array");
		for(String s : msgs)
		{
			sender.spigot().sendMessage(ChatApi.generateTextComponent(
					s.replace("%cmd%", PluginSettings.settings.getCommands(KeyHandler.WPC_REGISTER).replace(" ", "+"))));
		}
	}
}