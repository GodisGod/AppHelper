package com.example.apphelper.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.apphelper.utlis.ScreenUtils;

import java.util.List;
import java.util.Random;

/**
 * 一个音频正弦波view，固定拥有14条线
 */
public class SongWave2 extends View {


    // 波长
    int width;
    //振幅
    int amplitude;

    private Paint paint;
    private Paint paintCircle;

    private double radian = 0;     //起始弧度为0
    private double interval = Math.PI / 6;      //每次的间隔为30°
    private int totalDivide = 12;//将正弦波分成几段，默认为36段

    private int[] startX = new int[totalDivide];
    private int[] endY = new int[totalDivide];

    int skip = 0;

    int padingTop = 20;//上下默认加上一个padding值，让波形的振动不会紧贴着view边界
    int halfHeight;
    private double startChange = 0;//让正弦波开始滚动
    private int lineWidth = 6;
    private int radius = lineWidth / 2;

    private Handler handler;
    private Runnable runnable;

    private boolean isRuning = false;

    private int alpha = 25;
    private LinearGradient[] gradients = new LinearGradient[totalDivide];

    public SongWave2(Context context) {
        this(context, null);
    }

    public SongWave2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SongWave2(Context context, AttributeSet attrs, int defStyleAttr) {
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

        for (int i = 0; i < totalDivide; i++) {
            startX[i] = i * skip + lineWidth / 2;
            radian = interval * i + startChange;//当前的弧，这样计算出来的波是均匀变化的

            if (radian == 0) {
                radian = interval;//过滤到0的情况
            }

            Log.i("LHD", "当前的弧度 = " + radian);
            //计算振幅
            endY[i] = (int) Math.abs(amplitude * Math.sin(radian));
            Log.i("LHD", "LHD 计算的坐标 = x = " + startX[i] + "  y = " + endY[i]);

            LinearGradient gradient = new LinearGradient(startX[i], halfHeight - endY[i], startX[i], halfHeight + endY[i], new int[]{Color.parseColor("#17FFD5"),
                    Color.parseColor("#17FFD5")}, null, Shader.TileMode.CLAMP);
            gradients[i] = gradient;

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private void init() {
        paint = new Paint();
        //LinearGradient linearGradient = new LinearGradient(getWidth(),400,0,0,Color.RED,Color.GREEN, Shader.TileMode.CLAMP);

        paint.setColor(Color.parseColor("#17FFD5"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);

        paintCircle = new Paint();
        paintCircle.setColor(Color.parseColor("#17FFD5"));
        paintCircle.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < totalDivide; i++) {

            paint.setShader(gradients[i]);

            canvas.drawLine(startX[i], halfHeight - endY[i], startX[i], halfHeight + endY[i], paint);

            canvas.drawCircle(startX[i], halfHeight - endY[i], radius, paintCircle);
            canvas.drawCircle(startX[i], halfHeight + endY[i], radius, paintCircle);
            if (i == totalDivide / 2) {
                canvas.drawLine(startX[i], halfHeight - endY[i - 1], startX[i], halfHeight + endY[i - 1], paint);
                canvas.drawCircle(startX[i], halfHeight - endY[i - 1], radius, paintCircle);
                canvas.drawCircle(startX[i], halfHeight + endY[i - 1], radius, paintCircle);
            }
        }


    }

    public void setStartChange() {
        startChange += Math.PI / 25;//每次更新每条线的弧度变化Math.PI / 30，这样做可以起到渐变的效果，不会让变化显得很突兀

        for (int i = 0; i < totalDivide; i++) {
            radian = interval * i + 1 + startChange;//当前的弧，这样计算出来的波是均匀变化的

            if (radian == 0) {
                radian = interval;//过滤到0的情况
            }

            Log.i("LHD", "当前的弧度 = " + radian);
            //计算振幅
            endY[i] = (int) Math.abs(amplitude * Math.sin(radian));
            Log.i("LHD", "LHD 计算的坐标 = x = " + startX[i] + "  y = " + endY[i]);

            alpha -= 10;
            if (alpha <= 122) {
                alpha = 122;
            }

//            paint.setAlpha(alpha);
//            paintCircle.setAlpha(alpha);

        }

        invalidate();
    }

    public void start() {
        isRuning = true;
        handler.post(runnable);
    }

    public void reset() {
        startChange = 0;
        isRuning = false;
        alpha = 255;
        paint.setAlpha(alpha);
        paintCircle.setAlpha(alpha);
        handler.removeCallbacksAndMessages(null);

        for (int i = 0; i < totalDivide; i++) {
            startX[i] = i * skip + lineWidth / 2;
            radian = interval * i + startChange;//当前的弧，这样计算出来的波是均匀变化的

            if (radian == 0) {
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

}
