package Command.Commands;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class BouncyCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Bouncy",  
		"Description: Creates Bouncy Images."
	};
	
	List<Bouncy> bouncies = new ArrayList<Bouncy>();
	boolean done = true;
	
	float gravity = -1.5f;
	float friction = -1.5f;
	
	int delay = 5;
	
	public BouncyCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			// See the help.
		} else if(args.length == 1) {
			switch(args[0]) {
			case "on":
				if(done) {
					start();
				} else {
					// already started.
				}
				break;
			case "off":
				if(!done) {
					done = true;
				} else {
					// already done.
				}
				break;
			case "clear":
				bot.messenger.sendMessageToDefault(":hourglass: Clearing Bouncies...");
				for(Bouncy bouncy : bouncies) {
					bouncy.destroy();
				}
				bouncies.clear();
				bot.messenger.sendMessageToDefault(":white_check_mark: Cleared.");
				break;
			case "reset":
				bot.messenger.sendMessageToDefault(":hourglass: Resetting...");
				for(Bouncy bouncy : bouncies) {
					bouncy.reset();
				}
				bot.messenger.sendMessageToDefault(":white_check_mark: Bouncies reset!");
				break;
			case "random":
				bot.messenger.sendMessageToDefault(":hourglass: Randomizing...");
				for(Bouncy bouncy : bouncies) {
					bouncy.velocityX = (int) ((Math.random() - 0.5) * 100);
					bouncy.velocityY = (int) ((Math.random() - 0.5) * 100);
				}
				bot.messenger.sendMessageToDefault(":white_check_mark: Randomized.");
				break;
			}
		} else if(args.length == 2) {
			switch(args[0]) {
			case "add":
				URL url = null;
				try {
					url = new URL(args[1]);
					url.openConnection();
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid URL.");
					return;
				}
				bot.messenger.sendMessageToDefault(":hourglass: Adding new Bouncy...");
				if(addBouncy(url)) {
					bot.messenger.sendMessageToDefault(":white_check_mark: Added!");
				}
				break;
			case "setdelay":
				int amount = 0;
				try {
					amount = Integer.parseInt(args[1]);
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Number.");
					return;
				}
				if(amount < 0) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Delay cannot be Negative.");
					return;
				}
				delay = amount;
				bot.messenger.sendMessageToDefault(":white_check_mark: Delay set to " + amount + ".");
				break;
			case "setgravity":
				float amount3 = 0;
				try {
					amount3 = (float) Double.parseDouble(args[1]);
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Number.");
					return;
				}
				gravity = amount3;
				bot.messenger.sendMessageToDefault(":white_check_mark: Gravity set to " + amount3 + ".");
				break;
			case "random":
				int amount2 = 0;
				try {
					amount2 = Integer.parseInt(args[1]);
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Number.");
				}
				bot.messenger.sendMessageToDefault(":hourglass: Randomizing...");
				for(Bouncy bouncy : bouncies) {
					bouncy.velocityX = (int) ((Math.random() - 0.5) * amount2);
					bouncy.velocityY = (int) ((Math.random() - 0.5) * amount2);
				}
				bot.messenger.sendMessageToDefault(":white_check_mark: Randomized.");
				break;
			default:
				break;
			}
		} else if(args.length == 3) {
			switch(args[0]) {
			case "add":
				URL url = null;
				int amount = 0;
				try {
					url = new URL(args[1]);
					url.openConnection();
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid URL.");
					return;
				}
				try {
					amount = Integer.parseInt(args[2]);
				} catch (Exception e) {
					bot.messenger.sendMessageToDefault(":no_entry_sign: Invalid Number.");
					return;
				}
				bot.messenger.sendMessageToDefault(":hourglass: Adding " + amount + " Bouncies...");
				int count = 0;
				while(count < amount) {
					if(addBouncy(url)) {
						count++;
					} else {
						bot.messenger.sendMessageToDefault(":no_entry_sign: Error while adding Bouncies :(");
						return;
					}
				}
				bot.messenger.sendMessageToDefault(":white_check_mark: Added!");
				break;
			}
		}
	}
	
	public boolean addBouncy(URL url) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			JWindow window = new JWindow();
			BufferedImage image = ImageIO.read(url);
			window.getContentPane().add(new JLabel("", new ImageIcon(url), SwingConstants.CENTER));
			int x = (int) (Math.random() * screenSize.width);
			window.setBounds(x, 0, image.getWidth(), image.getHeight());
			window.setVisible(true);
			Bouncy bouncy = new Bouncy(window);
			bouncies.add(bouncy);
			return true;
		} catch (Exception e) {
			bot.messenger.sendMessageToDefault(":no_entry_sign: Error while adding Bouncy.");
			return false;
		}
	}
	
	public void start() {
		done = false;
		while(!done) {
			try {
				for(Bouncy bouncy : bouncies) {
					bouncy.tick(gravity, friction);
					Thread.sleep(delay);
				}
			} catch (Exception e) { }
		}
	}

	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}

class Bouncy {
	
	JWindow window;
	
	float bounceReduce = 4.0f;
	
	float velocityX = 0;
	float velocityY = 0;
	Dimension dims = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Bouncy(JWindow window) {
		this.window = window;
	}
	
	public void tick(float gravity, float friction) {
		float x = window.getX() + velocityX;
		float y = window.getY() + velocityY;
		
		if(x < 0) {
			x = 0;
			velocityX = -(velocityX + bounceReduce);
		}
		if(x + window.getWidth() > dims.getWidth()) {
			x = (float) dims.getWidth() - window.getWidth();
			velocityX = -(velocityX - bounceReduce);
		}
		if(y + window.getHeight() > dims.getHeight()) {
			y = (float) dims.getHeight() - window.getHeight();
			velocityY = -(velocityY - bounceReduce);
		}
		if(y + window.getHeight() < dims.getHeight()) {
			velocityY += -gravity;
		}
		window.setBounds((int) x, (int) y, window.getWidth(), window.getHeight());
	}
	
	public void reset() {
		window.setBounds(window.getX(), 0, window.getWidth(), window.getHeight());
	}
	
	public void destroy() {
		window.dispose();
	}
}
