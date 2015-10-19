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

public class ProcessingGRAYActivity extends AppCompatActivity {

    private final String _TAG = "ProcessingGRAYActivity:";

    private ImageView mSrcImageView;
    private ImageView mDestImageView;

    private SeekBar mSeekBarLowGRAY;
    private SeekBar mSeekBarHighGRAY;

    private TextView mTextViewLowGRAY;
    private TextView mTextViewHighGRAY;

    private int mLowGRAY = 0;
    private int mHighGRAY = 255;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_gray);

        mSrcImageView = (ImageView) findViewById(R.id.srcImageView);
        mDestImageView = (ImageView) findViewById(R.id.destImageView);

        initSeekBar();
    }

    private void initSeekBar() {
        mTextViewLowGRAY = (TextView) findViewById(R.id.textViewLowGRAY);
        mTextViewHighGRAY = (TextView) findViewById(R.id.textViewHighGRAY);

        mSeekBarLowGRAY = (SeekBar) findViewById(R.id.seekBarLowGRAY);
        mSeekBarLowGRAY.setMax(255);
        mSeekBarLowGRAY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLowGRAY = i;

                mTextViewLowGRAY.setText("Low GRAY: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                process();
            }
        });
        mSeekBarHighGRAY = (SeekBar) findViewById(R.id.seekBarHighGRAY);
        mSeekBarHighGRAY.setMax(255);
        mSeekBarHighGRAY.setProgress(255);
        mSeekBarHighGRAY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mHighGRAY = i;

                mTextViewHighGRAY.setText("High GRAY: " + i);
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

        Bitmap destBitmap = ProcessUtils.processGRAY(srcBitmap, mLowGRAY, mHighGRAY);

        mSrcImageView.setImageBitmap(srcBitmap);
        mDestImageView.setImageBitmap(destBitmap);
    }
}
