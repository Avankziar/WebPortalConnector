package main.java.me.avankziar.spigot.wpc.cmd.wpc;

import java.util.List;

import org.bukkit.command.CommandSender;

import main.java.me.avankziar.general.wpc.ChatApi;
import main.java.me.avankziar.spigot.wpc.WebPortalConnector;
import main.java.me.avankziar.spigot.wpc.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.spigot.wpc.cmdtree.ArgumentModule;
import main.java.me.avankziar.spigot.wpc.cmdtree.CommandExecuteType;
import main.java.me.avankziar.spigot.wpc.cmdtree.CommandSuggest;

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
					s.replace("%cmd%", CommandSuggest.get(CommandExecuteType.WPC_REGISTER).replace(" ", "+"))));
		}
	}
}