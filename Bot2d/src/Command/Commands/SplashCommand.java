package Command.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class SplashCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Splash",  
		"Description: Create a Splash Image."
	};
	
	public SplashCommand(String cmd, Bot2d bot) {
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
