package com.wxl.andfixdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by wxl on 2017/11/17.
 */

public class MyApp extends Application {

    private PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mPatchManager = new PatchManager(this);
        mPatchManager.init(BuildConfig.VERSION_NAME);//current version
        mPatchManager.loadPatch();

        updataApatch();

    }

    private void updataApatch() {

        try {
            String patchFileStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fix1.apatch";
            File file=new File(patchFileStr);
            Log.e("===","file="+file.length());
            Log.e("===","path="+patchFileStr);
            Log.e("===","path="+file.exists());
            mPatchManager.addPatch(patchFileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
