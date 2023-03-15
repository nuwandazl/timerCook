package com.example.timercook;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    private Button exitexit;

    private static final long START_TIME_IN_MILLIS = 60000;
    private static final long START_TIME_IN_MILLIS2 = 1200000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private TextView mTextViewCountDown2;
    private Button mButtonStartPause2;
    private Button mButtonReset2;

    private CountDownTimer mCountDownTimer;
    private CountDownTimer mCountDownTimer2;

    private boolean mTimerRunning;
    private boolean mTimerRunning2;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mTimeLeftInMillis2 = START_TIME_IN_MILLIS2;
    private Vibrator vibrator;
    private Handler handler;
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 100;
    private final String CHANEL = "main";
    private Context context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    long[] pattern={0,400,100,0};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) getView().findViewById(R.id.list_view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Ingredienty,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        exitexit = (Button) getView().findViewById(R.id.exit);
        exitexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentStore.recipesFragment).commit();
            }
        });
        mTextViewCountDown = getView().findViewById(R.id.countTime);
        mButtonStartPause = getView().findViewById(R.id.btnStartPause);
        mButtonReset = getView().findViewById(R.id.btnReset);

        mTextViewCountDown2 = getView().findViewById(R.id.countTime2);
        mButtonStartPause2 = getView().findViewById(R.id.btnStartPause2);
        mButtonReset2 = getView().findViewById(R.id.btnReset2);


        mButtonStartPause2.setOnClickListener(new View.OnClickListener() {
            private Context context;

            @Override
            public void onClick(View view) {
                if (mTimerRunning2) {
                    pauseTimer2();
                }else{
                    startTimer2();
                }
            }
        });
        mButtonReset2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resetTimer2();
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
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Пауза");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void startTimer2() {
        mCountDownTimer2 = new CountDownTimer(mTimeLeftInMillis2,1000) {
            @Override
            public void onTick(long millisUntilFinished2) {
                mTimeLeftInMillis2 = millisUntilFinished2;
                updateCountDownText2();
            }

            @Override
            public void onFinish() {
                mTimerRunning2 = false;
                mButtonStartPause2.setText("Старт");
                mButtonStartPause2.setVisibility(View.INVISIBLE);
                mButtonReset2.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning2 = true;
        mButtonStartPause2.setText("Пауза");
        mButtonReset2.setVisibility(View.INVISIBLE);
    }


    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Старт");
        mButtonReset.setVisibility(View.VISIBLE);
    }
    private void pauseTimer2() {
        mCountDownTimer2.cancel();
        mTimerRunning2 = false;
        mButtonStartPause2.setText("Старт");
        mButtonReset2.setVisibility(View.VISIBLE);
    }

    private void  resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void  resetTimer2() {
        mTimeLeftInMillis2 = START_TIME_IN_MILLIS2;
        updateCountDownText2();
        mButtonReset2.setVisibility(View.INVISIBLE);
        mButtonStartPause2.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes,seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateCountDownText2() {
        int minutess = (int) (mTimeLeftInMillis2 / 1000) / 60;
        int secondss = (int) (mTimeLeftInMillis2 / 1000) % 60;

        String timeLeftFormatted2 = String.format(Locale.getDefault(),"%02d:%02d", minutess,secondss);

        mTextViewCountDown2.setText(timeLeftFormatted2);
    }
}