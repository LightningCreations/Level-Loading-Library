package github.lightningcreations.game.level.format;

import github.lightningcreations.lclib.Version;

public interface Constants {
	public int MAGIC = 0xEEABDC0B;
	public Version CURRENT = new Version(1,0);
	
	public String RESOURCE_NAME_REGEX = "[A-Za-z_-][\\w_-]*:[A-Za-z_-][\\w_-]*(\\/[A-Za-z_-][\\w_-]*)*",
			IMAGE_PATH_REGEX = "[^/ ]*(/[^/ ]*)*/?";
}
