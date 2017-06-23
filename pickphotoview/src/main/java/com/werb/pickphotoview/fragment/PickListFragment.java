package com.werb.pickphotoview.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.werb.pickphotoview.R;
import com.werb.pickphotoview.activity.PickParentActivity;
import com.werb.pickphotoview.adapter.PickListAdapter;
import com.werb.pickphotoview.model.PickData;
import com.werb.pickphotoview.util.PickPhotoHelper;
import com.werb.pickphotoview.widget.MyToolbar;

import java.util.List;

/**
 * Author:　Created by benjamin
 * DATE :  2017/6/15 13:44
 * versionCode:　v2.2
 * 图片列表分类
 */

public class PickListFragment extends Fragment {

    private LinearLayout mLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (mLayout == null) {
            mLayout = (LinearLayout) inflater.inflate(R.layout.pick_activity_pick_photo_new, container, false);
        }
        return mLayout;
    }

    private PickData pickData;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pickData = ((PickParentActivity) getActivity()).getPickData();

        initToolbar();
        initRecyclerView();

        initPhotoHasSelected();
    }

    /**
     * 选中图片张数+上传图片按钮
     */
    private void initPhotoHasSelected() {
        TextView tvHasSelectedCount = (TextView) mLayout.findViewById(R.id.tv_photo_has_select);
        Button btnPhotoUpload = (Button) mLayout.findViewById(R.id.btn_upload_pickphoto);
        List<String> selectPath = ((PickParentActivity) getActivity()).getSelectPath();
        tvHasSelectedCount.setText("已选择" + selectPath.size() + "张照片");
        if (selectPath.size() == 0) {
            btnPhotoUpload.setEnabled(false);
            btnPhotoUpload.setBackgroundResource(R.drawable.has_selected_bg_pressed_pickphoto_shape);
        } else {
            btnPhotoUpload.setEnabled(true);
            btnPhotoUpload.setBackgroundResource(R.drawable.has_selected_bg_pickphoto_selector);
        }

        View.OnClickListener uploadLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        };
        btnPhotoUpload.setOnClickListener(uploadLis);
    }

    private void initToolbar() {
//        Window window = getActivity().getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(pickData.getStatusBarColor());
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (pickData.isLightStatusBar()) {
//                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }
//        MyToolbar myToolbar = (MyToolbar) mLayout.findViewById(R.id.toolbar);
//        myToolbar.setBackgroundColor(pickData.getToolbarColor());
//        myToolbar.setIconColor(pickData.getToolbarIconColor());
//        myToolbar.setPhotoDirName(getString(R.string.pick_photos));
//        myToolbar.setLeftIcon(R.mipmap.pick_ic_back);
//        myToolbar.setLeftLayoutOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PickListActivity.this.finish();
//            }
//        });

        ImageView ivBack = (ImageView) mLayout.findViewById(R.id.iv_back_toolbar);
        TextView tvCancel = (TextView) mLayout.findViewById(R.id.tv_cancel_toolbar);
        TextView tvName = (TextView) mLayout.findViewById(R.id.tv_name_toolbar);

        tvName.setText("相簿");
        View.OnClickListener backLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PickParentActivity) getActivity()).getSelectPath().clear();
                getActivity().finish();
            }
        };
        ivBack.setOnClickListener(backLis);
        View.OnClickListener cancelLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PickParentActivity) getActivity()).getSelectPath().clear();
                getActivity().finish();
            }
        };
        tvCancel.setOnClickListener(cancelLis);
    }

    private void initRecyclerView() {
        RecyclerView listPhoto = (RecyclerView) mLayout.findViewById(R.id.photo_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        listPhoto.setLayoutManager(layoutManager);

        PickListAdapter listAdapter = new PickListAdapter(getActivity(), listener);

        listPhoto.setAdapter(listAdapter);
    }

    /**
     * 打开对应分类下的真正的图片选取页面
     */
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String dirName = (String) v.getTag(R.id.pick_dir_name);
//            Intent intent = new Intent();
//            intent.putExtra(PickConfig.INTENT_DIR_NAME, dirName);
            ((PickParentActivity) getActivity()).setDirName_album(dirName);

            PickPhotoFragment pickPhotoFragment = new PickPhotoFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fl_pickphoto_container, pickPhotoFragment)
                    .commit();
        }
    };
}
