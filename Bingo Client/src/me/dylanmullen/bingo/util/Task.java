package me.dylanmullen.bingo.util;

public abstract class Task implements Runnable
{

	private Thread thread;
	private boolean running;
	private boolean repeatingTask;

	private long last;
	private double delay;

	public Task(double seconds)
	{
		this.delay = seconds * 1000;
		System.out.println(delay);
		this.thread = new Thread(this);
		setRepeatingTask(true);
	}

	public Task()
	{
		this.thread = new Thread(this);
	}

	public abstract void task();

	public synchronized void start()
	{
		if (running)
			return;
		thread.start();
		running = true;
	}

	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
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

		while (running)
		{
			if (delay == -1)
			{
				task();
			}
			else if (System.currentTimeMillis() - getLast() >= getDelay())
			{
				task();
				setLast(System.currentTimeMillis());
			}
		}

	}

	public long getLast()
	{
		return last;
	}

	public double getDelay()
	{
		return delay;
	}

	public void setLast(long last)
	{
		this.last = last;
	}

	public Task setRepeatingTask(boolean repeatingTask)
	{
		this.repeatingTask = repeatingTask;
		return this;
	}

	public boolean isRepeatingTask()
	{
		return repeatingTask;
	}

}
