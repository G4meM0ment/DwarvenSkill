package me.G4meM0ment.DwarvenSkill.Schedule;

import me.G4meM0ment.DwarvenSkill.Draw.AnimateButton;
import me.G4meM0ment.DwarvenSkill.Handler.FilesHandler;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf.DwarfHandler;

public class Schedule implements Runnable{
	
	
	private FilesHandler fh;
	private AnimateButton animateButton;
	private DwarfHandler dh;
	
    private static final int FRAME_RATE = 2;
	private static final int FRAME_PERIOD = 1000 / FRAME_RATE;
	private static boolean isRunning = false;
	private Thread thread = null;
	private static int hour = 0;
	private static int day = 0;
	private static int week = 0;
	private static int month = 0;
	private static int year = 0;
	private static String splitter = "_";
	private static String currentTime = year+splitter
			+month+splitter
			+week+splitter
			+day+splitter
			+hour;
	
	public Schedule() {
		fh = new FilesHandler();
		animateButton = new AnimateButton();
		dh = new DwarfHandler();
	}
	
	public void startSchedule() {
//		setupSchedule();
		run();
	}
	public void pause() {
		isRunning = false;
		while(true) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}
	public void resume() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void setupSchedule() {
		if(fh.loadSchedule() != null) {
			year = Integer.parseInt(fh.loadSchedule().split(splitter)[0]);
			month = Integer.parseInt(fh.loadSchedule().split(splitter)[1]);
			week = Integer.parseInt(fh.loadSchedule().split(splitter)[2]);
			day = Integer.parseInt(fh.loadSchedule().split(splitter)[3]);
			hour = Integer.parseInt(fh.loadSchedule().split(splitter)[4]);
		}
		else {
			hour = 0;
			day = 0;
			week = 0;
			month = 0;
			year = 0;
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		
		long beginTime;
		long timeDiff;
		int sleepTime;
		int dayCounter = 0;
		
		while(isRunning) {
			if(animateButton.getPPState())
				continue;
			
			beginTime = System.currentTimeMillis();

			if(hour >= 24) {
				day += 1;
				dayCounter += 1;
				hour = 0;
			}
			if(dayCounter >= 7) {
				week += 1;
				dayCounter = 0;
			}
			if(week >= 4 || day >= 28) {
				week = 0;
				month += 1;
				day = 0;
			}
			if(month >= 12) {
				year += 1;
				month = 0;
			}			
			hour += 1;
			dh.firstTestMove();
				
			timeDiff = System.currentTimeMillis() - beginTime;
			sleepTime = (int)(FRAME_PERIOD - timeDiff);

			if (sleepTime > 0) {
				try {
					Thread.currentThread().sleep(sleepTime);
				} catch (InterruptedException e) {}
			}
		} 
	}

	public String getCurrentDate() {
		currentTime = year+splitter
				+month+splitter
				+week+splitter
				+day+splitter
				+hour;
		return currentTime;
	}
	
	
}
