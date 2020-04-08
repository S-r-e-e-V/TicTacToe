package com.puzzlegame.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class first extends AppCompatActivity {

    Button start,option,ok,exit;
    TextView heading;
    boolean android=false;
    RadioButton player;
    RadioGroup radioGroup;
    Spinner difficulty;
    EditText player_1_name,player_2_name;
    int selected_Id;
    String selected_radio;
    String difficulty_level="Easy",player_1,player_2;
    MediaPlayer btn_click_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        heading=findViewById(R.id.heading);
        Shader textShader=new LinearGradient(0, 0, 0, 145,
                new int[]{getResources().getColor(R.color.first_xml_heading_2),getResources().getColor(R.color.first_xml_heading_1)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        heading.getPaint().setShader(textShader);

        selected_radio=getString(R.string.single_player);
        exit=findViewById(R.id.exit);
        start=findViewById(R.id.start);
        start.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_click_color_first_xml));
        option=findViewById(R.id.option);
        option.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_click_color_first_xml));

        btn_click_sound=MediaPlayer.create(this,R.raw.btn_click);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_click_color_first_xml));
                btn_click_sound.start();
                Intent intent=new Intent(first.this,MainActivity.class);
                intent.putExtra("android",selected_radio);
//                Toast.makeText(getApplicationContext(),selected_radio+ " "+ difficulty_level,Toast.LENGTH_SHORT).show();
                if (selected_radio.equals(getString(R.string.single_player))){
                    intent.putExtra("level",difficulty_level);
//                    intent.putExtra("player_1",player_1);
                }
//                else if (selected_radio.equals("Two player")){
////                    intent.putExtra("player_1",player_1);
////                    intent.putExtra("player_2",player_2);
////              }
                startActivity(intent);
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_click_color_first_xml));
                btn_click_sound.start();
                show_Dialog();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
            }
        });
    }

    private void show_Dialog() {
        final Dialog dialog = new Dialog(first.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.option_dialog);
        radioGroup=dialog.findViewById(R.id.radio_gp);
//        two_player=view.findViewById(R.id.two_player);
        difficulty=dialog.findViewById(R.id.difficulty);


//        player_1_name=dialog.findViewById(R.id.player_1_name);
//        player_2_name=dialog.findViewById(R.id.player_2_name);
        ok=dialog.findViewById(R.id.ok);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Easy");
        arrayList.add("Hard");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_checked, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_checked);
        difficulty.setAdapter(arrayAdapter);

        if (!selected_radio.equals(getString(R.string.single_player))){
            player=dialog.findViewById(R.id.two_player);
            difficulty.setEnabled(false);
            player.setChecked(true);
        }

        switch (difficulty_level) {
            case "Easy":
                difficulty.setSelection(arrayAdapter.getPosition("Easy"));
                break;
            case "Hard":
                difficulty.setSelection(arrayAdapter.getPosition("Hard"));
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selected_Id=checkedId;
                player=dialog.findViewById(checkedId);
                selected_radio=player.getText().toString();
//               Toast.makeText(getApplicationContext(),selected_radio,Toast.LENGTH_SHORT).show();
                if (selected_radio.equals(getString(R.string.single_player))){
//                    player_1_name.setEnabled(true);
//                    player_2_name.setEnabled(false);
                    difficulty.setEnabled(true);
                }
                else if (selected_radio.equals(getString(R.string.multi_player))){
//                    player_1_name.setEnabled(true);
//                    player_2_name.setEnabled(true);
                    difficulty.setEnabled(false);
                }
            }
        });
        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficulty_level=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                difficulty_level="Easy";
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                player_1=player_1_name.getText().toString();
//                player_2=player_2_name.getText().toString();
                dialog.dismiss();
            }
        });
    }
}
