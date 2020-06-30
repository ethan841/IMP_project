package com.example.imp_snakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.imp_snakegame.Led.LedJNI;
import com.example.imp_snakegame.Piazo.PiazoJNI;
import com.example.imp_snakegame.SegmentJNI.SegmentJNI;
import com.example.imp_snakegame.TextLcd.TextLcdJNI;
import com.example.imp_snakegame.engine.GameEngine;
import com.example.imp_snakegame.enums.Direction;
import com.example.imp_snakegame.enums.GameState;
import com.example.imp_snakegame.views.SnakeView;
import com.example.imp_snakegame.enums.Piazo;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 100;
    private final long segmentDelay = 1;

    private float prevX, prevY;
    private int score;
    private int length;
    private int tick;
    private int temp;

    String length_T = "Length = ";
    String score_T = "Score = ";

    private LedJNI ledJNI = new LedJNI();
    public static PiazoJNI piazoJNI = new PiazoJNI();
    private TextLcdJNI textLcdJNI = new TextLcdJNI();
    private SegmentJNI segmentJNI = new SegmentJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = 0;
        length = 6;
        tick = 0;
        temp = 0;
        ledJNI.on((char)0b00000000);
        piazoJNI.write(20);
        textLcdJNI.on();
        textLcdJNI.clear();
        segmentJNI.open();

        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = (SnakeView)findViewById(R.id.Snakeview);
        snakeView.setOnTouchListener(this);

        startUpdateHandler();
        segmentHandler();
    }

    private void segmentHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                segmentJNI.print(temp);
                handler.postDelayed(this, segmentDelay);
            }
        }, segmentDelay);
    }

    private void startUpdateHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                //textLcdJNI.on();
                if(gameEngine.getCurrentGameState() == GameState.Runnung){
                    tick++;
                    handler.postDelayed(this, updateDelay);
                }

                if(gameEngine.getCurrentGameState() == GameState.Lost){
                    try {
                        textLcdJNI.clear();
                        textLcdJNI.print1Line("Game Over.");
                        ledJNI.on((char) 0b11111111);
                        piazoJNI.write(20);
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                    piazoJNI.write(0);
                    OnGameLost();
                }

                if(gameEngine.getCurrentPiazo() == Piazo.ON){
                    score++;
                    length++;
                    ledJNI.on((char)score);
                    piazoJNI.write(17);
                    textLcdJNI.print1Line(length_T + length);
                    textLcdJNI.print2Line(score_T + score);
                }
                else{
                    piazoJNI.write(0);
                    ledJNI.on((char)0b00000000);
                    textLcdJNI.print1Line(length_T + length);
                    textLcdJNI.print2Line(score_T + score);
                }
                if(tick % 10 == 0)
                    temp++;
                segmentJNI.print(temp);
                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();
            }
        }, updateDelay);
    }

    private void OnGameLost(){
        Toast.makeText(this, "You Lose.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();
                //어디로 스와이프?
                if(Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    //왼쪽오른쪽
                    if(newX > prevX){
                        //오른쪽
                        gameEngine.UpdateDirection(Direction.East);
                    }else{
                        //왼쪽
                        gameEngine.UpdateDirection(Direction.West);
                    }
                }
                else{
                    //위아래방향
                    if(newY > prevY){
                        //위쪽
                        gameEngine.UpdateDirection(Direction.South);
                    }else{
                        //아래쪽
                        gameEngine.UpdateDirection(Direction.North);
                    }
                }

                break;
        }

        return true;
    }
}