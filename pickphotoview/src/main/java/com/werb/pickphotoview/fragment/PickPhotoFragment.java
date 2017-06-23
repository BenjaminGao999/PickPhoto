package com.werb.pickphotoview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.werb.pickphotoview.R;
import com.werb.pickphotoview.activity.PickParentActivity;
import com.werb.pickphotoview.adapter.MyPickGridAdapter;
import com.werb.pickphotoview.adapter.MySpaceItemDecoration;
import com.werb.pickphotoview.model.GroupImage;
import com.werb.pickphotoview.model.PickData;
import com.werb.pickphotoview.util.PickConfig;
import com.werb.pickphotoview.util.PickPhotoHelper;
import com.werb.pickphotoview.util.PickPhotoListener;
import com.werb.pickphotoview.util.PickPreferences;
import com.werb.pickphotoview.util.PickUtils;
import com.werb.pickphotoview.widget.MyToolbar;

import java.util.List;

/**
 * Author:　Created by benjamin
 * DATE :  2017/6/15 13:56
 * versionCode:　v2.2
 * 真正的图片选取页面
 */

public class PickPhotoFragment extends Fragment {

    private LinearLayout mLayout;

    private PickData pickData;
    private RecyclerView photoList;
    private MyPickGridAdapter pickGridAdapter;
    private MyToolbar myToolbar;
    private TextView selectText, selectImageSize;
    private List<String> allPhotos;
    private RequestManager manager;
    private String dirName_album;
    private TextView tvHasSelectedCount;
    private Button btnPhotoUpload;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        mLayout = (LinearLayout) inflater.inflate(R.layout.pick_activity_pick_photo_new, container, false);
        return mLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = Glide.with(getActivity());
        pickData = ((PickParentActivity) getActivity()).getPickData();

        initToolbar();

        //相册分类之一的名称
        dirName_album = ((PickParentActivity) getActivity()).getDirName_album();
        GroupImage listImage = PickPreferences.getInstance(getActivity()).getListImage();
        allPhotos = listImage.mGroupMap.get(dirName_album);
//        myToolbar.setPhotoDirName(dirName_album);
//        selectText.setText(getString(R.string.pick_pick));
//        selectText.setTextColor(getResources().getColor(R.color.pick_black));

        initRecyclerView();

//        setHasSelectedCount();
        initPhotoHasSelected();
    }

    /**
     * 选中图片张数+上传图片按钮
     */
    private void initPhotoHasSelected() {
        tvHasSelectedCount = (TextView) mLayout.findViewById(R.id.tv_photo_has_select);
        btnPhotoUpload = (Button) mLayout.findViewById(R.id.btn_upload_pickphoto);
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
//        selectText = (TextView) mLayout.findViewById(R.id.tv_pick_photo);
//        selectImageSize = (TextView) mLayout.findViewById(R.id.tv_preview_photo);
//        selectImageSize.setText(String.valueOf("0"));
//        myToolbar = (MyToolbar) mLayout.findViewById(R.id.toolbar);
//        myToolbar.setBackgroundColor(pickData.getToolbarColor());
//        myToolbar.setIconColor(pickData.getToolbarIconColor());
//        myToolbar.setLeftIcon(R.mipmap.pick_ic_open);
//        myToolbar.setRightIcon(R.mipmap.pick_ic_close);
//        myToolbar.setPhotoDirName(getString(R.string.pick_all_photo));
//        myToolbar.setLeftLayoutOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PickPhotoActivity.this.startPhotoListActivity();
//            }
//        });
//        myToolbar.setRightLayoutOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PickPhotoActivity.this.finish();
//            }
//        });
//
//        selectText.setOnClickListener(selectClick);

        FrameLayout flUploadDesc = (FrameLayout) mLayout.findViewById(R.id.fl_upload_desc);
        flUploadDesc.setVisibility(View.VISIBLE);

        ImageView ivBack = (ImageView) mLayout.findViewById(R.id.iv_back_toolbar);
        TextView tvCancel = (TextView) mLayout.findViewById(R.id.tv_cancel_toolbar);
        TextView tvName = (TextView) mLayout.findViewById(R.id.tv_name_toolbar);

        tvName.setText("相机胶卷");
        View.OnClickListener backLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
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
        photoList = (RecyclerView) mLayout.findViewById(R.id.photo_list);
        photoList.setItemAnimator(new DefaultItemAnimator());

//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), pickData.getSpanCount());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        photoList.setLayoutManager(layoutManager);

//        photoList.addItemDecoration(new SpaceItemDecoration(PickUtils.getInstance(getActivity()).dp2px(PickConfig.ITEM_SPACE), pickData.getSpanCount()));
        photoList.addItemDecoration(new MySpaceItemDecoration(PickUtils.getInstance(getActivity()).dp2px(3), 3));

        photoList.addOnScrollListener(scrollListener);

        PickPhotoHelper helper = new PickPhotoHelper(getActivity(), new PickPhotoListener() {
            @Override
            public void pickSuccess() {
//                GroupImage groupImage = PickPreferences.getInstance(getActivity()).getListImage();
//                allPhotos = groupImage.mGroupMap.get(PickConfig.ALL_PHOTOS);
                if (allPhotos == null) {
                    Log.d("PickPhotoView", "Image is Empty");
                } else {
                    Log.d("All photos size:", String.valueOf(allPhotos.size()));
                }
                if (allPhotos != null && !allPhotos.isEmpty()) {
                    pickGridAdapter = new MyPickGridAdapter(PickPhotoFragment.this, manager, allPhotos, pickData.isShowCamera(), pickData.getSpanCount(), pickData.getPickPhotoSize(), imageClick);
                    photoList.setAdapter(pickGridAdapter);
                }
            }
        });
        helper.getImages();
    }

    public void updateSelectText(int selectSize) {
        tvHasSelectedCount = (TextView) mLayout.findViewById(R.id.tv_photo_has_select);
        btnPhotoUpload = (Button) mLayout.findViewById(R.id.btn_upload_pickphoto);
        tvHasSelectedCount.setText("已选择" + selectSize + "张照片");
        if (selectSize == 0) {
            btnPhotoUpload.setEnabled(false);
            btnPhotoUpload.setBackgroundResource(R.drawable.has_selected_bg_pressed_pickphoto_shape);
        } else {
            btnPhotoUpload.setEnabled(true);
            btnPhotoUpload.setBackgroundResource(R.drawable.has_selected_bg_pickphoto_selector);
        }
    }

//    /**
//     * 打开图片列表分类
//     */
//    private void startPhotoListActivity() {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), PickListActivity.class);
//        intent.putExtra(PickConfig.INTENT_PICK_DATA, pickData);
//        startActivityForResult(intent, PickConfig.LIST_PHOTO_DATA);
//    }

    View.OnClickListener imageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            String imgPath = (String) v.getTag(R.id.pick_image_path);
//            Intent intent = new Intent();
//            intent.setClass(getActivity(), PickPhotoPreviewActivity.class);
//            intent.putExtra(PickConfig.INTENT_IMG_PATH, imgPath);
//            intent.putExtra(PickConfig.INTENT_IMG_LIST, (Serializable) allPhotos);
//            intent.putExtra(PickConfig.INTENT_IMG_LIST_SELECT, (Serializable) pickGridAdapter.getSelectPath());
//            intent.putExtra(PickConfig.INTENT_PICK_DATA, pickData);
//            startActivityForResult(intent, PickConfig.PREVIEW_PHOTO_DATA);
        }
    };


    /**
     * 之前toolbar的监听。
     */
    private View.OnClickListener selectClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            select();
        }
    };

    public void select() {
        if (pickGridAdapter == null) {
            return;
        }

        if (!pickGridAdapter.getSelectPath().isEmpty()) {
//            Intent intent = new Intent();
//            intent.putExtra(PickConfig.INTENT_IMG_LIST_SELECT, (Serializable) pickGridAdapter.getSelectPath());
//            setResult(PickConfig.PICK_PHOTO_DATA, intent);
//            finish();
        }
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (Math.abs(dy) > PickConfig.SCROLL_THRESHOLD) {
                manager.pauseRequests();
            } else {
                manager.resumeRequests();
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                manager.resumeRequests();
            }
        }
    };
}
