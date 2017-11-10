package Command.Commands;

import java.awt.Toolkit;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class BeepCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Beep",  
		"Description: Plays a Beep."
	};
	
	public BeepCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			Toolkit.getDefaultToolkit().beep();
			bot.messenger.sendMessageToDefault(":robot: Beep!");
		} else if(args.length == 1) {
			int amount = 0;
			try {
				amount = Integer.parseInt(args[0]);
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":robot: Invalid Number! :[");
				return;
			}
			bot.messenger.sendMessageToDefault(":robot: Starting Beeps...");
			int count = 0;
			while(count < amount) {
				Toolkit.getDefaultToolkit().beep();
				try {
				Thread.sleep(100);
				} catch (Exception e) { }
				count++;
			}
			bot.messenger.sendMessageToDefault(":white_check_mark: Finished.");
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
