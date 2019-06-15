package com.example.apphelper.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * 一个音频正弦波view
 */
public class SongWave extends View {


    // 波长
    int width;
    //振幅
    int amplitude;

    private Paint paint;

    private double radian = 0;     //起始弧度为0
    private double interval = Math.PI / 12;      //每次的间隔为30°
    private int totalDivide = 36;//将正弦波分成几段，默认为36段

    private int[] startX = new int[totalDivide];
    private int[] endY = new int[totalDivide];

    int skip = 0;

    int padingTop = 20;//上下默认加上一个padding值，让波形的振动不会紧贴着view边界
    int halfHeight;
    private double startChange = 0;//让正弦波开始滚动
    private Random random;

    private Handler handler;
    private Runnable runnable;

    private boolean isRuning = false;

    public SongWave(Context context) {
        this(context, null);
    }

    public SongWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SongWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                setStartChange();
                handler.postDelayed(this, 50);
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        halfHeight = h / 2;
        amplitude = h / 2 - padingTop;

        skip = width / totalDivide;//减去两端的端点，一共分为36段
        Log.i("LHD", "view 的宽 = " + width + "  height = " + h + "   skip = " + skip);

        random = new Random();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < totalDivide; i++) {
            canvas.drawLine(startX[i], halfHeight - endY[i], startX[i], halfHeight + endY[i], paint);
        }
    }

    public void setStartChange() {
        startChange += Math.PI / 25;
        caclue();
        invalidate();
    }

    public void start() {
        isRuning = true;
        for (int i = 0; i < totalDivide; i++) {
            startX[i] = (i + 1) * skip;
            radian = interval * i + startChange;//当前的弧，这样计算出来的波是均匀变化的
//            int randomSkip = random.nextInt(12);
//            radian = interval * randomSkip;

            if (radian == 0 || radian == Math.PI) {
                radian = interval;//过滤到0的情况
            }

            Log.i("LHD", "当前的弧度 = " + radian);
            //计算振幅
            endY[i] = (int) Math.abs(amplitude * Math.sin(radian));
            Log.i("LHD", "LHD 计算的坐标 = x = " + startX[i] + "  y = " + endY[i]);
        }
        handler.post(runnable);
    }

    public void reset() {
        startChange = 0;
        isRuning = false;
        handler.removeCallbacksAndMessages(null);
        for (int i = 0; i < totalDivide; i++) {
            startX[i] = (i + 1) * skip;
            radian = interval * i + startChange;//当前的弧，这样计算出来的波是均匀变化的
//            int randomSkip = random.nextInt(12);
//            radian = interval * randomSkip;

            if (radian == 0 || radian == Math.PI) {
                radian = interval;//过滤到0的情况
            }

            Log.i("LHD", "当前的弧度 = " + radian);
            //计算振幅
            endY[i] = (int) Math.abs(amplitude * Math.sin(radian));
            Log.i("LHD", "LHD 计算的坐标 = x = " + startX[i] + "  y = " + endY[i]);
        }
        invalidate();
    }

    public boolean isRuning() {
        return isRuning;
    }

    private void caclue() {
        for (int i = 0; i < totalDivide; i++) {
            radian = interval * i + startChange;//当前的弧，这样计算出来的波是均匀变化的
            if (radian == 0 || radian == Math.PI) {
                radian = interval;//过滤到0的情况
            }

            Log.i("LHD", "当前的弧度 = " + radian);
            //计算振幅
            endY[i] = (int) Math.abs(amplitude * Math.sin(radian));
            Log.i("LHD", "LHD 计算的坐标 = x = " + startX[i] + "  y = " + endY[i]);
        }
    }

}
