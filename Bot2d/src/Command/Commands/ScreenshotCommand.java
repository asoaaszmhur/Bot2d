package Command.Commands;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Command.Command;
import Main.Bot2d;

public class ScreenshotCommand implements Command {
	
	Bot2d bot;
	String cmd;
	
	String[] help = { "Usage: MyBot--Screenshot",  
		"Description: Takes a Screenshot."
	};
	
	public ScreenshotCommand(String cmd, Bot2d bot) {
		this.cmd = cmd.toLowerCase();
		this.bot = bot;
	}
	
	public void action(String[] args, MessageReceivedEvent event) {
		if(args == null) {
			bot.messenger.sendMessageToDefault(":hourglass: Taking Screenshot...");
			screenshot();
			bot.messenger.sendMessageToDefault(":white_check_mark: Done.");
		}
	}
	
	public void screenshot() {
		try {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			File temp = File.createTempFile("screenshot", ".png");
			ByteArrayOutputStream sizeCheck = new ByteArrayOutputStream();
			ImageIO.write(capture, "png", sizeCheck);
			if(sizeCheck.size() > 8388608) {
				capture = rescale(capture, capture.getWidth() / 2, capture.getHeight() / 2);
				bot.messenger.sendMessageToDefault(":hourglass: Image too large! Rescaling to half the resolution.");
			}
			ImageIO.write(capture, "png", temp);
			bot.messenger.sendFileToDefault(temp);
		} catch (Exception e) {
			System.out.println("Error in Command:Commnds:ScreenshotCommand:screenshot(): " + e);
			bot.messenger.sendMessageToDefault("Error while taking screenshot. Sorrweh :fearful:");
		}
	}

	public BufferedImage rescale(BufferedImage image, int width, int height) {
		BufferedImage newImage = null;
	    if(image != null) {
	        newImage = new BufferedImage(width, height, image.getType());
	        Graphics2D g = newImage.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(0.5, 0.5);
	        g.drawRenderedImage(image, at);
	    }
	    return newImage;
	}
	
	public String[] help() {
		return help;
	}

	public String getCmd() {
		return cmd;
	}

}
