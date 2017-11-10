package Command.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class NicknameCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--nickname (set/get) (name)",
			"Description: Sets the nickname for this client.",
			"Parameters: set (name), get"
	};
	
	public NicknameCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			bot.messenger.sendMessageToDefault("Current Nickname: " + bot.nickname.getNickname());
		} else if(args.length == 1) {
			switch(args[0]) {
			case "set":
				bot.messenger.sendMessageToDefault("Invaild Parameters. Try the Help Command");
				break;
			case "get":
				bot.messenger.sendMessageToDefault("Current Nickname: " + bot.nickname.getNickname());
				break;
			default:
				bot.messenger.sendMessageToDefault("??? : " + args[0]);
				break;
			}
		} else if(args.length == 2) {
			switch(args[0]) {
			case "set":
				bot.nickname.setNickname(args[1]);
				bot.messenger.sendMessageToDefault("Nickname set to: " + args[1]);
				break;
			case "get":
				bot.messenger.sendMessageToDefault("Current Nickname: " + bot.nickname.getNickname());
				break;
			default:
				bot.messenger.sendMessageToDefault("???");
				break;
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
