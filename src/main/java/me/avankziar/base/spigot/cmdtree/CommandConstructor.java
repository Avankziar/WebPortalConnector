package main.java.me.avankziar.base.spigot.cmdtree;

import java.util.ArrayList;

import main.java.me.avankziar.base.spigot.BaseMain;

public class CommandConstructor extends BaseConstructor
{
    public ArrayList<ArgumentConstructor> subcommands;
    public ArrayList<String> tablist;

	public CommandConstructor(String path, boolean canConsoleAccess,
    		ArgumentConstructor...argumentConstructors)
    {
		super(BaseMain.getPlugin().getYamlHandler().getCommands().getString(path+".Name"),
				path,
				BaseMain.getPlugin().getYamlHandler().getCommands().getString(path+".Permission"),
				BaseMain.getPlugin().getYamlHandler().getCommands().getString(path+".Suggestion"),
				BaseMain.getPlugin().getYamlHandler().getCommands().getString(path+".CommandString"),
				BaseMain.getPlugin().getYamlHandler().getCommands().getString(path+".HelpInfo"),
				canConsoleAccess);
        this.subcommands = new ArrayList<>();
        this.tablist = new ArrayList<>();
        for(ArgumentConstructor ac : argumentConstructors)
        {
        	this.subcommands.add(ac);
        	this.tablist.add(ac.getName());
        }
        BaseMain.getPlugin().getCommandTree().add(this);
    }
}