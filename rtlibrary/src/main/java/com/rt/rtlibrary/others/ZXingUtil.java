package com.rt.rtlibrary.others;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

//二维码工具类
public class ZXingUtil {

    //生成二维码
    public static Bitmap createQRcodeImage( String content) {

        int imageViewWidth = 200;
        int imageViewHeight = 200;

        //检测数据是否正常
        if (TextUtils.isEmpty(content)) {
            return null;
        }

        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, imageViewWidth, imageViewHeight, hints);
            int[] pixels = new int[imageViewWidth * imageViewHeight];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < imageViewHeight; y++) {
                for (int x = 0; x < imageViewWidth; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * imageViewWidth + x] = 0xff000000;
                    } else {
                        pixels[y * imageViewWidth + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(imageViewWidth, imageViewHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, imageViewWidth, 0, 0, imageViewWidth, imageViewHeight);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
