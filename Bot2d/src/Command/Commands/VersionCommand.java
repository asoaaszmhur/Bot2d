package Command.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class VersionCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Version",  
		"Description: Shows client version."
	};
	
	public VersionCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		bot.messenger.sendMessageToDefault("Current Version: " + bot.getVersion());
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
