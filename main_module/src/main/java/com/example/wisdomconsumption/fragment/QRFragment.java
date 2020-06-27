package com.example.wisdomconsumption.fragment;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.base.MVPQMUIFragment;
import com.example.common_lib.info.NowUserInfo;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.java_bean.QRBean;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * A simple {@link Fragment} subclass.
 */
public class QRFragment extends MVPQMUIFragment {

    private ImageView mQrImg;
    private String mType;

    public QRFragment(String type) {
        mType = type;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.main_fragment_qr;
    }

    @Override
    protected void initAllMembersView() {
        mQrImg = mView.findViewById(R.id.qrImg);
        QRBean qrBean = new QRBean(NowUserInfo.getNowUserPhone(), NowUserInfo.getNowUserId(), mType);//什么类型的

        Bitmap bitmap = getQrCode(SizeUtils.dp2px(320), SizeUtils.dp2px(320), qrBean.toString());
        if (bitmap != null) {
            mQrImg.setImageBitmap(bitmap);
        }
    }


    public Bitmap getQrCode(int width, int height, String content) {
        try {
            Map<EncodeHintType, Object> hints = null;
            String encoding = getEncoding(content);//获取编码格式
            if (encoding != null) {
                hints = new EnumMap<>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
            BitMatrix result = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);//通过字符串创建二维矩阵
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;//根据二维矩阵数据创建数组
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建位图
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);//设置位图像素集为数组
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到编码
     *
     * @param contents
     * @return
     */
    private static String getEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++)
            if (contents.charAt(i) > 0xFF)
                return "UTF-8";
        return null;
    }


}
