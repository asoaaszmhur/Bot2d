package Command.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class HelpCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "MyBot--help (command)",
			"MyBot--commands"
	};
	
	public HelpCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			for(String str : help) {
				bot.messenger.sendMessageToDefault(str);
			}
		} else if(args.length == 1) {
			boolean found = false;
			for(Command cmd : bot.getCommands()) {
				if(cmd.getCmd().equals(args[0])) {
					for(String str : cmd.help()) {
						found = true;
						bot.messenger.sendMessageToDefault(str);
					}
				}
			}
			if(!found) {
				bot.messenger.sendMessageToDefault(":no_entry_sign: Couldn't find the Command: " + args[0]);
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
