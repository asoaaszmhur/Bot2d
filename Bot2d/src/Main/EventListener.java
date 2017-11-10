package Main;

import Command.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
	
	Bot2d bot;
	
	public EventListener(Bot2d bot) {
		this.bot = bot;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String recieved = event.getMessage().getContent();
		if(recieved.equals(bot.getCMDKey() + "ping")) {
			bot.messenger.sendMessageToDefault(":wave::skin-tone-1: Ping from: " + bot.getIP() + " (" + bot.nickname.getNickname() + ")");
		}
		if(recieved.contains(bot.getCMDKey())) {
			String[] split = recieved.split(bot.getCMDKey());
			if(split[1] == null) {
				return;
			}
			if(split[0].equals(bot.getIP()) || split[0].equals(bot.nickname.getNickname())) {
				String content = split[1];
				for(Command cmd : bot.commands) {
					if(content.startsWith(cmd.getCmd())) {
						if(split[1].contains(" ")) {
							String beheaded = split[1].substring(content.indexOf(" ") + 1, content.length());
							String[] args = beheaded.split(" ");
							bot.executor.executeCommand(cmd, args, event);
						} else {
							bot.executor.executeCommand(cmd, null, event);
						}
					}
				}
			}
		}
	}
}
