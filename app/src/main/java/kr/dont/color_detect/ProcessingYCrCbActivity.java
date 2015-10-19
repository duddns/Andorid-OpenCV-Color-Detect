package kr.dont.color_detect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class ProcessingYCrCbActivity extends AppCompatActivity {
    private final String _TAG = "ProcessingGRAYActivity:";

    private ImageView mSrcImageView;
    private ImageView mDestImageView;

    private SeekBar mSeekBarLowY;
    private SeekBar mSeekBarHighY;
    private SeekBar mSeekBarLowCr;
    private SeekBar mSeekBarHighCr;
    private SeekBar mSeekBarLowCb;
    private SeekBar mSeekBarHighCb;

    private TextView mTextViewLowY;
    private TextView mTextViewHighY;
    private TextView mTextViewLowCr;
    private TextView mTextViewHighCr;
    private TextView mTextViewLowCb;
    private TextView mTextViewHighCb;

    private int mLowY = 0;
    private int mHighY = 255;
    private int mLowCr = 0;
    private int mHighCr = 255;
    private int mLowCb = 0;
    private int mHighCb = 255;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_ycrcb);

        mSrcImageView = (ImageView) findViewById(R.id.srcImageView);
        mDestImageView = (ImageView) findViewById(R.id.destImageView);

        initSeekBar();
    }

    private void initSeekBar() {
        mTextViewLowY = (TextView) findViewById(R.id.textViewLowY);
        mTextViewHighY = (TextView) findViewById(R.id.textViewHighY);
        mTextViewLowCr = (TextView) findViewById(R.id.textViewLowCr);
        mTextViewHighCr = (TextView) findViewById(R.id.textViewHighCr);
        mTextViewLowCb = (TextView) findViewById(R.id.textViewLowCb);
        mTextViewHighCb = (TextView) findViewById(R.id.textViewHighCb);

        mSeekBarLowY = (SeekBar) findViewById(R.id.seekBarLowH);
        mSeekBarLowY.setMax(255);
        mSeekBarLowY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowY = i;

                mTextViewLowY.setText("Low Y: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighY = (SeekBar) findViewById(R.id.seekBarHighH);
        mSeekBarHighY.setMax(255);
        mSeekBarHighY.setProgress(255);
        mSeekBarHighY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighY = i;

                mTextViewHighY.setText("High Y: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });

        mSeekBarLowCr = (SeekBar) findViewById(R.id.seekBarLowS);
        mSeekBarLowCr.setMax(255);
        mSeekBarLowCr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowCr = i;

                mTextViewLowCr.setText("Low Cr: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighCr = (SeekBar) findViewById(R.id.seekBarHighS);
        mSeekBarHighCr.setMax(255);
        mSeekBarHighCr.setProgress(255);
        mSeekBarHighCr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighCr = i;

                mTextViewHighCr.setText("High Cr: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });

        mSeekBarLowCb = (SeekBar) findViewById(R.id.seekBarLowV);
        mSeekBarLowCb.setMax(255);
        mSeekBarLowCb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowCb = i;

                mTextViewLowCb.setText("Low Cb: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighCb = (SeekBar) findViewById(R.id.seekBarHighV);
        mSeekBarHighCb.setMax(255);
        mSeekBarHighCb.setProgress(255);
        mSeekBarHighCb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighCb = i;

                mTextViewHighCb.setText("High Cb: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        String TAG = new StringBuilder(_TAG).append("onResume").toString();
        if (!OpenCVLoader.initDebug()) {
            Log.i(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initiation");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, loaderCallback);
        } else {
            Log.i(TAG, "OpenCV library found inside package. Using it");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            String TAG = new StringBuilder(_TAG).append("onManagerConnected").toString();

            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");

                    process();
                    break;
                default:
                    super.onManagerConnected(status);
            }
        }
    };


    private void process() {
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.example);

        Bitmap destBitmap = ProcessUtils.processYCbCb(srcBitmap, mLowY, mHighY, mLowCr, mHighCr, mLowCb, mHighCb);

        mSrcImageView.setImageBitmap(srcBitmap);
        mDestImageView.setImageBitmap(destBitmap);
    }
}
