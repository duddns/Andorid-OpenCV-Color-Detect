package kr.dont.color_detect;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by duddns on 10/16/15.
 */
public class ProcessUtils {

    public static Bitmap processGRAY(Bitmap srcBitmap, int lowGray, int highGray) {
        /*
         * ROI 지정
         * convert to gray
         * 흰색 (시험지 색상) 검출, 나머지 영역은 테스트 라인으로 가정
         */

        Mat srcMat = new Mat();
        Mat destMat = new Mat();

        Utils.bitmapToMat(srcBitmap, srcMat);

        Rect roi = new Rect(srcMat.cols() / 3, 0, srcMat.cols() / 3, srcMat.rows());
        destMat = srcMat.submat(roi);

        Imgproc.cvtColor(destMat, destMat, Imgproc.COLOR_BGR2GRAY);

        Core.inRange(destMat, new Scalar(lowGray), new Scalar(highGray), destMat);

        Imgproc.erode(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.dilate(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));

        Imgproc.dilate(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.erode(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));

//        detectLine(destMat, destMat);

        Bitmap destBitmap = Bitmap.createBitmap(destMat.cols(), destMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(destMat, destBitmap);

        return destBitmap;
    }

    public static Bitmap processYCbCb(Bitmap srcBitmap, int lowY, int hithY, int lowCr, int hithCr, int lowCb, int hithCb) {
        /*
         * ROI 지정
         * convert to YCrCb
         * Y Cr Cb 이 특정 범위(흰색이 아닌)인 부분만 걸러냄
         */

        Mat srcMat = new Mat();
        Mat destMat = new Mat();

        Utils.bitmapToMat(srcBitmap, srcMat);

        Rect roi = new Rect(srcMat.cols() / 3, 0, srcMat.cols() / 3, srcMat.rows());
        destMat = srcMat.submat(roi);

        Imgproc.cvtColor(destMat, destMat, Imgproc.COLOR_BGR2YCrCb);

        Core.inRange(destMat, new Scalar(lowY, lowCr, lowCb), new Scalar(hithY, hithCr, hithCb), destMat);

        Imgproc.erode(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.dilate(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));

        Imgproc.dilate(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.erode(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));

        Bitmap destBitmap = Bitmap.createBitmap(destMat.cols(), destMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(destMat, destBitmap);

        return destBitmap;
    }


    public static Bitmap processHSV(Bitmap srcBitmap, int lowH, int hithH, int lowS, int hithS, int lowV, int hithV) {
        /*
         * ROI 지정
         * convert to HSV
         * H S V 이 특정 범위(흰색이 아닌)인 부분만 걸러냄
         */

        Mat srcMat = new Mat();
        Mat destMat = new Mat();

        Utils.bitmapToMat(srcBitmap, srcMat);

        Rect roi = new Rect(srcMat.cols() / 3, 0, srcMat.cols() / 3, srcMat.rows());
        destMat = srcMat.submat(roi);

        Imgproc.cvtColor(srcMat, destMat, Imgproc.COLOR_BGR2HSV);

        Core.inRange(destMat, new Scalar(lowH, lowS, lowV), new Scalar(hithH, hithS, hithV), destMat);

        Imgproc.erode(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.dilate(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));

        Imgproc.dilate(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.erode(destMat, destMat, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));

        Bitmap destBitmap = Bitmap.createBitmap(destMat.cols(), destMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(destMat, destBitmap);

        return destBitmap;
    }


//    private static void detectLine(Mat srcMat, Mat descMat) {
//        /*
//         * 반듯한 직선이 아니기 때문에, 전체 row 에 대해 10%에 해당하는 col 샘플링
//         * 각 row 별 샘플링한 col 중에서 색상이 있는 영역이 80% 이상이면 라인으로 판단
//         */
//
//        float SAMPLE_RATE = 0.1f;
//        float DECISION_RATE = 0.8f;
//
//        int sampleCount = (int) Math.floor(srcMat.cols() * SAMPLE_RATE);
//        int samplePoint = srcMat.cols() / sampleCount;
//        double color[];
//        for (int i = 0, lenRows = srcMat.rows(); i < lenRows; i++) {
//            color = srcMat.get(i, 0);
//            Log.d("color:", "color: " + color[0]);
//
//            int lineCheckCount = 0;
//            if (0 == color[0]) {
//                for (int j = 1; j < sampleCount; j++) {
//                    int x = j * samplePoint;
//
//                    color = srcMat.get(i, x);
//                    if (0 == color[0]) {
//                        lineCheckCount++;
//                    }
//                }
//            }
//
//            int lineDecisionCount = (int) Math.floor(sampleCount * DECISION_RATE);
//            if (lineDecisionCount <= lineCheckCount) {
//                Point start = new Point(0, i);
//                Point end = new Point(srcMat.cols() - 1, i);
//
//                Imgproc.line(descMat, start, end, new Scalar(0, 255, 0), 1);
//            }
//        }
//    }
}
