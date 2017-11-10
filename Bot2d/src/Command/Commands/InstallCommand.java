package Command.Commands;

import java.io.File;
import java.net.URL;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;
import Main.Installer;

public class InstallCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Version",  
		"Description: Shows client version."
	};
	
	public InstallCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			bot.messenger.sendMessageToDefault(":no_entry_sign: Not enough Parameters.");
		} else if(args.length == 1) {
			bot.messenger.sendMessageToDefault(":no_entry_sign: Not enough Parameters.");
		} else if(args.length == 2) {
			URL url;
			File file;
			try {
				url = new URL(args[0]);
				url.openConnection();
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid URL.");
				return;
			}
			try {
				file = new File(args[1]);
				if(!file.exists()) {
					file.getParentFile().mkdirs();
				}
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Directory.");
				return;
			}
			bot.messenger.sendMessageToDefault(":hourglass: Installing...");
			Installer install = new Installer(url.toString(), file.getAbsolutePath());
			while(install.isInstalling()) {
				try {
				Thread.sleep(500);
				} catch(Exception e) { }
			}
			if(install.failed()) {
				bot.messenger.sendMessageToDefault(":no_entry_sign: Error failed to install file.");
			} else {
				bot.messenger.sendMessageToDefault(":white_check_mark: Installed.");
			}
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
