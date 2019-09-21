package com.example.tictactoe;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] button=new Button[3][3];
    boolean playerturn=true;
    int count;
    int pts1;
    int pts2;
    TextView player1,player2,toast1,toast2,draww,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title=findViewById(R.id.title);
        title.setText("TicTacToe");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar=findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        player1=findViewById(R.id.player_1);
        player2=findViewById(R.id.player_2);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonID="button_"+i+j;
                int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
                button[i][j]=findViewById(resID);
                button[i][j].setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View view) {

        if(!((Button)view).getText().equals("")){
            return;
        }
        if(playerturn){
            ((Button) view).setText("X");
        }
        else {
            ((Button)view).setText("O");
        }
        count++;

        if(checkwin()){
            if(playerturn){
                player1wins();
            }
            else {
                player2wins();
            }
        }
        else if(count>=9){
            draw();
        }
        else {
            playerturn=!playerturn;
        }
    }

    private void draw() {
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.toast_item,null);
        draww=v.findViewById(R.id.toast);
        draww.setText("Draw");
        Toast toast=new Toast(this);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,500);
        toast.show();
        //Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setText("");
            }
        }
        count=0;
        playerturn=true;
    }

    private void player2wins() {
        pts2++;
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.toast_item,null);
        toast1=v.findViewById(R.id.toast);
        toast1.setText("Player 2 Wins");
        Toast toast=new Toast(this);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,500);
        toast.show();
//        Toast.makeText(this,"Player 2 Wins",Toast.LENGTH_SHORT).show();
        Updatepoints();

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setText("");
            }
        }
        playerturn=true;
        count=0;
    }

    private void player1wins() {
        pts1++;
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.toast_item,null);
        toast2=v.findViewById(R.id.toast);
        toast2.setText("Player 1 Wins");
        Toast toast=new Toast(this);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,500);
        toast.show();
        //Toast.makeText(this,"Player 1 Wins",Toast.LENGTH_SHORT).show();
        Updatepoints();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setText("");
            }
        }
        playerturn=true;
        count=0;

    }

    private void Updatepoints() {
        player1.setText("Player 1: "+pts1);
        player2.setText("Player 2: "+pts2);
    }

    private boolean checkwin(){
        String[][] field=new String[3][3];

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                field[i][j]=button[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&(!field[i][0].equals(""))){
                return true;
            }
        }

        for (int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&(!field[0][i].equals(""))){
                return true;
            }
        }

        for (int i=0;i<3;i++){
            if(field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&&(!field[0][0].equals(""))){
                return true;
            }
        }

        for (int i=0;i<3;i++){
            if(field[0][2].equals(field[1][1])&&field[0][2].equals(field[2][0])&&(!field[0][2].equals(""))){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("count",count);
        outState.putInt("pts1",pts1);
        outState.putInt("pts2",pts2);
        outState.putBoolean("playerturn",playerturn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        count=savedInstanceState.getInt("count");
        pts1=savedInstanceState.getInt("pts1");
        pts2=savedInstanceState.getInt("pts2");
        playerturn=savedInstanceState.getBoolean("playerturn");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.reset:{
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        button[i][j].setClickable(true);
                        button[i][j].setText("");
                    }
                }
                pts2=pts1=0;
                player1.setText("Player 1: "+pts1);
                player2.setText("Player 2: "+pts2);

                count=0;
                playerturn=true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
