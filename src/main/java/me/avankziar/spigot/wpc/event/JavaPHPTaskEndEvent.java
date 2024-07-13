package main.java.me.avankziar.spigot.wpc.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JavaPHPTaskEndEvent extends Event
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean isCancelled;
	private int mySQLID;
	private boolean wasSuccessful;
	private String errorMessage;

	public JavaPHPTaskEndEvent(int mySQLID, boolean wasSuccessful, String errorMessage)
	{
		setCancelled(false);
		setMySQLID(mySQLID);
		setWasSuccessful(wasSuccessful);
		setErrorMessage(errorMessage);
	}
	
	public HandlerList getHandlers() 
    {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() 
    {
        return HANDLERS;
    }

	public boolean isCancelled()
	{
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled)
	{
		this.isCancelled = isCancelled;
	}

	public int getMySQLID()
	{
		return mySQLID;
	}

	public void setMySQLID(int mySQLID)
	{
		this.mySQLID = mySQLID;
	}

	public boolean isWasSuccessful()
	{
		return wasSuccessful;
	}

	public void setWasSuccessful(boolean wasSuccessful)
	{
		this.wasSuccessful = wasSuccessful;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
