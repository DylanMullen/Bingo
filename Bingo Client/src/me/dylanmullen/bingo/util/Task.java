package me.dylanmullen.bingo.util;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public abstract class Task implements Runnable
{

	private Thread thread;
	private boolean running;
	private boolean repeatingTask;

	private long last;
	private double delay;

	/**
	 * A task that is created to be executed every time the seconds are reached.
	 * Sets the task as a repeating task.
	 * 
	 * @param seconds Delay to repeat the task.
	 */
	public Task(double seconds)
	{
		this.delay = seconds * 1000;
		this.thread = new Thread(this);
		setRepeatingTask(true);
	}

	/**
	 * Creates a non-repeating task that will be completed whenever the task is set
	 * to start.
	 */
	public Task()
	{
		this.thread = new Thread(this);
	}

	/**
	 * The task to complete.
	 */
	public abstract void task();

	/**
	 * Starts the task as long as the task is not already running.
	 */
	public synchronized void start()
	{
		if (this.running)
			return;
		this.thread.start();
		this.running = true;
	}

	/**
	 * Stops the task.
	 */
	public synchronized void stop()
	{
		this.running = false;
		try
		{
			this.thread.join();
		} catch (InterruptedException e)
		{
		}
	}

	@Override
	public void run()
	{
		if (!isRepeatingTask())
		{
			task();
			return;
		}

		while (this.running)
		{
			if (getDelay() == -1)
			{
				task();
			} else if (System.currentTimeMillis() - getLast() >= getDelay())
			{
				task();
				setLast(System.currentTimeMillis());
			}
		}

	}

	/**
	 * @return Returns the last time the task was last called.
	 */
	public long getLast()
	{
		return this.last;
	}

	/**
	 * @return Returns the delay of the task.
	 */
	public double getDelay()
	{
		return this.delay;
	}

	/**
	 * Sets the last time the task was called.
	 * 
	 * @param last The time to be set.
	 */
	public void setLast(long last)
	{
		this.last = last;
	}

	/**
	 * Sets whether or not this task is a repeating task and returns the task.
	 * 
	 * @param repeatingTask Whether or not it is repeating.
	 * @return Returns the task.
	 */
	public Task setRepeatingTask(boolean repeatingTask)
	{
		this.repeatingTask = repeatingTask;
		return this;
	}

	/**
	 * @return Returns true if the task is a repeating task.
	 */
	public boolean isRepeatingTask()
	{
		return this.repeatingTask;
	}

}
