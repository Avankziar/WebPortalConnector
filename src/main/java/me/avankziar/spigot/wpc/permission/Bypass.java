package main.java.me.avankziar.spigot.wpc.permission;

import java.util.LinkedHashMap;

public class Bypass
{
	public enum Permission
	{
		REGISTER_ADMIN
	}
	private static LinkedHashMap<Bypass.Permission, String> mapPerm = new LinkedHashMap<>();
	
	public static void set(Bypass.Permission bypass, String perm)
	{
		mapPerm.put(bypass, perm);
	}
	
	public static String get(Bypass.Permission bypass)
	{
		return mapPerm.get(bypass);
	}
	
	public enum CountPermission
	{
		BASE
	}
	private static LinkedHashMap<Bypass.CountPermission, String> mapCount = new LinkedHashMap<>();
	
	public static void set(Bypass.CountPermission bypass, String perm)
	{
		mapCount.put(bypass, perm);
	}
	
	public static String get(Bypass.CountPermission bypass)
	{
		return mapCount.get(bypass);
	}
}