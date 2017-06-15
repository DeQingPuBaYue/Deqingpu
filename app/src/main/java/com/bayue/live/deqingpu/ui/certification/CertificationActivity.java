package com.bayue.live.deqingpu.ui.certification;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.FragmentActivityBase;
import com.bayue.live.deqingpu.ui.certification.fragment.CertificationFrag;
import com.bayue.live.deqingpu.utils.ImageByAndroid;

import java.io.File;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class CertificationActivity extends FragmentActivityBase {
    public static File tempFile;
    public static final int PHOTO_REQUEST_CAMERA = 0x0001;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 0x0002;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 0x0003;// 结果
    public static final String PHOTO_FILE_NAME = "DeQingPu_Crop.jpg";
    public static final String PHOTO_NAME = "DeQingPu_Crop";
    public static String hmtempPath = "";
    private CertificationFrag mTextTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
//        ImageByAndroid.getPath(ImageByAndroid.avatorpath);
        tempFile = ImageByAndroid.getFile(ImageByAndroid.avatorpath, PHOTO_FILE_NAME);
        if (mTextTabFragment == null) {
            mTextTabFragment = CertificationFrag.newInstance(getString(R.string.navigation_text_tab));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, mTextTabFragment);
        transaction.commit();
    }

}
