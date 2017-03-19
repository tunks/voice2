package com.att.voice2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.speech.tts.TextToSpeech;
import static com.att.voice2.ListenerFactory.REQUEST_OK;
import com.att.voice2.parser.ParserFactory;
import com.att.voice2.services.RestServiceClient;
import com.att.voice2.services.RestServiceFactory;
import com.att.voice2.tasks.RestTask;
import com.att.voice2.models.ResponseData;
import com.att.voice2.parser.SSOTQueryParser;
import com.att.voice2.parser.ParserFactory.QueryModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RequestHandler<Map<String,String>>{
    public static final String ACTION_INTETNT_TTS = "INTENT_SSOT_TEXT_TO_SPEECH";
    public static final String SSOT_SST_RESPONSE = "SSOT_SST_RESPONSE";
    private static final String ACTION_FOR_INTENT_CALLBACK = "SSOT_REST_CALL"; //Intent callback key
    private RestServiceFactory serviceFactory;
    private SSOTQueryParser queryParser;
    private String baseUrl;
    private RestServiceClient serviceClient;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageButton speechButton;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get base url from resources
        baseUrl = this.getResources().getString(R.string.base_url);
        //initial rest service factory
        serviceFactory = new RestServiceFactory();
        //initialise service client
        serviceClient = serviceFactory.createRestServiceClient(baseUrl);
        //initialize text view
        textView = (TextView)findViewById(R.id.textView);
        //initialize progress bar
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        speechButton = (ImageButton)findViewById(R.id.imageButton1);
        //initialize query parser
        queryParser = new SSOTQueryParser();
        //intialize text to speech
        initializeTextToSpeech();
        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        speechButton.setOnClickListener(ListenerFactory.getListener(this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
             ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
             textView.setText(results.get(0));
             QueryModel queryModel = queryParser.parse(results.get(0));
              if(queryModel != null){
                  Map<String,String> request = new HashMap<>();
                  request.put("cypherQuery",queryModel.getIdentifier());
                  this.processRequest(request);
              }
              else{
                  String message = "Invalid, cannot understand what you want";
                  errorResponseToVoice(message);
                  Log.e("Request error",message);
                  textView.setTextColor(Color.RED);
                  textView.setText(message);
              }
        }
    }

    @Override
    public void processRequest(Map<String,String> request)
    {
        // the request
        try
        {
            textView.setText("");
            textView.setTextColor(Color.BLACK);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            RestTask task = new RestTask(this.getBaseContext(), ACTION_FOR_INTENT_CALLBACK,serviceClient);
            task.execute(request);
        }
        catch (Exception e)
        {
            Log.e("Error getting content: ",e.getMessage());
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK));
        this.registerReceiver(ttsReceiver, new IntentFilter(ACTION_INTETNT_TTS));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.unregisterReceiver(receiver);
        this.unregisterReceiver(ttsReceiver);
    }

    /**
     * Our Broadcast Receiver. We get notified that the data is ready this way.
     */
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            ///clear the progress indicator
            if (progressBar != null)
            {
                progressBar.setVisibility(View.INVISIBLE);
            }

            ResponseData  response = (ResponseData) intent.getSerializableExtra(RestTask.HTTP_RESPONSE);

            System.out.println("onReceive result "+response);
            if(response.getCode().equals(ResponseData.StatusCode.STATUS_OK)) {
                textView.append(response.getContent().toString());
            }
            else{
                textView.setTextColor(Color.RED);
                textView.setText(response.getDescription());
            }
        }
    };

    private BroadcastReceiver ttsReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String  response =  intent.getStringExtra(SSOT_SST_RESPONSE);
            speak(response);
        }
    };

    private void initializeTextToSpeech(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }


        });
    }

    private void speak(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void errorResponseToVoice(String errorMessage){
        Intent intent = new Intent( ACTION_INTETNT_TTS );
        intent.putExtra(SSOT_SST_RESPONSE,errorMessage);
        // broadcast the completion);
        this.getBaseContext().sendBroadcast(intent);
    }

}
