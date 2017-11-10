package Command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

	public void action(String[] args, MessageReceivedEvent event);
	public String[] help();
	public String getCmd();
}
