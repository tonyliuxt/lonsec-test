package com.lonsec.project.java;

public class Application {
	
	/**
	 * Running entrance
	 * @param args
	 */
	public static void main(String args[]){
		// 1. load configuration
		if(!ConfigReader.LoadConfig()){
			return;
		}
		// 2. Running process task thread
		ProcessTask task = new ProcessTask();
		task.run();
	}
}
