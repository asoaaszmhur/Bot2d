package Command.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class UpdateCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Version",  
		"Description: Shows client version."
	};
	
	public UpdateCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			bot.messenger.sendMessageToDefault(":hourglass: Checking for Updates!");
			bot.updater.fetchUpdates();
			if(bot.updater.updatesAvailable()) {
				bot.messenger.sendMessageToDefault(":white_check_mark: Updates Found. Beginning update...");
				bot.updateMode();
			} else {
				bot.messenger.sendMessageToDefault(":no_entry_sign: No updates found.");
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
