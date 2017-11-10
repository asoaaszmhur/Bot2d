package Handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import Command.Command;

public class Execution {

	ExecutorService executePool;
	List<Future<?>> pastExecutions = new ArrayList<Future<?>>();
	
	public Execution() {
		executePool = Executors.newCachedThreadPool();
	}
	
	public Future<?> executeCommand(Command command, String[] args, MessageReceivedEvent event) {
		Future<?> future = executePool.submit(new RunnableCommand(command, args, event));
		pastExecutions.add(future);
		return future;
	}
	
	public void clearPastExecutions() {
		pastExecutions.clear();
	}
	
	public void shutdownAfterExecutions() {
		executePool.shutdown();
	}
	
	public void shutdownNow() {
		executePool.shutdownNow();
	}
	
	
	// Gets and Sets
	
	public Future<?> getLastExecution() {
		return pastExecutions.get(pastExecutions.size());
	}
}

class RunnableCommand implements Runnable {

	Command command;
	String[] args;
	MessageReceivedEvent event;
	
	public RunnableCommand(Command command, String[] args, MessageReceivedEvent event) {
		this.command = command;
		this.args = args;
		this.event = event;
	}
	
	@Override
	public void run() {
		command.action(args, event);
	}
}