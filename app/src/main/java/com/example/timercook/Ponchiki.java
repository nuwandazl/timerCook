package com.example.timercook;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ponchiki#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ponchiki extends Fragment {

    private Button exitexit2;

    private static final long START_TIME_IN_MILLIS = 10000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ponchiki() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ponchiki.
     */
    // TODO: Rename and change types and number of parameters
    public static Ponchiki newInstance(String param1, String param2) {
        Ponchiki fragment = new Ponchiki();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ponchiki, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) getView().findViewById(R.id.list_view2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Ingredienty2,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);


        exitexit2 = (Button) getView().findViewById(R.id.exit2);
        exitexit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = MediaPlayer.create(getContext(), R.raw.hogwarts);
                if (player.isPlaying()) {
                    player.stop();
                    player.release();
                    player = MediaPlayer.create(getContext(), R.raw.hogwarts);
                }
                player.start();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentStore.recipesFragment).commit();

            }
        });
        mTextViewCountDown = getView().findViewById(R.id.countTime);
        mButtonStartPause = getView().findViewById(R.id.btnStartPause);
        mButtonReset = getView().findViewById(R.id.btnReset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                }else{
                    startTimer();
                }
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(getContext().VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    long[] pattern={0,400,100,0};
                    vibrator.vibrate(VibrationEffect.createWaveform(pattern,-1));
                }else {
                    vibrator.vibrate(1000);
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Старт");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                final String CHANEL = "main";
                final int NOTIFY_ID = 100;

                Handler handler = new Handler();
                NotificationManager  notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel channel = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    channel = new NotificationChannel(CHANEL, "Main chanel", notificationManager.IMPORTANCE_DEFAULT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(channel);
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANEL)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle(getContext().getString(R.string.timerOver))
                                .setChannelId(CHANEL)
                                .setContentText(getContext().getString(R.string.miniText))
                                .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);



                        Notification notification=builder.build();
                        notification.defaults=Notification.DEFAULT_ALL;

                        notificationManager.notify(NOTIFY_ID, builder.build());
                    }
                }, 5000);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Пауза");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Старт");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void  resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes,seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}

