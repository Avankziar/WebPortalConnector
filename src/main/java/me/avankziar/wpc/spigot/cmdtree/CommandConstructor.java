package main.java.me.avankziar.wpc.spigot.cmdtree;

import java.util.ArrayList;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;

public class CommandConstructor extends BaseConstructor
{
    public ArrayList<ArgumentConstructor> subcommands;
    public ArrayList<String> tablist;

	public CommandConstructor(String path, boolean canConsoleAccess,
    		ArgumentConstructor...argumentConstructors)
    {
		super(WebPortalConnector.getPlugin().getYamlHandler().getCommands().getString(path+".Name"),
				path,
				WebPortalConnector.getPlugin().getYamlHandler().getCommands().getString(path+".Permission"),
				WebPortalConnector.getPlugin().getYamlHandler().getCommands().getString(path+".Suggestion"),
				WebPortalConnector.getPlugin().getYamlHandler().getCommands().getString(path+".CommandString"),
				WebPortalConnector.getPlugin().getYamlHandler().getCommands().getString(path+".HelpInfo"),
				canConsoleAccess);
        this.subcommands = new ArrayList<>();
        this.tablist = new ArrayList<>();
        for(ArgumentConstructor ac : argumentConstructors)
        {
        	this.subcommands.add(ac);
        	this.tablist.add(ac.getName());
        }
        WebPortalConnector.getPlugin().getCommandTree().add(this);
    }
}