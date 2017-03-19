package com.att.voice2;

import android.view.View.OnClickListener;
import android.app.Activity;

import com.att.voice2.listeners.SpeakerListener;

/**
 * Created by ebrimatunkara on 3/13/17.
 */

public class ListenerFactory {
    public static final int REQUEST_OK = 1;

    public static OnClickListener getListener(Activity activity ){
       return new SpeakerListener(activity);
    }

}
