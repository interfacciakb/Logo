package com.example.logo.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.logo.R;

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    // Default Values
    private static final float DEFAULT_BORDER_WIDTH = 4;
    private static final float DEFAULT_SHADOW_RADIUS = 8.0f;

    // Properties
    private float borderWidth;
    private int canvasSize;
    private float shadowRadius;
    private int shadowColor = Color.BLACK;

    // Object used to draw
    private Bitmap image;
    private Drawable drawable;
    private Paint paint;
    private Paint paintBorder;

    //region Constructor & Init Method
    public CircleImageView(final Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        // Init paint
        paint = new Paint();
        paint.setAntiAlias(true);

        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);

        // Load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);

        // Init Border
        if (attributes.getBoolean(R.styleable.CircleImageView_civ_border, true)) {
            float defaultBorderSize = DEFAULT_BORDER_WIDTH * getContext().getResources().getDisplayMetrics().density;
            setBorderWidth(attributes.getDimension(R.styleable.CircleImageView_civ_border_width, defaultBorderSize));
            setBorderColor(attributes.getColor(R.styleable.CircleImageView_civ_border_color, Color.WHITE));
        }

        // Init Shadow
        if (attributes.getBoolean(R.styleable.CircleImageView_civ_shadow, false)) {
            shadowRadius = DEFAULT_SHADOW_RADIUS;
            drawShadow(attributes.getFloat(R.styleable.CircleImageView_civ_shadow_radius, shadowRadius), attributes.getColor(R.styleable.CircleImageView_civ_shadow_color, shadowColor));
        }
    }
    //endregion

    //region Set Attr Method
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        requestLayout();
        invalidate();
    }

    public void setBorderColor(int borderColor) {
        if (paintBorder != null)
            paintBorder.setColor(borderColor);
        invalidate();
    }

    public void addShadow() {
        if (shadowRadius == 0)
            shadowRadius = DEFAULT_SHADOW_RADIUS;
        drawShadow(shadowRadius, shadowColor);
        invalidate();
    }

    public void setShadowRadius(float shadowRadius) {
        drawShadow(shadowRadius, shadowColor);
        invalidate();
    }

    public void setShadowColor(int shadowColor) {
        drawShadow(shadowRadius, shadowColor);
        invalidate();
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported. ScaleType.CENTER_CROP is used by default. So you don't need to use ScaleType.", scaleType));
        }
    }
    //endregion

    //region Draw Method
    @Override
    public void onDraw(Canvas canvas) {
        // Load the bitmap
        loadBitmap();

        // Check if image isn't null
        if (image == null)
            return;

        if (!isInEditMode()) {
            canvasSize = canvas.getWidth();
            if (canvas.getHeight() < canvasSize) {
                canvasSize = canvas.getHeight();
            }
        }


        int circleCenter = (int) (canvasSize - (borderWidth * 2)) / 2;
        canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - (shadowRadius + shadowRadius / 2), paintBorder);
        canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - (shadowRadius + shadowRadius / 2), paint);
    }

    private void loadBitmap() {
        if (this.drawable == getDrawable())
            return;

        this.drawable = getDrawable();
        this.image = drawableToBitmap(this.drawable);
        updateShader();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasSize = w;
        if (h < canvasSize)
            canvasSize = h;
        if (image != null)
            updateShader();
    }

    private void drawShadow(float shadowRadius, int shadowColor) {
        this.shadowRadius = shadowRadius;
        this.shadowColor = shadowColor;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        }
        paintBorder.setShadowLayer(shadowRadius, 0.0f, shadowRadius / 2, shadowColor);
    }

    private void updateShader() {
        if (image == null)
            return;


        image = cropBitmap(image);
        BitmapShader shader = new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setScale((float) canvasSize / (float) image.getWidth(), (float) canvasSize / (float) image.getHeight());
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }

    private Bitmap cropBitmap(Bitmap bitmap) {
        Bitmap bmp;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            bmp = Bitmap.createBitmap(
                    bitmap,
                    bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                    0,
                    bitmap.getHeight(), bitmap.getHeight());
        } else {
            bmp = Bitmap.createBitmap(
                    bitmap,
                    0,
                    bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                    bitmap.getWidth(), bitmap.getWidth());
        }
        return bmp;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();

        if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
            return null;

        try {

            Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {

            Log.e(getClass().toString(), "Encountered OutOfMemoryError while generating bitmap!");
            return null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {

            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {

            result = specSize;
        } else {

            result = canvasSize;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {

            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {

            result = specSize;
        } else {

            result = canvasSize;
        }

        return (result + 2);
    }

}

