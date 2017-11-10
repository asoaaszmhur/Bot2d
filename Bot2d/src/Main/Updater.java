package Main;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Updater {

	Bot2d bot;
	int newVersion = -1;
	
	updateThread thread;
	
	final int TIME_UPDATE_CHECK_HRS = 3;
	
	public Updater(Bot2d bot) {
		this.bot = bot;
		thread = new updateThread(bot, this, TIME_UPDATE_CHECK_HRS);
		thread.start();
		
		fetchUpdates();
	}
	
	public boolean updatesAvailable() {
		if(newVersion != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void fetchUpdates() {
		String content = null;
		List<Integer> versions = new ArrayList<Integer>();
		try {
			URLConnection connection = null;
			connection = new URL(bot.getUpdateWebsite() + "/update").openConnection();
			Scanner scan = new Scanner(connection.getInputStream());
			scan.useDelimiter("\\Z");
			content = scan.next();
			scan.close();
			int index = content.indexOf(">Dropper-");
			while (index >= 0) {
				String version = content.substring(index + 9, content.indexOf(".jar<", index + 9));
				versions.add(Integer.parseInt(version));
			    index = content.indexOf(">Dropper", index + 1);
			}
			int greatestVer = 0;
			for(int ver : versions) {
				if(ver > greatestVer) {
					greatestVer = ver;
				}
			}
			if(greatestVer > bot.getVersion()) {
				newVersion = greatestVer;
			}
		} catch (Exception e) {
			System.out.println("FATAL ERROR: While checking for Updates.");
		}
	}
	
	public void beginUpdate() {
		if(newVersion > bot.getVersion()) {
			Installer install = new Installer(bot.getUpdateWebsite() + "/update/Dropper-" + newVersion + ".jar", "C:/Users/" + System.getProperty("user.name") + "/update.jar");
			while(install.isInstalling()) {
				try {
					Thread.sleep(500);
				} catch(Exception e) { }
			}
			try {
				Runtime.getRuntime().exec("java -jar \"C:/Users/" + System.getProperty("user.name") + "/update.jar\"");
			} catch (Exception e) {
				
			}
		} else {
			// No new Versions?
		}
	}
}

class updateThread extends Thread {
	
	Bot2d bot;
	Updater updater;
	boolean shutdown = false;
	int updateDelay = 0;
	
	public updateThread(Bot2d bot, Updater updater, int updateDelay) {
		this.bot = bot;
		this.updater = updater;
		this.updateDelay = updateDelay;
	}
	
	@Override
	public void run() {
		while(!shutdown) {
			try {
				TimeUnit.HOURS.sleep(updateDelay);
				updater.fetchUpdates();
				if(updater.updatesAvailable()) {
					bot.updateMode();
				}
			} catch (Exception e) {
				// Error Checking for Updates;
			}
		}
	}
}
