package com.puzzlegame.tictactoe;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    Button[][] button = new Button[3][3];
    boolean playerturn = true, computer_win, delay_flag = false;
    String selected_radio;
    int count = 0;
    int pts1;
    int pts2;
    int q,r,m,n;
    int loop_break = 0;
    ImageButton close_btn;
    String difficulty_level;
    Dialog dialog;
    String player_1_name;
    String player_2_name;
    MediaPlayer btn_click_sound;
    MediaPlayer error_sound;
    TextView player1, player2, toast,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        title.setText("TicTacToe");

        Toolbar toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        player1 = findViewById(R.id.player_1);
        player2 = findViewById(R.id.player_2);

        btn_click_sound=MediaPlayer.create(this,R.raw.btn_click);
        error_sound=MediaPlayer.create(this,R.raw.alert);

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.toast_item);
        toast = dialog.findViewById(R.id.toast);
        close_btn = dialog.findViewById(R.id.close_btn);
        dialog.setCanceledOnTouchOutside(true);

        selected_radio = getIntent().getStringExtra("android");
        if (selected_radio.equals(getString(R.string.single_player))) {
            difficulty_level = getIntent().getStringExtra("level");
            player2.setText("Android (O): 0");
//            player_1_name=getIntent().getStringExtra("player_1");
//            if (!player_1_name.equals("")){
//                player1.setText(player_1_name);
        }
//        }
//        else if (selected_radio.equals("Two player")){
//            player_1_name=getIntent().getStringExtra("player_1");
//            player_2_name=getIntent().getStringExtra("player_2");
//            if(!player_1_name.equals("")){
//                player1.setText(player_1_name);
//            }
//            if (!player_2_name.equals(""))
//            {
//                player2.setText(player_2_name);
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                button[i][j] = findViewById(resID);
                button[i][j].setOnTouchListener(this);
//                button[i][j].setOnClickListener(this);
            }
        }

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        button[i][j].setText("");
                    }
                }
                playerturn = true;
                count = 0;
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        button[i][j].setText("");
                    }
                }
                playerturn = true;
                count = 0;
                dialog.dismiss();
            }
        });


    }

//    @Override
//    public void onClick(View view) {
//
//        if (!((Button) view).getText().equals("")) {
//            return;
//        }
//
//        if (selected_radio.equals("Play with computer")) {
//            if (playerturn) {
//                loop_break = 0;
//                computer_win = false;
//                ((Button) view).setText("X");
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        button[i][j].setEnabled(false);
//                    }
//                }
//                count = count + 2;
//            }
//            if (checkwin()) {
//                player1wins();
//                return;
//            } else if (count >= 9) {
//                draw();
//                return;
//            } else {
//                while (loop_break == 0) {
//                    int random = new Random().nextInt(9);
//                    q = random / 3;
//                    r = random % 3;
//                    if (button[q][r].getText().equals("")) {
//                        button[q][r].postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                button[q][r].setText("O");
//                            }
//                        },1000);
//
//                        loop_break = 1;
//                    }
//                }
//                if (checkwin()) {
//                    player2wins();
//                    return;
//                }
//                playerturn = true;
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        button[i][j].setEnabled(true);
//                    }
//                }
////                Thread();
////                synchronized (thread) {
////                    try {
////                        thread.wait();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//////                    if (difficulty_level.equals("Hard")) {
//////                        if (count > 2) {
//////                            for (int i = 0; i < 3; i++) {
//////                                for (int j = 0; j < 3; j++) {
//////                                    if (button[i][j].getText().equals("")) {
//////                                        button[i][j].setText("O");
//////                                        if (checkwin()) {
//////                                            player2wins();
//////                                            computer_win = true;
//////                                            loop_break = 1;
////////                                        Log.d("1",i+" "+j+computer_win);
//////                                            return;
//////                                        } else {
////////                                        Log.d("2",i+" "+j+computer_win);
//////                                            button[i][j].setText("");
//////                                        }
//////                                    }
//////                                }
//////                            }
//////                            if (!computer_win) {
//////                                for (int i = 0; i < 3; i++) {
//////                                    for (int j = 0; j < 3; j++) {
//////                                        if (button[i][j].getText().equals("")) {
//////                                            button[i][j].setText("X");
//////                                            if (checkwin()) {
////////                                            Log.d("3",i+" "+j+computer_win);
//////                                                button[i][j].setText("O");
//////                                                loop_break = 1;
//////                                                return;
//////                                            } else {
////////                                            Log.d("4",i+" "+j+computer_win);
//////                                                button[i][j].setText("");
//////                                            }
//////                                        }
//////                                    }
//////                                }
//////                            }
//////                        }
//////                    }
//////
////////                if (difficulty_level.equals("Level 2") && loop_break == 0) {
////////                    switch (((Button) view).getId()) {
////////                        case R.id.button_11:
////////                            if (button[0][0].getText().equals("X") && button[2][2].getText().equals("")) {
////////                                button[2][2].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[2][2].getText().equals("X") && button[0][0].getText().equals("")) {
////////                                button[0][0].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[0][2].getText().equals("X") && button[2][0].getText().equals("")) {
////////                                button[2][0].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[2][0].getText().equals("X") && button[0][2].getText().equals("")) {
////////                                button[0][2].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[0][1].getText().equals("X") && button[2][1].getText().equals("")) {
////////                                button[2][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[2][1].getText().equals("X") && button[0][1].getText().equals("")) {
////////                                button[0][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][0].getText().equals("X") && button[1][2].getText().equals("")) {
////////                                button[1][2].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][0].getText().equals("") && button[1][2].getText().equals("X")) {
////////                                button[1][0].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_00:
////////                            if (button[1][1].getText().equals("") && button[2][2].getText().equals("X")) {
////////                                button[1][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[0][1].getText().equals("") && button[0][2].getText().equals("X")) {
////////                                button[0][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][0].getText().equals("") && button[2][0].getText().equals("X")) {
////////                                button[1][0].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_02:
////////                            if (button[0][1].getText().equals("") && button[0][0].getText().equals("X")) {
////////                                button[0][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][1].getText().equals("") && button[2][0].getText().equals("X")) {
////////                                button[1][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][2].getText().equals("") && button[2][2].getText().equals("X")) {
////////                                button[1][2].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_20:
////////                            if (button[2][1].getText().equals("") && button[2][2].getText().equals("X")) {
////////                                button[2][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][1].getText().equals("") && button[0][2].getText().equals("X")) {
////////                                button[1][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][0].getText().equals("") && button[0][0].getText().equals("X")) {
////////                                button[1][0].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_22:
////////                            if (button[2][1].getText().equals("") && button[2][0].getText().equals("X")) {
////////                                button[2][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][1].getText().equals("") && button[0][0].getText().equals("X")) {
////////                                button[1][1].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[1][2].getText().equals("") && button[0][2].getText().equals("X")) {
////////                                button[1][2].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_01:
////////                            if (button[0][0].getText().equals("X") && button[0][2].getText().equals("")) {
////////                                button[0][2].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[0][0].getText().equals("") && button[0][2].getText().equals("X")) {
////////                                button[0][0].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_21:
////////                            if (button[2][0].getText().equals("X") && button[2][2].getText().equals("")) {
////////                                button[2][2].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[2][0].getText().equals("") && button[2][2].getText().equals("X")) {
////////                                button[2][0].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_10:
////////                            if (button[0][0].getText().equals("X") && button[2][0].getText().equals("")) {
////////                                button[2][0].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[0][0].getText().equals("") && button[2][0].getText().equals("X")) {
////////                                button[0][0].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                        case R.id.button_12:
////////                            if (button[0][2].getText().equals("X") && button[2][2].getText().equals("")) {
////////                                button[2][2].setText("O");
////////                                loop_break = 1;
////////                            } else if (button[0][2].getText().equals("") && button[2][2].getText().equals("X")) {
////////                                button[0][2].setText("O");
////////                                loop_break = 1;
////////                            }
////////                            break;
////////                    }
////////                }
//////
//////                    while (loop_break == 0) {
//////                        int random = new Random().nextInt(9);
//////                        int q = random / 3;
//////                        int r = random % 3;
//////                        if (button[q][r].getText().equals("")) {
//////                            button[q][r].setText("O");
//////                            loop_break = 1;
//////                        }
//////                    }
//////                    if (checkwin()) {
//////                        player2wins();
//////                        return;
//////                    }
//////                    playerturn = true;
////                }
//            }
//
//        } else {
//            if (playerturn) {
//                ((Button) view).setText("X");
//            } else {
//                ((Button) view).setText("O");
//            }
//            count++;
//            if (checkwin()) {
//                if (playerturn) {
//                    player1wins();
//                } else {
//                    player2wins();
//                }
//            } else if (count >= 9) {
//                draw();
//            } else {
//                playerturn = !playerturn;
//            }
//        }
//
//    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                Log.d("down","down");
                if (!((Button) view).getText().equals("")) {
                    error_sound.start();
                    return true;
                }

                if (selected_radio.equals(getString(R.string.single_player))) {
                    if (playerturn) {
                        loop_break = 0;
                        computer_win = false;
                        ((Button) view).setText("X");
                        btn_click_sound.start();
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                button[i][j].setEnabled(false);
                            }
                        }
                        count = count + 2;
                    }
                    if (checkwin()) {
                        player1wins();
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                button[i][j].setEnabled(true);
                            }
                        }
                        return true;
                    } else if (count >= 9) {
                        draw();
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                button[i][j].setEnabled(true);
                            }
                        }
                        return true;
                    } else {
                        delay_flag=true;
                    }

                } else {
                    if (playerturn) {
                        ((Button) view).setText("X");
                        btn_click_sound.start();
                    } else {
                        ((Button) view).setText("O");
                        btn_click_sound.start();
                    }
                    count++;
                    if (checkwin()) {
                        if (playerturn) {
                            player1wins();
                        } else {
                            player2wins();
                        }
                    } else if (count >= 9) {
                        draw();
                    } else {
                        playerturn = !playerturn;
                    }
                }
            }
            case MotionEvent.ACTION_UP:
                if (delay_flag){
                    delay_flag=false;
                    if (difficulty_level.equals("Hard")) {
                        if (count > 2) {
                            Log.d("player2","enter");
                            for (m = 0; m < 3; m++) {
                                for (n = 0; n < 3; n++) {
                                    if (button[m][n].getText().equals("")) {
                                        button[m][n].setText("O");
                                        if (checkwin()) {
                                            button[m][n].setText("");
                                            button[m][n].postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    button[m][n].setText("O");
                                                    btn_click_sound.start();
                                                    for (m = 0; m < 3; m++) {
                                                        for (n = 0; n < 3; n++) {
                                                            button[m][n].setEnabled(true);
                                                        }
                                                    }
                                                    player2wins();
                                                }
                                            },700);
                                            computer_win = true;
                                            loop_break = 1;
                                            return true;
                                        } else {
//                                        Log.d("2",i+" "+j+computer_win);
                                            button[m][n].setText("");
                                        }
                                    }
                                }
                            }
                            if (!computer_win) {
                                for (m = 0; m < 3; m++) {
                                    for (n = 0; n < 3; n++) {
                                        if (button[m][n].getText().equals("")) {
                                            button[m][n].setText("X");
                                            if (checkwin()) {
                                                button[m][n].setText("");
                                                button[m][n].postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        button[m][n].setText("O");
                                                        btn_click_sound.start();
                                                        for (m = 0; m < 3; m++) {
                                                            for (n = 0; n < 3; n++) {
                                                                button[m][n].setEnabled(true);
                                                            }
                                                        }
                                                    }
                                                },700);
                                                loop_break = 1;
                                                return true;
                                            } else {
                                                button[m][n].setText("");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

//                if (difficulty_level.equals("Level 2") && loop_break == 0) {
//                    switch (((Button) view).getId()) {
//                        case R.id.button_11:
//                            if (button[0][0].getText().equals("X") && button[2][2].getText().equals("")) {
//                                button[2][2].setText("O");
//                                loop_break = 1;
//                            } else if (button[2][2].getText().equals("X") && button[0][0].getText().equals("")) {
//                                button[0][0].setText("O");
//                                loop_break = 1;
//                            } else if (button[0][2].getText().equals("X") && button[2][0].getText().equals("")) {
//                                button[2][0].setText("O");
//                                loop_break = 1;
//                            } else if (button[2][0].getText().equals("X") && button[0][2].getText().equals("")) {
//                                button[0][2].setText("O");
//                                loop_break = 1;
//                            } else if (button[0][1].getText().equals("X") && button[2][1].getText().equals("")) {
//                                button[2][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[2][1].getText().equals("X") && button[0][1].getText().equals("")) {
//                                button[0][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][0].getText().equals("X") && button[1][2].getText().equals("")) {
//                                button[1][2].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][0].getText().equals("") && button[1][2].getText().equals("X")) {
//                                button[1][0].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_00:
//                            if (button[1][1].getText().equals("") && button[2][2].getText().equals("X")) {
//                                button[1][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[0][1].getText().equals("") && button[0][2].getText().equals("X")) {
//                                button[0][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][0].getText().equals("") && button[2][0].getText().equals("X")) {
//                                button[1][0].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_02:
//                            if (button[0][1].getText().equals("") && button[0][0].getText().equals("X")) {
//                                button[0][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][1].getText().equals("") && button[2][0].getText().equals("X")) {
//                                button[1][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][2].getText().equals("") && button[2][2].getText().equals("X")) {
//                                button[1][2].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_20:
//                            if (button[2][1].getText().equals("") && button[2][2].getText().equals("X")) {
//                                button[2][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][1].getText().equals("") && button[0][2].getText().equals("X")) {
//                                button[1][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][0].getText().equals("") && button[0][0].getText().equals("X")) {
//                                button[1][0].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_22:
//                            if (button[2][1].getText().equals("") && button[2][0].getText().equals("X")) {
//                                button[2][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][1].getText().equals("") && button[0][0].getText().equals("X")) {
//                                button[1][1].setText("O");
//                                loop_break = 1;
//                            } else if (button[1][2].getText().equals("") && button[0][2].getText().equals("X")) {
//                                button[1][2].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_01:
//                            if (button[0][0].getText().equals("X") && button[0][2].getText().equals("")) {
//                                button[0][2].setText("O");
//                                loop_break = 1;
//                            } else if (button[0][0].getText().equals("") && button[0][2].getText().equals("X")) {
//                                button[0][0].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_21:
//                            if (button[2][0].getText().equals("X") && button[2][2].getText().equals("")) {
//                                button[2][2].setText("O");
//                                loop_break = 1;
//                            } else if (button[2][0].getText().equals("") && button[2][2].getText().equals("X")) {
//                                button[2][0].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_10:
//                            if (button[0][0].getText().equals("X") && button[2][0].getText().equals("")) {
//                                button[2][0].setText("O");
//                                loop_break = 1;
//                            } else if (button[0][0].getText().equals("") && button[2][0].getText().equals("X")) {
//                                button[0][0].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                        case R.id.button_12:
//                            if (button[0][2].getText().equals("X") && button[2][2].getText().equals("")) {
//                                button[2][2].setText("O");
//                                loop_break = 1;
//                            } else if (button[0][2].getText().equals("") && button[2][2].getText().equals("X")) {
//                                button[0][2].setText("O");
//                                loop_break = 1;
//                            }
//                            break;
//                    }
//                }
                    while (loop_break == 0) {
                        int random = new Random().nextInt(9);
                        q = random / 3;
                        r = random % 3;
                        if (button[q][r].getText().equals("")) {
                            button[q][r].postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    button[q][r].setText("O");
                                    btn_click_sound.start();
                                    for (int i = 0; i < 3; i++) {
                                        for (int j = 0; j < 3; j++) {
                                            button[i][j].setEnabled(true);
                                        }
                                    }
                                    if (checkwin()) {
                                        player2wins();
                                        return;
                                    }
                                }
                            }, 700);

                            loop_break = 1;
                        }
                    }
                    playerturn = true;
                    Log.d("up",delay_flag+"");
                }
                break;
        }
        return true;
    }


    private void draw() {
        toast.setText("It's a tie!");
        dialog.show();
    }

    private void player2wins() {
        pts2++;
        if (selected_radio.equals(getString(R.string.single_player))){
            toast.setText("Android (O) Won!!");
        }
        else {
            toast.setText("Player 2(O) Won!!");
        }
        dialog.show();
        Updatepoints();
    }

    private void player1wins() {
        pts1++;
        toast.setText("Player 1(X) Won!!");
        dialog.show();
        Updatepoints();
    }

    private void Updatepoints() {
        player1.setText("Player 1(X): " + pts1);
        if (selected_radio.equals(getString(R.string.single_player))){
            player2.setText("Android (O): "+pts2);
        }
        else {
            player2.setText("Player 2(O): " + pts2);
        }
    }

    private boolean checkwin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = button[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && (!field[i][0].equals(""))) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && (!field[0][i].equals(""))) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && (!field[0][0].equals(""))) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && (!field[0][2].equals(""))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("count", count);
        outState.putInt("pts1", pts1);
        outState.putInt("pts2", pts2);
        outState.putBoolean("playerturn", playerturn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        count = savedInstanceState.getInt("count");
        pts1 = savedInstanceState.getInt("pts1");
        pts2 = savedInstanceState.getInt("pts2");
        playerturn = savedInstanceState.getBoolean("playerturn");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset: {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        button[i][j].setClickable(true);
                        button[i][j].setText("");
                    }
                }
                pts2 = pts1 = 0;
                player1.setText("Player 1(X): " + pts1);
                if (selected_radio.equals(getString(R.string.single_player))){
                    player2.setText("Android (O): "+pts2);
                }
                else {
                    player2.setText("Player 2(O): " + pts2);
                }

                count = 0;
                playerturn = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
