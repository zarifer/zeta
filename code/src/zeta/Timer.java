package zeta;

import java.util.ArrayList;

public class Timer {
	private static long startTime;
	private static final long roundTime=60000;
	private static long remainingTime;
	private static final long enemyMaxResponseTime=10000;
	private static long enemyRemainingTime;
	private static long enemyStartTime;
	private static final ArrayList<Steppable> steppables = new ArrayList<Steppable>();
	
	public static void addSteppable(Steppable s) {
		steppables.add(s);
	}
	
	public static void removeSteppable(Steppable s) {
		steppables.remove(s);
	}
	
	public static void startRound() {
		startTime = System.currentTimeMillis();
		remainingTime = roundTime;
	}
	
	public static void startEnemyRound() {
		enemyStartTime = System.currentTimeMillis();
		enemyRemainingTime = enemyMaxResponseTime;
	}
	
	public static void clearSteppable() {
		steppables.clear();
	}
	
	public static void tick() {
		for(Steppable steppy:steppables) {
			steppy.step();
		}
	}
	
	public static long getEnemyRemainingTime() {
		return enemyRemainingTime-(System.currentTimeMillis()-enemyStartTime);
	}
	
	public static long timeSpentByEnemy() {
		return Math.min(Math.abs(System.currentTimeMillis()-enemyStartTime), enemyMaxResponseTime);
	}
	
	public static long getRemainingTime() {
		return remainingTime-(System.currentTimeMillis()-startTime);
	}
	
	public static void increaseRemainingTime(long time) {
		remainingTime+=time;
	}
}
