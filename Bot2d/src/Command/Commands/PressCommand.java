package Command.Commands;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class PressCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Press",  
		"Description: Presses keys.."
	};
	
	HashMap<String, Integer> keyDictionary = new HashMap<String, Integer>();
	
	public PressCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
		
		keyDictionary.put("[ENTER]", KeyEvent.VK_ENTER);
		keyDictionary.put("[CAPSLOCK]", KeyEvent.VK_CAPS_LOCK);
		keyDictionary.put("[F1]", KeyEvent.VK_F1);
		keyDictionary.put("[F2]", KeyEvent.VK_F2);
		keyDictionary.put("[F3]", KeyEvent.VK_F3);
		keyDictionary.put("[F4]", KeyEvent.VK_F4);
		keyDictionary.put("[F5]", KeyEvent.VK_F5);
		keyDictionary.put("[F6]", KeyEvent.VK_F6);
		keyDictionary.put("[F7]", KeyEvent.VK_F7);
		keyDictionary.put("[F8]", KeyEvent.VK_F8);
		keyDictionary.put("[F9]", KeyEvent.VK_F9);
		keyDictionary.put("[F10]", KeyEvent.VK_F10);
		keyDictionary.put("[F11]", KeyEvent.VK_F11);
		keyDictionary.put("[F12]", KeyEvent.VK_F12);
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			// see help.
		} else if(args.length > 0) {
			String concat = "";
			for(String str : args) {
				concat += str + " ";
			}
			int interval = -1;
			if(concat.lastIndexOf("\"") != concat.length() - 2) {
				String number = concat.substring(concat.lastIndexOf("\"") + 2, concat.length() - 1);
				try {
					interval = Integer.parseInt(number);
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Number.");
					return;
				}
				if(interval < 0) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Number cannot be Negative.");
					return;
				}
			}
			concat = concat.substring(1, concat.lastIndexOf("\"") + 1);
			bot.messenger.sendMessageToDefault(":hourglass: Pressing Keys...");
			if(interval == -1) {
				pressKeys(concat);
			} else {
				pressKeys(concat, interval);
			}
			bot.messenger.sendMessageToDefault(":white_check_mark: Finished.");
		}
	}
	
	public void pressKeys(String keys) {
		pressKeys(keys, 5);
	}
	
	public void pressKeys(String keys, int interval) {
		try {
			Robot robot = new Robot();
			char[] characters = keys.toCharArray();
			for(int i = 0; i < characters.length - 1; i++) {
				try {
					if(characters[i] == '[') {
						String brackets = "";
						int index = keys.indexOf(']', i);
						brackets = keys.substring(i, index + 1);
						int key = keyDictionary.get(brackets);
						i = index + 1;
						robot.keyPress(key);
						robot.keyRelease(key);
					} else {
						int keycode;
						if(characters[i] == '"') {
							keycode = KeyEvent.VK_QUOTE;
						} else {
							keycode = KeyEvent.getExtendedKeyCodeForChar(characters[i]);
						}
						robot.keyPress(keycode);
						robot.keyRelease(keycode);
					}
				} catch (Exception e) {
					
				}
				Thread.sleep(interval);
			}
		} catch (Exception e) {
			System.out.println("Error while pressing dese keys");
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
