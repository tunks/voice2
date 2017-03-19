package com.att.voice2.listeners;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.att.voice2.RequestHandler;

import java.util.HashMap;

import static com.att.voice2.ListenerFactory.REQUEST_OK;
import static com.att.voice2.MainActivity.ACTION_INTETNT_TTS;
import static com.att.voice2.MainActivity.SSOT_SST_RESPONSE;
import com.att.voice2.R;

/**
 * Created by ebrimatunkara on 3/13/17.
 */

public class SpeakerListener implements OnClickListener {
    private Activity activity;
    //private
    public SpeakerListener(Activity activity) {

        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        PackageManager pm = this.activity.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if(activities.size()>0){
            processVoice();
        }
        else {
            Toast.makeText(this.activity, "Recognizer package not present", Toast.LENGTH_LONG).show();
            errorResponseToVoice("Recognizer package not present in your device");
        }
    }

    private void processVoice(){
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            // Specify the calling package to identify your application
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
            // Display an hint to the user about what he should say.
            String title = this.activity.getResources().getString(R.string.recognizer_title);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, title);
            // Given an hint to the recognizer about what the user is going to say
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // Specify how many results you want to receive. The results will be sorted
            // where the first result is the one with higher confidence.
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            activity.startActivityForResult(intent, REQUEST_OK);
        } catch (Exception e) {
            String errorMsg = "Error initializing speech to text engine";
            //errorResponseToVoice(errorMsg);
            Log.e(e.getMessage(), errorMsg);
            Toast.makeText(activity,  errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    private void errorResponseToVoice(String errorMessage){
        Intent intent = new Intent( ACTION_INTETNT_TTS );
        intent.putExtra(SSOT_SST_RESPONSE,errorMessage);
        // broadcast the completion);
        this.activity.getBaseContext().sendBroadcast(intent);
    }
}
