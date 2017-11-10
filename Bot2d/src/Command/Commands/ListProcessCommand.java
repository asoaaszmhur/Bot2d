package Command.Commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class ListProcessCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--ListProcesses (search)",  
		"Description: Shows all currently running processes.",
		"Parameters: search (name)"
	};
	
	public ListProcessCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			bot.messenger.sendMessageToDefault(":hourglass: Getting Processes...");
			getProcesses();
			bot.messenger.sendMessageToDefault(":white_check_mark: Done.");
		} else if(args.length == 1) {
			getProcess(args[0]);
		} else if(args.length > 1) {
			bot.messenger.sendMessageToDefault("???");
		}
	}
	
	public void getProcesses() {
		try {
			String concat = "Process List: " + System.getProperty("line.separator");
			Process p = Runtime.getRuntime().exec("TASKLIST");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if(concat.length() > 1900) {
					bot.messenger.sendMessageToDefault(concat);
					concat = "";
				}
				concat += line + System.getProperty("line.separator");
			}
			bot.messenger.sendMessageToDefault(concat);
		} catch (Exception e) {
			System.out.println("Error in Command:Commands:ListProcessCommand:getProcesses(): " + e);
			bot.messenger.sendMessageToDefault("Error while getting processes. Sorrweh :fearful:");
		}
	}
	
	public void getProcess(String processName) {
		try {
			Process p = Runtime.getRuntime().exec("TASKLIST");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				if(line.contains(processName)) {
					count++;
				}
			}
			if(count > 0) {
				bot.messenger.sendMessageToDefault(":white_check_mark: Found " + count + " occurences of \"" + processName + "\".");
			} else {
				bot.messenger.sendMessageToDefault(":no_entry_sign: No process with the name \"" + processName + "\" found.");
			}
		} catch (Exception e) {
			System.out.println("Error in Command:Commands:ListProcessCommand:getProcess(): " + e);
			bot.messenger.sendMessageToDefault("Error while searching. Sorrweh :fearful:");
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
