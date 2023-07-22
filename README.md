# Minigame-Demo
Hey there, lately I've been writing a lot of minigames and overall minigame code, so I'd like to give some insights into my journey and overall writing scalable minigame code.

My major source of inspiration and the reason why I even decided to dabble in this topic was Minikloon's guide on minigame code and finite-state machines (https://www.spigotmc.org/threads/organizing-your-minigame-code-using-fsmgasm.235786/), so I'd suggest reading that first.

Before we write any minigame code, let's break down the concept of a minigame so we can develop a simple structure that'll end up benefiting us later on.
The core principle of a minigame is a "phase". Think of phases as short temporary states that have their own tasks and event handlers associated, that run for a given amount of time. 

Here is the main structure of a phase:
```java
public interface GamePhase {

	void start(); // Start method
	
	default void tick() {} // Override if you want, this runs every tick
	
	void end(); // Called when the phase ends
	
	default void cancel() { // Called when the phase is cancelled
		end();
	}
	
}
```

But this is a bit too rudimentary, so let's make something that's tailored to minecraft minigames, implementation is up to you:

```java
public abstract class AbstractGamePhase implements GamePhase {
	
	// Event logic methods
	
	public <EventType extends Event> void registerEvents(Class<EventType> eventClass, Consumer<EventType> handler) {
		// Up for you to implement
	}
	
	public void scheduleSyncTask(Consumer<BukkitTask> task, int delay) {
		
	}
	
	public void scheduleAsyncTask(Consumer<BukkitTask> task, int delay) {
	
	}
	
	public void scheduleRepeatingTask(Consumer<BukkitTask> task, int offset, int delay) {
	
	}
	
	// Event state notification methods
	
	public void onStart(Runnable task) {
	
	}
	
	public void onCancel(Runnable task) {
		// This might be called by external factors, make sure to not just set a new task, but to "add" to the old one
	}
	
	public void onEnd(Runnable task) {
		
	}
	
	// Event state methods
	
	public void dispose() {
		// This is where you cancel all the tasks and unregister your listeners
	}
	
	@Override
	public void end() {
		// Call the end task
		dispose();
	}
	
	@Override
	public void cancel() {
		// Call the cancel task
		dispose();
	}
	
}
```

Phase chaining:
When dealing with multiple phases, it's important to follow a sequential order. So, we'll create a sort of "Phase chain", where we can link a phase order, so that when a phase ends, the next one starts:

```java
public class PhaseChain {

	private final List<AbstractGamePhase> chain = new LinkedList<>();
	private AbstractGamePhase currentPhase;

	public void addPhase(AbstractGamePhase phase) {
		chain.add(phase);
		phase.onEnd(() -> tryAdvance(phase)); // When the phase ends, we try advancing to the next one
		phase.onCancel(() -> tryRetreat(phase)); // When the phase is cancelled, we try going back to the previous one
	}
	
	public void tryAdvance(AbstractGamePhase previous) {
		if(currentPhase != previous) { // Prevents accidental recursion
			return;
		}
		
		GamePhase next = getNext(previous);
		
		if(next == null) { // End of the chain or the chain is broken somehow
			dispose();
		}
		
		setPhase(next);
	} 
	
	public void tryRetreat(AbstractGamePhase current) {
		if(currentPhase != current) {
			return;
		}
		
		GamePhase previous = getPrevious(current);
		
		if(previous == null) {
			current.dispose();
			current.start(); // We dispose and start again, which reboots the phase
			return;
		}
		
		setPhase(previous);
	}
	
	public void start() {
		setPhase(chain.get(0));
	}
	
	public void dispose() {
		for(AbstractGamePhase phase : chain) {
			phase.dispose();
		}
		
		chain.clear();
		currentPhase = null;
	}
	
	// internal methods
	private void setPhase(AbstractGamePhase current) {
		currentPhase = current;
		current.start();
	}
	
	private AbstractGamePhase getNext(AbstractGamePhase current) {
		if(current == null && !chain.isEmpty()) {
			return chain.get(0);
		}
	
		int index = chain.indexOf(current);
		
		if(index == -1 || index == current.size() - 1) {
			return null; // If it's the last phase or if the phase isn't even in the chain anymore
		}
		
		return chain.get(index + 1);
	}
	
	private AbstractGamePhase getPrevious(AbstractGamePhase current) {
		int index = chain.indexOf(current);
		
		if(index < 1) // If it's 0 or -1, meaning there's no previous or it's not even in the chain
			return null;
		}
		
		return chain.get(index - 1);
	}
}
```

With this approach, we have the following rules:
- When a phase ends, the next one starts
- When a phase cancels, the previous one starts. If there is no previous one, the current phase cancels and restarts
- When all phases have finished, the phase chain disposes

Now that this is done, we can start by creating phases, but before we do that, let's write a basic Game class

```java
public class Game {

	// It'd be nice to keep a reference of your plugin just in case
	
	private final PhaseChain phases = new PhaseChain();
	
	// Phase logic
	
	protected void addPhase(AbstractGamePhase phase) {
		phases.addPhase(phase);
	}
	
	protected void start() {
		phases.start();
	}
	
	protected void dispose() {
		phases.dispose();
	}
	
	// Player logic
	
	public Collection<? extends Player> getPlayers() {
		// Up to you
	}
	
	public void addPlayer(Player player) {
	
	}
	
	public void removePlayer(Player player) {
	
	}
	
	public boolean isPlayer(Player player) {
	
	}
	
}
```

A simple minigame to code will be TNT Tag, which when broken down into states, looks like the following:
- Wait for players
- Countdown
- Main "game loop" starts:

- Every 15 seconds all "bombers" explode, and new bombers are picked at random
- As a bomber, if you punch a "non-bomber", they become the bomber. The timer does not reset
- Last player standing wins

Here's a sample version of the "Waiting for players phase":

```java
public class WaitingForPlayersPhase extends AbstractGamePhase {

	private final Game game;
	private final int amount;
	
	public WaitingForPlayersPhase(Game game, int amount) {
		this.game = game;
		this.amount = amount;
	}
	
	@Override
	public void start() {
		registerEvents(PlayerJoinEvent.class, event -> {
			// It's ok to run this check on every player join event, ideally we'd have a GameAddPlayerEvent
			
			if(game.getPlayers().size() >= amount) {
				end();
			}
		});
	}
}
```

And a sample game, skipping all the map logic for now

```java
public class TNTTagGame extends Game {

	public TNTTagGame() {
		addPhase(new WaitingForPlayersPhase(this, 4)) // 4 players minimum
		// Add all the other phases
		
		start();
	}
}
```

I will be updating this guide with more information as I see fit. A lot of my current minigame code is still being polished, but expect updates on team handling, scoreboards, maps and a full demo game!
