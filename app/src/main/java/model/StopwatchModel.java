/**
 *  Purpose/Timer This model is all timer setting methods. It can be used in any view.
 *  @author Jinhong Fan (fan00036@algonquinlive.com)
 */


package model;

import java.text.NumberFormat;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by helen on 2015-11-17.
 */
public class StopwatchModel extends Observable{

    private static final NumberFormat NF;

    private int  mHours;
    private boolean mIsRunning;
    private int mMinutes;
    private int mSeconds;
    private int mTenthOfASecond;
    private TimerTask mTimerTask;
    private Timer mTimer;

    static {
        NF = NumberFormat.getInstance();
        NF.setMaximumIntegerDigits(2);
        NF.setMinimumIntegerDigits(2);
    }

    ///no-argument constructor
    public StopwatchModel(){ this(0,0,0,0);

    }


    public int getHours() { return mHours; }
    public int getMinutes() { return mMinutes; }
    public int getSeconds() { return mSeconds; }
    public int getTenthOfASecond() { return mTenthOfASecond; }

    public StopwatchModel(int hours, int minutes, int seconds, int tenthOfASecond){
        super();

        this.mHours = hours;
        this.mMinutes= minutes;
        this.mSeconds=seconds;
        this.mTenthOfASecond=tenthOfASecond;

        mIsRunning = false;

        mTimer =  new Timer();
    }


    public boolean isRunning() {
        return mIsRunning;
    }

    public void reset(){
        mHours = mMinutes = mSeconds = mTenthOfASecond =0;

        this.updateObservers();
    }

    public  void start(){
        if( this.isRunning()==false){
            mTimerTask = new StopwatchTask();
            mTimer.scheduleAtFixedRate( mTimerTask, 0L, 100L);
            mIsRunning = true;

        }

        this.updateObservers();
    }

    public void stop(){
        if(this.isRunning() == true){
            mTimerTask.cancel();
            mIsRunning = false;
        }

        this.updateObservers();
    }


    public String toString(){
        return(NF.format(mHours)
                + ":" + NF.format(mMinutes)
                + ":" + NF.format(mSeconds)
                + ":" + mTenthOfASecond
        );
    }


    private void updateObservers(){
        this.setChanged();
        this.notifyObservers();
    }

    private class StopwatchTask extends TimerTask{

        public void run(){
            mTenthOfASecond++;
            if(mTenthOfASecond==10){
                mTenthOfASecond=0;
                mSeconds++;

                if(mSeconds >= 60) {
                    mMinutes = 0;
                    mHours++;

                }
            }
            updateObservers();
        }

    }

    public static  void main( String[] args) {
        StopwatchModel stopwatch;
        stopwatch = new StopwatchModel();

        System.out.println("starting the stopwatch...\n");
        stopwatch.start();

        System.out.println("sleeping for 10 mSeconds...\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        System.out.println("stopping the stopwatch...\n");
        stopwatch.stop();
        System.out.println(stopwatch);
        System.out.println();

        System.exit(0);



    }





}
