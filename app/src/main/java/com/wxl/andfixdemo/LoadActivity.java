package com.wxl.andfixdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wxl on 2017/11/20.
 */

public class LoadActivity extends AppCompatActivity {
    private String baseUrl="http://192.168.120.26:8080/";
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        mTextView = findViewById(R.id.text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoadActivity.this, "版本二", Toast.LENGTH_SHORT).show();
            }
        });

        getVersionCode();
        startDownload();
    }

    private void getVersionCode(){
        String url="version.json";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                //解析rxjava的适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<VersionInfo> versionCode = apiService.getVersionCode(url);
        versionCode.enqueue(new Callback<VersionInfo>() {
            @Override
            public void onResponse(Call<VersionInfo> call, Response<VersionInfo> response) {
                Log.e("===","json="+response.body());


            }

            @Override
            public void onFailure(Call<VersionInfo> call, Throwable t) {

            }
        });

    }

    private void startDownload() {



        String downloadUrl = "fix1.apatch";

//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //解析rxjava的适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService downloadService = retrofit.create(ApiService.class);

        Call<ResponseBody> responseBodyCall = downloadService.downloadFile(downloadUrl);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if(response==null){
                    return;
                }
                Log.d("vivi",response.message()+"  length  "+response.body().contentLength()+"  type "+response.body().contentType());

                //建立一个文件
                final File file = FileUtils.createFile(LoadActivity.this);

                //下载文件放在子线程
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        long length=file.length();
                        Log.e("===","length1="+length);
                        //保存到本地
                        FileUtils.writeFile2Disk(response, file, new HttpCallBack() {
                            @Override
                            public void onLoading(final long current, final long total) {
                                /**
                                 * 更新进度条
                                 */
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            }
                        });

                        long length2=file.length();
                        Log.e("===","length2="+length2);
                        Log.e("===","path="+file.getAbsolutePath());

                    }
                }.start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
                Log.d("vivi",t.getMessage()+"  "+t.toString());
            }
        });

    }
}
