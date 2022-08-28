package main.java.me.avankziar.wpc.spigot.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.wpc.general.ChatApi;
import main.java.me.avankziar.wpc.general.ConvertHandler;
import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.wpc.spigot.handler.ConfigHandler;
import main.java.me.avankziar.wpc.spigot.objects.WebPortalUser;

public class JoinListener implements Listener
{
	private WebPortalConnector plugin;
	
	public JoinListener(WebPortalConnector plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		final String playername = player.getName();
		new BukkitRunnable()
		{
			
			@Override
			public void run()
			{
				if(!player.isOnline())
				{
					cancel();
					return;
				}
				int count = plugin.getMysqlHandler().getCount(Type.PLUGINUSER, "`player_name` = ?", playername);
				WebPortalUser user = (WebPortalUser) plugin.getMysqlHandler().getData(Type.PLUGINUSER,
						"`player_uuid` = ?", player.getUniqueId().toString());
				if(user == null && count == 0)
				{
					sendNewPlayerInfoMessage(player);
				} else if(user == null && count > 0)
				{
					ArrayList<WebPortalUser> users = ConvertHandler.convertListI(
							plugin.getMysqlHandler().getFullList(Type.PLUGINUSER, "`id` ASC", "`player_name` = ?", playername));
					for(WebPortalUser u : users)
					{
						plugin.getMysqlHandler().deleteData(Type.PLUGINUSER, "`player_uuid` = ?", u.getUUID());
					}
					sendNewPlayerInfoMessage(player);
				} else if(user != null && count == 0)
				{
					updateName(player, playername);
				} else if(user != null && count == 1)
				{
					updateName(player, playername);
				}
			}
		}.runTaskLaterAsynchronously(plugin, 40L);
	}
	
	private void sendNewPlayerInfoMessage(Player player)
	{
		if(!new ConfigHandler().isSendNewPlayerAInfo())
		{
			return;
		}
		List<String> listmsg = plugin.getYamlHandler().getLang().getStringList("NewPlayerInfoMessage");
		for(String s : listmsg)
		{
			player.spigot().sendMessage(ChatApi.generateTextComponent(s.replace("%url%", new ConfigHandler().getWebURL())));
		}
	}
	
	private void updateName(Player player, String playername)
	{
		WebPortalUser user = (WebPortalUser) plugin.getMysqlHandler().getData(Type.PLUGINUSER, "`player_uuid` = ?", player.getUniqueId().toString());
		if(user == null)
		{
			return;
		}
		if(user.getName().equals(playername))
		{
			return;
		}
		user.setName(playername);
		plugin.getMysqlHandler().updateData(Type.PLUGINUSER, user, "`player_uuid` = ?", user.getUUID());
	}
}
