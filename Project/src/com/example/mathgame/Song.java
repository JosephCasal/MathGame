package com.example.mathgame;

import android.content.Context;
import android.media.MediaPlayer;

public class Song implements Runnable{

	private static MediaPlayer music;
	private static MediaPlayer victory;
	
	public Song(Context context){
		music = MediaPlayer.create(context, R.raw.happy_instrumental);
		victory = MediaPlayer.create(context, R.raw.flawless_victory);
	}
	
	@Override
	public void run() {
		music.start();
		music.setLooping(true);
	}
	
	public static void stop(){
		music.stop();
	}
	
	public static void pause(){
		music.pause();
	}
	
	public static void start(){
		music.start();
	}
	
	public static void startVictory(){
		victory.setVolume(25, 25);
		victory.start();
	}
}