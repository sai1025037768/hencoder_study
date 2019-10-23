package org.android.study.materialedittext.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import org.android.study.materialedittext.R;
import org.android.study.materialedittext.Utils;

/**
 * @author Administrator
 * @name [MaterialEditText]
 * @time 2019/10/23 9:15
 * @describe
 */
public class MaterialEditText extends AppCompatEditText {

    private static final float TEXT_SIZE = Utils.dp2px(12);
    private static final float TEXT_MARGIN = Utils.dp2px(8);
    private static final float VERTICAL_OFFSET = Utils.dp2px(38);
    private static final float HORIZONTAL_OFFSET = Utils.dp2px(5);
    private static final float EXTRA_OFFSET = Utils.dp2px(16);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect backgroundPaddings = new Rect();
    boolean floatingLabelShown;
    float floatingLabelFraction;
    boolean useFloatingLabel = true;
    ObjectAnimator animator;

    public MaterialEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true);
        typedArray.recycle();
        paint.setTextSize(TEXT_SIZE);
        getBackground().getPadding(backgroundPaddings);
        if(useFloatingLabel){
            setPadding(backgroundPaddings.left, (int) (backgroundPaddings.top + TEXT_SIZE + TEXT_MARGIN),
                    backgroundPaddings.right, backgroundPaddings.bottom);

            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!floatingLabelShown && !TextUtils.isEmpty(s)){
                        floatingLabelShown = true;
                        getAnimator().start();
                    }else if(floatingLabelShown && TextUtils.isEmpty(s)) {
                        floatingLabelShown = false;
                        getAnimator().reverse();
                    }
                }
            });
        }

    }

    private ObjectAnimator getAnimator() {
        if(animator == null){
            animator = ObjectAnimator.ofFloat(this,"floatingLabelFraction", 1);
        }
        return animator;
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if(this.useFloatingLabel != useFloatingLabel){
            this.useFloatingLabel = useFloatingLabel;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(useFloatingLabel) {
            paint.setAlpha((int) (floatingLabelFraction * 0xff));
            float extraOffset = -EXTRA_OFFSET * floatingLabelFraction;
            canvas.drawText(getHint().toString(), HORIZONTAL_OFFSET, VERTICAL_OFFSET + extraOffset, paint);
        }

    }
}
