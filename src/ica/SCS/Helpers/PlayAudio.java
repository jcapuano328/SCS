package ica.SCS.Helpers;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Created by jcapuano on 5/18/2014.
 */
public class PlayAudio {
    private MediaPlayer player;
    private Context context;

	public PlayAudio(Context ctx) {
		context = ctx;
        player = null;
	}
		
	public void play () {
		try {
			player = MediaPlayer.create (context, ica.SCS.R.raw.droll);
            player.setLooping(false);
			player.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
				    player.stop ();
    				player.release ();
    				player = null;
                }                    
			});
			player.start ();
		} catch (Exception ex) {
		}
	}

}
