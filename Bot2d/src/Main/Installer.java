package Main;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class Installer {
	
	private boolean Installing = true;
	private boolean failed = false;
	
	public Installer(String link, String output) {
		URL url;
		try {
			url = new URL(link);
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			File file = new File(output);
			if(!file.exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(output);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (Exception e) { failed = true; }
		this.Installing = false;
	}
	
	public boolean isInstalling() {
		return this.Installing;
	}
	
	public boolean failed() {
		return failed;
	}
}
