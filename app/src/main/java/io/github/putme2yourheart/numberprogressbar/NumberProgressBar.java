package io.github.putme2yourheart.numberprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by Frank on 2017/11/6.
 * number progress bar
 */

public class NumberProgressBar extends View {
    // attrs
    // progress text size
    private float mProgressTextSize;
    // text color
    private int mProgressTextColor;
    // progress bar height
    private int mProgressBarHeight;
    // text
    private boolean mTextVisible;
    // current progress
    private float mProgress;
    // max progress
    private float mMaxProgress;
    // reach color
    private int mReachColor;
    // unreached color
    private int mUnreachedColor;

    // reach paint
    private Paint mProgressUnreachedPaint;
    // unreached paint
    private Paint mProgressReachPaint;
    // text paint
    private Paint mTextPaint;
    private Paint mTextPaintWhite;
    // text background paint
    private Paint mTextBackgroundPaint;
    // view height
    private int mHeight;
    // view width
    private int mWidth;
    // measure text size rect
    private Rect mTextRect = new Rect();

    private Context mContext;
    // progress text format
    private DecimalFormat df = new DecimalFormat("0.0");

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        //read custom attrs
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.NumberProgressBar, 0, 0);

        mProgress = t.getFloat(R.styleable.NumberProgressBar_npb_progress_current, 0f);
        mMaxProgress = t.getFloat(R.styleable.NumberProgressBar_npb_progress_max, 100f);
        mProgressBarHeight = t.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_progress_bar_height,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        mReachColor = t.getColor(R.styleable.NumberProgressBar_npb_reach_color, Color.parseColor("#27AE60"));
        mUnreachedColor = t.getColor(R.styleable.NumberProgressBar_npb_unreached_color, Color.parseColor("#CDCDCD"));
        mTextVisible = t.getBoolean(R.styleable.NumberProgressBar_npb_text_visible, true);
        mProgressTextColor = t.getColor(R.styleable.NumberProgressBar_npb_text_color, Color.parseColor("#27AE60"));
        mProgressTextSize = t.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_text_size,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        // we should always recycle after used
        t.recycle();

        mTextPaint = new Paint();
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setAntiAlias(true);

        mTextPaintWhite = new Paint();
        mTextPaintWhite.setColor(Color.WHITE);
        mTextPaintWhite.setAntiAlias(true);

        mTextBackgroundPaint = new Paint();
        mTextBackgroundPaint.setColor(Color.WHITE);
        mTextBackgroundPaint.setAntiAlias(true);

        // set reach paint
        mProgressReachPaint = new Paint();
        mProgressReachPaint.setColor(mReachColor);
        mProgressReachPaint.setAntiAlias(true);

        // set unreached paint
        mProgressUnreachedPaint = new Paint();
        mProgressUnreachedPaint.setColor(mUnreachedColor);
        mProgressUnreachedPaint.setAntiAlias(true);

        // measure text
        if (mTextVisible) {
            String percent = String.valueOf(df.format(mProgress * 100.0 / mMaxProgress) + "%");
            mTextPaint.setTextSize(mProgressTextSize);
            mTextPaintWhite.setTextSize(mProgressTextSize);
            mTextPaint.getTextBounds(percent, 0, percent.length(), mTextRect);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // the exact value of the width
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // the exact value of the height
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // height measurement mode
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                // view minion height
                mHeight = Math.max(mTextRect.height(), mProgressBarHeight) + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                // view minion height
                mHeight = Math.max(heightSize, Math.max(mTextRect.height(), mProgressBarHeight)) + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.UNSPECIFIED:
                mHeight = heightSize - getPaddingTop() - getPaddingBottom();
                mProgressBarHeight = mHeight;
                break;
        }

        // default width
        mWidth = widthSize - getPaddingLeft() - getPaddingRight();

        // set the width and height
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // progress length
        float current_progress = mProgress / mMaxProgress * mWidth;
        current_progress = Math.min(current_progress, mWidth);

        // draw progress
        if (mProgressBarHeight < mHeight) {
            canvas.drawRect(0, (mHeight - mProgressBarHeight) / 2, current_progress,
                    (mHeight - mProgressBarHeight) / 2 + mProgressBarHeight, mProgressReachPaint);
            canvas.drawRect(current_progress, (mHeight - mProgressBarHeight) / 2, mWidth,
                    (mHeight - mProgressBarHeight) / 2 + mProgressBarHeight, mProgressUnreachedPaint);
        } else {
            canvas.drawRect(0, 0, current_progress, mProgressBarHeight, mProgressReachPaint);
            canvas.drawRect(current_progress, 0, mWidth, mProgressBarHeight, mProgressUnreachedPaint);
        }

        // draw text
        if (mTextVisible) {
            String percent = String.valueOf(df.format(mProgress * 100.0 / mMaxProgress) + "%");
            float width;
            float height;

            // percent text height > progress bar height
            if (mProgressBarHeight < mHeight) {
                mTextPaint.getTextBounds(percent, 0, percent.length(), mTextRect);

                width = mTextRect.width();
                height = mTextRect.height();

                if (current_progress + width < mWidth) {
                    canvas.drawRect(current_progress, 0, current_progress + width, mHeight, mTextBackgroundPaint);
                    canvas.drawText(percent, -mTextRect.left + current_progress, mHeight, mTextPaint);
                } else {
                    canvas.drawRect(mWidth - width, 0, mWidth, mHeight, mTextBackgroundPaint);
                    canvas.drawText(percent, -mTextRect.left + mWidth - width, mHeight, mTextPaint);
                }
            } else {
                mTextPaintWhite.getTextBounds(percent, 0, percent.length(), mTextRect);

                width = mTextRect.width();
                height = mTextRect.height();

                // text padding left or right
                float padding = dip2px(mContext, 8f);
                if (current_progress < width + padding * 2) {
                    canvas.drawText(percent, -mTextRect.left + current_progress + padding,
                            height < mHeight ? (mHeight - height) / 2 + height : height, mTextPaintWhite);
                } else {
                    canvas.drawText(percent, current_progress - mTextRect.right - padding,
                            height < mHeight ? (mHeight - height) / 2 + height : height, mTextPaintWhite);
                }
            }
        }
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * set progress with animation
     *
     * @param progress progress
     * @param millis   time(ms)
     */
    public void setProgressWithAnimation(int progress, long millis) {
        millis = millis < 1000 ? 1000 : millis;
        final long interval;
        final float tProgress;
        final float ratio;
        // divided into 1000 part，every part of the time
        if (progress < mMaxProgress) {
            ratio = 1;
            interval = (long) (millis / (progress * 1000.0 / mMaxProgress));
            tProgress = progress * 100;
        } else {
            // divided into 1000/ratio part
            ratio = progress / mMaxProgress;
            interval = (long) (millis / (progress * 1000.0 / ratio / mMaxProgress - progress % mMaxProgress));
            tProgress = progress * 100 / ratio;
        }
        new Thread() {
            @Override
            public void run() {
                float unit = mMaxProgress / 10f;

                for (float i = 0; i <= tProgress; i += unit) {
                    mProgress = i / 100 * ratio;
                    postInvalidate();
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public float getProgressTextSize() {
        return mProgressTextSize;
    }

    public void setProgressTextSize(float progressTextSize) {
        mProgressTextSize = progressTextSize;
        invalidate();
    }

    public int getProgressTextColor() {
        return mProgressTextColor;
    }

    public void setProgressTextColor(int progressTextColor) {
        mProgressTextColor = progressTextColor;
        mTextPaintWhite.setColor(mProgressTextColor);
        mTextPaint.setColor(mProgressTextColor);
        invalidate();
    }

    public boolean isTextVisible() {
        return mTextVisible;
    }

    public void setTextVisible(boolean textVisible) {
        mTextVisible = textVisible;
        invalidate();
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        mMaxProgress = maxProgress;
        invalidate();
    }

    public int getReachColor() {
        return mReachColor;
    }

    public void setReachColor(int reachColor) {
        mReachColor = reachColor;
        mProgressReachPaint.setColor(mReachColor);
        invalidate();
    }

    public int getUnreachedColor() {
        return mUnreachedColor;
    }

    public void setUnreachedColor(int unreachedColor) {
        mUnreachedColor = unreachedColor;
        mProgressUnreachedPaint.setColor(mUnreachedColor);
        invalidate();
    }
}
