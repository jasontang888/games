package br.ita.tsp.media;

import br.ita.tsp.Game;
import br.ita.tsp.R;
import br.ita.tsp.util.Constants;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class GameMediaManager {
	private MediaPlayer mMediaPlayer= null;
	private static boolean isWinner = false;
	private Game game=null;
	private static GameMediaManager instance=null;
	
	public static GameMediaManager getInstance(Game game){
		if(instance==null)
			instance=new GameMediaManager(game);
		return instance;
	}
	
	private GameMediaManager(Game game){
		this.game=game;
		
	}
	
	public void playMusic(boolean isWinner){
		if(Constants.MUSIC!=0){
			GameMediaManager.isWinner= isWinner;
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					Handler handler = new Handler();
					if(GameMediaManager.isWinner==true)//ganhou
						mMediaPlayer= MediaPlayer.create(game, R.raw.whowho);
					else 
						mMediaPlayer= MediaPlayer.create(game, R.raw.doh);
					
					mMediaPlayer.start();
					
					Message message = handler.obtainMessage();
					handler.dispatchMessage(message);
					Looper.loop();
				}
			}).start();
		}
	}
	
	public void stopMusic(){
		if(mMediaPlayer!=null)
			mMediaPlayer.stop();
	}
}
