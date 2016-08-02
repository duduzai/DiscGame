package com.labulaka.discgame.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.labulaka.discgame.R;

/**
 * Created by L on 16/8/1.
 */

public class DiscViewGroup extends RelativeLayout {
    public DiscViewGroup(Context context) {
        super(context);
    }

    public DiscViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final DiscView discView = new DiscView(getContext());
        addView(discView);

        ImageView zhiZhen = new ImageView(getContext());
        zhiZhen.setBackgroundResource(R.mipmap.zhizhen);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        addView(zhiZhen,layoutParams);

        zhiZhen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                discView.starteGame();
            }
        });
    }
}
