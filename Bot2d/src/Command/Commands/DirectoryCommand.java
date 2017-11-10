package Command.Commands;

import java.io.File;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class DirectoryCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Version",  
		"Description: Shows client version."
	};
	
	public DirectoryCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			// g
		} else if(args.length == 1) {
			String path = "";
			for(String str : args) {
				path += str + " ";
			}
			File file = new File(path);
			if(file.exists()) {
				bot.messenger.sendMessageToDefault(":hourglass: Listing...");
				listDirectory(path);
				bot.messenger.sendMessageToDefault(":white_check_mark: Finished.");
			} else {
				bot.messenger.sendMessageToDefault(":no_entry_sign: That directory doesn't exist.");
			}
		}
	}
	
	public void listDirectory(String path) {
		try {
			String textToSend = ":newspaper: Files: " + System.getProperty("line.separator");
			File dir = new File(path);
			File[] files = dir.listFiles();
			for(File file : files) {
				if(file.getName().lastIndexOf('.') != -1) {
					if(textToSend.length() > 1900) {
						bot.messenger.sendMessageToDefault(textToSend);
						textToSend = "";
					} else {
						textToSend += System.getProperty("line.separator") + file.getName();
					}
				}
			}
			textToSend += System.getProperty("line.separator") + System.getProperty("line.separator") + ":file_folder: Folders: " + System.getProperty("line.separator");
			for(File file : files) {
				if(!(file.getName().lastIndexOf('.') != -1)) {
					if(textToSend.length() > 1900) {
						bot.messenger.sendMessageToDefault(textToSend);
						textToSend = "";
					} else {
						textToSend += System.getProperty("line.separator") + file.getName();
					}
				}
			}
			bot.messenger.sendMessageToDefault(textToSend);
		} catch (Exception e) {
			bot.messenger.sendMessageToDefault(":no_entry_sign: Error while trying to list directory.");
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
