package com.zhenqi.baseutil.pop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhenqi.baseutil.R;
import com.zhenqi.baseutil.base.BasePopWindow;


public class HomeSharePopWindow extends BasePopWindow {
    public ImageView showImg;
//    public UMWeb mUMWeb;
    public ImageView close;
    public View wechat;
    public View friend;
    public View download;

    public HomeSharePopWindow(Activity activity) {
        super(activity);
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        View view = View.inflate(mActivity, R.layout.pop_share, null);
        showImg = view.findViewById(R.id.img);
        close = view.findViewById(R.id.home_share_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        wechat = view.findViewById(R.id.wechat_share);
        friend = view.findViewById(R.id.friend_share);
        initView(wechat, R.mipmap.wechat, "微信");
        initView(friend, R.mipmap.friend, "朋友圈");
        wechat.setOnClickListener(mOnclick);
        friend.setOnClickListener(mOnclick);
        return view;
    }

    private void initView(View view, int imgRes, String title) {
        if (view != null) {
            ImageView imageView = view.findViewById(R.id.share_img);
            imageView.setImageResource(imgRes);
            TextView textView = view.findViewById(R.id.share_title);
            textView.setText(title);
        }
    }

    private View.OnClickListener mOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if(mUMWeb == null)
//                return;
//            int id = v.getId();
//            if (id == R.id.wechat_share) {
//                ShareLoginUtil.ShareWeb(mActivity, SHARE_MEDIA.WEIXIN, mUMWeb);
//            } else if (id == R.id.friend_share) {
//                ShareLoginUtil.ShareWeb(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE,mUMWeb);
//            }
        }
    };


}
