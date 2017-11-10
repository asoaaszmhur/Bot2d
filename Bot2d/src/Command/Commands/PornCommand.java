package Command.Commands;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class PornCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Porn",  
		"Description: MmmmmMMmmm."
	};
	
	public PornCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			bot.messenger.sendMessageToDefault(":tired_face: Please add more parameters.");
		} else if(args.length == 1) {
			bot.messenger.sendMessageToDefault(":tired_face: Please add more parameters.");
		} else if(args.length == 2) {
			int amount;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":weary: Invalid Number!");
				return;
			}
			try {
				URL url = new URL(args[0]);
				try {
					url.openConnection();
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":tongue: Invalid URL B-a-b-y. :lips:");
					return;
				}
				bot.messenger.sendMessageToDefault("<:eggplant2:340256835623714817> :hourglass: Starting Porn Downloader... :wink:");
				startPorn(url, amount);
				bot.messenger.sendMessageToDefault(":sweat_drops: Finished.");
			} catch (Exception e) {
				System.out.println("Invalid pastbin");
			}
		} else if(args.length == 3) {
			int amount;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":weary: Invalid Number!");
				return;
			}
			try {
				URL url = new URL(args[0]);
				try {
					url.openConnection();
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":tongue: Invalid URL B-a-b-y. :lips:");
					return;
				}
				args[1] = args[1].replace("\\", "/");
				File file = new File(args[1]);
				if(!file.exists()) {
					file.mkdirs();
				}
				bot.messenger.sendMessageToDefault("<:eggplant2:340256835623714817> :hourglass: Starting Porn Downloader... :wink:");
				startPorn(url, args[1], amount);
				bot.messenger.sendMessageToDefault(":sweat_drops: Finished.");
			} catch (Exception e) {
				System.out.println("Invalid pastbin");
			}
		}
	}

	public void startPorn(URL pastebinUrl, int amount) {
		startPorn(pastebinUrl, "C:/Users/" + System.getProperty("user.name") + "/Desktop", amount);
	}
	
	public void startPorn(URL pastebinUrl, String path, int amount) {
		try {
			ArrayList<URL> pornImages = new ArrayList<URL>();
			BufferedReader in = new BufferedReader(new InputStreamReader(pastebinUrl.openStream()));
			String line;
			while((line = in.readLine()) != null) {
				pornImages.add(new URL(line));
			}
			BufferedImage image = null;
			int count = 0;
			while(count < amount) {
				for(URL url : pornImages) {
					try {
						image = ImageIO.read(url);
						File temp = new File(path + "/Porn-" + (int) (10000 * Math.random()) + ".png");
						ImageIO.write(image, "png", temp);
						count++;
					} catch (Exception e) { }
				}
			}
		} catch (Exception e) {
			System.out.println("Error while downloading your sexy porn ;) r a w r: " + e);
		}
	}
	
	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
