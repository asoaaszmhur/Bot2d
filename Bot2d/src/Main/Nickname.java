package Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Nickname {

	Bot2d bot;
	String nickname = "";
	
	public Nickname(Bot2d bot) {
		this.bot = bot;
		
		String nickname = bot.config.getProperty("nickname");
		if(nickname != null) {
			this.nickname = nickname;
		}
		if(bot.getIPWebsite().length() > 0) {
			try {
				URL getip = new URL(bot.getIPWebsite());
				BufferedReader in = new BufferedReader(new InputStreamReader(getip.openStream()));
				nickname = in.readLine();
				bot.setIP(nickname);
				if(this.nickname == "") {
					this.nickname = nickname;
				}
				in.close();
			} catch (Exception e) {
				System.out.println("(Cant get IP) Error in Main:Nickname:Constructor(): " + e);
			}
		}
	}
	
	public void setNickname(String newNickname) {
		this.nickname = newNickname;
		bot.config.setProperty("nickname", newNickname);
		bot.config.saveProperties();
	}
	
	public String getNickname() {
		return nickname;
	}
}
