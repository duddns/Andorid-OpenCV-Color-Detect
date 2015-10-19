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

public class ProcessingHSVActivity extends AppCompatActivity {

    private final String _TAG = "ProcessingGRAYActivity:";

    private ImageView mSrcImageView;
    private ImageView mDestImageView;

    private SeekBar mSeekBarLowH;
    private SeekBar mSeekBarHighH;
    private SeekBar mSeekBarLowS;
    private SeekBar mSeekBarHighS;
    private SeekBar mSeekBarLowV;
    private SeekBar mSeekBarHighV;

    private TextView mTextViewLowH;
    private TextView mTextViewHighH;
    private TextView mTextViewLowS;
    private TextView mTextViewHighS;
    private TextView mTextViewLowV;
    private TextView mTextViewHighV;

    private int mLowH = 0;
    private int mHighH = 179;
    private int mLowS = 0;
    private int mHighS = 255;
    private int mLowV = 0;
    private int mHighV = 255;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_hsv);

        mSrcImageView = (ImageView) findViewById(R.id.srcImageView);
        mDestImageView = (ImageView) findViewById(R.id.destImageView);

        initSeekBar();
    }

    private void initSeekBar() {
        mTextViewLowH = (TextView) findViewById(R.id.textViewLowY);
        mTextViewHighH = (TextView) findViewById(R.id.textViewHighY);
        mTextViewLowS = (TextView) findViewById(R.id.textViewLowCr);
        mTextViewHighS = (TextView) findViewById(R.id.textViewHighCr);
        mTextViewLowV = (TextView) findViewById(R.id.textViewLowCb);
        mTextViewHighV = (TextView) findViewById(R.id.textViewHighCb);

        mSeekBarLowH = (SeekBar) findViewById(R.id.seekBarLowH);
        mSeekBarLowH.setMax(179);
        mSeekBarLowH.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowH = i;

                mTextViewLowH.setText("Low H: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighH = (SeekBar) findViewById(R.id.seekBarHighH);
        mSeekBarHighH.setMax(179);
        mSeekBarHighH.setProgress(179);
        mSeekBarHighH.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighH = i;

                mTextViewHighH.setText("High H: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });

        mSeekBarLowS = (SeekBar) findViewById(R.id.seekBarLowS);
        mSeekBarLowS.setMax(255);
        mSeekBarLowS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowS = i;

                mTextViewLowS.setText("Low S: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighS = (SeekBar) findViewById(R.id.seekBarHighS);
        mSeekBarHighS.setMax(255);
        mSeekBarHighS.setProgress(255);
        mSeekBarHighS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighS = i;

                mTextViewHighS.setText("High S: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });

        mSeekBarLowV = (SeekBar) findViewById(R.id.seekBarLowV);
        mSeekBarLowV.setMax(255);
        mSeekBarLowV.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowV = i;

                mTextViewLowV.setText("Low V: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighV = (SeekBar) findViewById(R.id.seekBarHighV);
        mSeekBarHighV.setMax(255);
        mSeekBarHighV.setProgress(255);
        mSeekBarHighV.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighV = i;

                mTextViewHighV.setText("High V: " + i);
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

        Bitmap destBitmap = ProcessUtils.processHSV(srcBitmap, mLowH, mHighH, mLowS, mHighS, mLowV, mHighV);

        mSrcImageView.setImageBitmap(srcBitmap);
        mDestImageView.setImageBitmap(destBitmap);
    }
}
