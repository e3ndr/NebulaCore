package xyz.e3ndr.NebulaCore.Module.Warps;

public abstract class AbstractWarpStorage {
	public static AbstractWarpStorage instance;
	
	public AbstractWarpStorage() {
		instance = this;
	}
	
	public abstract void init();
	
	public abstract void addWarp(Warp warp);
	
	public abstract void delWarp(Warp warp);
	
}
