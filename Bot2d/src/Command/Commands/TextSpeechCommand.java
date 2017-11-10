package Command.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class TextSpeechCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--TextSpeech",  
		"Description: Text 2 Speech."
	};
	
	public TextSpeechCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		String concat = "";
		for(String str : args) {
			concat += str + " ";
		}
		bot.messenger.sendMessageToDefault(":hourglass: Sending...");
		try {
			File file = new File("C:/Users/" + System.getProperty("user.name") + "/tts.vbs");
			BufferedWriter ou = new BufferedWriter(new FileWriter(file));
			PrintWriter pr = new PrintWriter(file);
			pr.write("");
			pr.close();
			ou.write("set objspeech=createobject(\"SAPI.spVoice\")");
			ou.newLine();
			ou.write("objspeech.speak \"" + concat + "\"");
			ou.close();
			String[] cmds = new String[]{"C:/Windows/System32/wscript.exe", "C:/Users/" + System.getProperty("user.name") + "/tts.vbs"};
			Runtime.getRuntime().exec(cmds);
			bot.messenger.sendMessageToDefault(":white_check_mark: Sent.");
		} catch (Exception e) {
			bot.messenger.sendMessageToDefault("Failed to Send Text to Speech");
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
