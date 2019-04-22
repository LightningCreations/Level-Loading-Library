package github.lightningcreations.game.level.data;

import java.util.Map;
import java.util.TreeMap;

import github.lightningcreations.game.ResourceName;


final class DataHelpers {
	private static final Map<ResourceName,Data.Reader> spriteDataReaders = new TreeMap<>();
	private static final Map<ResourceName,Data.Reader> triggerDataReaders = new TreeMap<>();
	
	
	private DataHelpers() {
		// TODO Auto-generated constructor stub
	}
	
	public static Data.Reader getSpriteReader(ResourceName id, Data.Reader orDefault){
		return spriteDataReaders.getOrDefault(id,orDefault);
	}
	public static Data.Reader getTriggerReader(ResourceName id, Data.Reader orDefault){
		return triggerDataReaders.getOrDefault(id,orDefault);
	}
}
