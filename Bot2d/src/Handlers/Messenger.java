package Handlers;

import java.io.File;

import Main.Bot2d;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;

public class Messenger {

	Bot2d bot;
	JDA client;
	TextChannel defaultChannel;
	
	public Messenger(Bot2d bot) {
		this.bot = bot;
		this.client = bot.client;
		
		if(bot.hasDefaultChannel()) {
			setDefaultChannel(bot.getDefaultChannel());
		}
	}
	
	public void sendMessageToDefault(String message) {
		try {
			defaultChannel.sendMessage(message).queue();
		} catch (Exception e) {
			System.out.println("Error in Handlers:Messenger:sendMessageToDefault(): " + e);
		}
	}
	
	public void sendFileToDefault(File file) {
		try {
			defaultChannel.sendFile(file, null).queue();
		} catch (Exception e) {
			System.out.println("Error in Handlers:Messenger:sendFileToDefault(): " + e);
		}
	}
	
	public void sendMessageToID(String id, String message) {
		try {
			client.getTextChannelById(id).sendMessage(message);
		} catch (Exception e) {
			System.out.println("Error in Handlers:Messenger:sendMessageToID(): " + e);
			System.out.println("Trying Default Channel.");
			sendMessageToDefault(message);
		}
	}
	
	// Gets and Sets
	
	public void setDefaultChannel(String id) {
		try {
			defaultChannel = client.getTextChannelById(id);
		} catch (Exception e) {
			System.out.println("Error in Handlers:Messenger:setDefaultChannel(): " + e);
		}
	}
}
