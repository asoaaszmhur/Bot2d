package Command.Commands;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class JitterCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Jitter",  
		"Description: Jitters the Cursor in Random Directions."
	};
	
	boolean done = true;
	int moveSpeed = 0;
	
	public JitterCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			if(done) {
				bot.messenger.sendMessageToDefault(":white_check_mark: Jitter started. MoveSpeed: " + moveSpeed);
				start();
			}
		} else if(args.length == 1) {
			switch(args[0]) {
			case "off":
				if(!done) {
					done = true;
					bot.messenger.sendMessageToDefault(":white_check_mark: Jitter off.");
				} else {
					bot.messenger.sendMessageToDefault(":thinking: The bot is already off.");
				}
				break;
			case "on":
				if(done) {
					bot.messenger.sendMessageToDefault(":white_check_mark: Jitter started. MoveSpeed: " + moveSpeed);
					start();
				} else {
					bot.messenger.sendMessageToDefault(":thinking: The bot is already on.");
				}
				break;
			case "setspeed":
				bot.messenger.sendMessageToDefault(":no_entry_sign: More parameters needed.");
				break;
			default:
				break;
			}
		} else if(args.length == 2) {
			switch(args[0]) {
			case "setspeed":
				int amount = 0;
				try {
					amount = Integer.parseInt(args[1]);
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Number.");
					return;
				}
				if(amount < 0) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Only positive numbers allowed.");
					return;
				}
				moveSpeed = amount;
				bot.messenger.sendMessageToDefault(":white_check_mark: MoveSpeed set to " + amount + ".");
				break;
			default:
				break;
			}
		}
	}
	
	public void start() {
		try {
			done = false;
			Robot robot = new Robot();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			if(moveSpeed == 0) {
				moveSpeed = 3;
			}
			while(!done) {
				int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
				int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
				mouseX += (int) ((Math.random() - 0.5) * moveSpeed);
				mouseY += (int) ((Math.random() - 0.5) * moveSpeed);
				if(mouseX < 0) {
					mouseX = 0;
				}
				if(mouseY < 0) {
					mouseY = 0;
				}
				if(mouseX > dim.width) {
					mouseX = dim.width;
				}
				if(mouseY > dim.height) {
					mouseY = dim.height;
				}
				robot.mouseMove(mouseX, mouseY);
				Thread.sleep(25);
			}
		} catch (Exception e) {
			System.out.println("Unable to Jitter Cursor");
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
