package Command.Commands;

import java.awt.Desktop;
import java.net.URL;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class OpenLinkCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Openlink",  
		"Description: Opens a URL."
	};
	
	public OpenLinkCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			
		} else if(args.length == 1) {
			try {
				URL url = new URL(args[0]);
				url.openConnection();
				Desktop d = Desktop.getDesktop();
				d.browse(url.toURI());
				bot.messenger.sendMessageToDefault(":white_check_mark: Opened.");
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":mag: Invalid Link!");
			}
		} else if(args.length == 2) {
			int amount = 0;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":mag: Invalid Number!");
			}
			bot.messenger.sendMessageToDefault(":hourglass: Opening " + amount + " Links.");
			try {
				URL url = new URL(args[0]);
				url.openConnection();
				Desktop d = Desktop.getDesktop();
				int count = 0;
				while(count < amount) {
					d.browse(url.toURI());
					count++;
				}
				bot.messenger.sendMessageToDefault(":white_check_mark: Finished.");
			} catch (Exception e) {
				bot.messenger.sendMessageToDefault(":mag: Invalid Link!");
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
