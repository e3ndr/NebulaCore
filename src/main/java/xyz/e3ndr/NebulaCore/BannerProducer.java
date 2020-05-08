package xyz.e3ndr.NebulaCore;

import java.util.Random;

import org.bukkit.ChatColor;

public class BannerProducer {
	private static final Random rand = new Random();
	private static final String bannerAscii = "" + 
			"\n\n&d                                                             \n" + 
			"      _   _             _                   _                \n" + 
			"     |#\\ |#|           |#|                 |#|               \n" + 
			"     |##\\|#|    ___    |#|__      _   _    |#|     __ _      \n" + 
			"     |#.#`#|   / _ \\   |##'_#\\   |#| |#|   |#|    /#_`#|     \n" + 
			"     |#|\\##|  |##__/   |##|_)#|  |#|_|#|   |#|   |#(_|#|     \n" + 
			"     \\_| \\_/   \\___|   |#_.__/    \\__,_|   |_|    \\__,_|     \n" + 
			"                                                             \n" + 
			"                                                             \n";
	

	public static String banner() {
		char[] banner = bannerAscii.toCharArray();
		int[] stars = new int[4 + rand.nextInt(16)];
		
		for (int i = 0; i != stars.length; i++) {
			stars[i] = rand.nextInt(stars.length - 1) + 1;
		}
		
		for (int star : stars) banner = placeStar(banner, star);
		String starred = new String(banner);
		
		return ChatColor.translateAlternateColorCodes('&', starred.replace("#", " ").replace("****", "  * ").replace("**", "  ").replace("* *", "   ").replace("*", "&f*&d"));
	}
	
	private static char[] placeStar(char[] banner, int loc) {
		char c = banner[loc];
		
		if (c == ' ') {
			banner[loc] = '*';
			return banner;
		} else {
			return placeStar(banner, rand.nextInt(banner.length - 1));
		}
	}
}
