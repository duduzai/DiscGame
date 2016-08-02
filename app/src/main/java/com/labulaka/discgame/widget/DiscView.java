package com.labulaka.discgame.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.labulaka.discgame.R;

/**
 * Created by L on 16/7/28.
 */

public class DiscView extends View {
    private final String TAG = getClass().getSimpleName();
    private int screenHeight;
    private int screenWidth;
    private Paint[] arrPaintArc;
    private final int HORIZANTAL_VERTS = 1;
    private final int VERTICAL_VERTS = 1;
    private final int TOTAL_VERTS = (HORIZANTAL_VERTS + 1) * (VERTICAL_VERTS + 1);
    private float[] drawVerts = new float[TOTAL_VERTS*2];
    private long lastEmit;
    private boolean start = false;

    public DiscView(Context context) {
        super(context);
        init();
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
//        startAnimate();
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        screenWidth = dm.widthPixels;
//        screenHeight = dm.heightPixels;

        arrPaintArc = new Paint[arrColorRgb.length];
        for(int i=0;i<arrColorRgb.length;i++)
        {
            arrPaintArc[i] = new Paint();
            //arrPaintArc[i].setColor(res.getColor(colors[i] ));
            arrPaintArc[i].setARGB(255, arrColorRgb[i][0], arrColorRgb[i][1], arrColorRgb[i][2]);
            arrPaintArc[i].setStyle(Paint.Style.FILL);
            arrPaintArc[i].setStrokeWidth(4);
//            arrPaintArc[i].setMaskFilter(PaintBGBlur);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        screenHeight = getHeight();
        screenWidth = getWidth();
    }
    final float arrPer[] = new float[]{20f,30f,10f,40f};
    private final int arrColorRgb[][] = { {77, 83, 97},
            {148, 159, 181},
            {253, 180, 90},
            {52, 194, 188},
            {39, 51, 72},
            {255, 135, 195},
            {215, 124, 124},
            {180, 205, 230}} ;

    float rotateDegree = 0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        Bitmap fillBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.original);

        int width = fillBitmap.getWidth();
        float cirX = screenWidth/2;
        float cirY = screenHeight/2;
        float radius = width/2;
        canvas.rotate(rotateDegree,cirX,cirY);

        float arcLeft = cirX - radius;
        float arcTop  = cirY - radius ;
        float arcRight = cirX + radius ;
        float arcBottom = cirY + radius ;
        RectF arcRF0 = new RectF(arcLeft ,arcTop,arcRight,arcBottom);
        canvas.drawBitmap(fillBitmap,null,arcRF0,null);

        canvas.restore();
        if(start){
            invalidate();
        }
    }

    private void update() {
        if(lastEmit + 300 < System.currentTimeMillis()){
            rotateDegree += 30;
            lastEmit = System.currentTimeMillis();
        }
    }

    public void starteGame() {
        if (!start) {
            start = true;
            invalidate();
            startAnimate();
        }
    }

    private void startAnimate() {
        float randomDegree = (float) (Math.random()*360);
        float beginDegree = rotateDegree % 360;
        final ValueAnimator rotateOff = ValueAnimator.ofFloat(beginDegree, 360 * 20 + randomDegree).setDuration(5* 1000);
//        rotateOff.setRepeatCount(1);
        rotateOff.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotateDegree = (float) animation.getAnimatedValue();
            }
        });
        rotateOff.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                start = false;
                rotateOff.cancel();
                int part = (int) ((rotateDegree % 360 + 22.5) / 45);
                part = Math.abs(8 - part);
                if (part == 0) {
                    part = 8;
                }
                Toast.makeText(getContext(),""+part,Toast.LENGTH_SHORT).show();
            }
        });
        rotateOff.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateOff.start();
    }

}
