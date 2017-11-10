package Command.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class CmdsCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Commands",  
		"Description: Shows all commands that are available."
	};
	
	public CmdsCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		String concat = "Commands: ";
		for(Command cmd : bot.getCommands()) {
			concat += cmd.getCmd() + ", ";
		}
		concat = concat.substring(0, concat.length() - 2);
		bot.messenger.sendMessageToDefault(concat);
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
