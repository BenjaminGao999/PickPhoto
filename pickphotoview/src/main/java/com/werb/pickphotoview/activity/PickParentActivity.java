package com.werb.pickphotoview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.werb.pickphotoview.R;
import com.werb.pickphotoview.fragment.PickListFragment;
import com.werb.pickphotoview.model.PickData;
import com.werb.pickphotoview.util.PickConfig;
import com.werb.pickphotoview.util.PickPhotoHelper;
import com.werb.pickphotoview.util.PickPhotoListener;
import com.werb.pickphotoview.util.PickPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:　Created by benjamin
 * DATE :  2017/6/15 14:04
 * versionCode:　v2.2
 * <p>
 * pick photo activity
 * 用于调度pick photo lib的fragment
 */

public class PickParentActivity extends AppCompatActivity {

    private PickData pickData;
    //相册分类条目之一的名字。
    private String dirName_album;

    //    选中的photo集合
    private List<String> selectPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickphoto_pickparent);

        pickData = (PickData) getIntent().getSerializableExtra(PickConfig.INTENT_PICK_DATA);
        if (pickData != null) {
            PickPreferences.getInstance(this).savePickData(pickData);
        } else {
            pickData = PickPreferences.getInstance(this).getPickData();
        }


        selectPath = new ArrayList<>();

        //查询数据库....注意一步加载数据库。必须等数据库加载完成之后才能去展示数据。

        PickPhotoListener photoListener = new PickPhotoListener() {
            @Override
            public void pickSuccess() {
                initView();
            }
        };
        PickPhotoHelper pickPhotoHelper = new PickPhotoHelper(this, photoListener);
        pickPhotoHelper.getImages();
    }

    private static final String TAG = "PickParentActivity";

    private void initView() {
        PickListFragment pickListFragment = new PickListFragment();
        getSupportFragmentManager()
                .beginTransaction()
//                .addToBackStack(null)
                .replace(R.id.fl_pickphoto_container, pickListFragment)
                .commit();
    }

    public PickData getPickData() {
        return pickData;
    }

    /**
     * @param dirName_album 设置相册分类条目之一的名字。
     */
    public void setDirName_album(String dirName_album) {
        this.dirName_album = dirName_album;
    }

    /**
     * @return dirName_album 设置相册分类条目之一的名字。
     */
    public String getDirName_album() {
        return dirName_album;
    }


    /**
     * @return selectPath 获取选中的photo集合
     */
    public List<String> getSelectPath() {
        return selectPath;
    }

    @Override
    public void finish() {

        Intent intent = new Intent();
        intent.putExtra(PickConfig.INTENT_IMG_LIST_SELECT, (Serializable) getSelectPath());
        setResult(PickConfig.PICK_PHOTO_DATA, intent);

        super.finish();
    }
}
