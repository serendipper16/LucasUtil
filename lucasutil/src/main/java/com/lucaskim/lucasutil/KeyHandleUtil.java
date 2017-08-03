package com.lucaskim.lucasutil;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 2016-06-28.
 */
public class KeyHandleUtil {
    private static final int BACK_KEY_DELAY = 1000;  // 대기시간
    private static long mBackKeyPressedTime = 0;    // 뒤로가기 누른 시간

    public static void doubleBackFinish(Activity activity) {
        if (System.currentTimeMillis() > (mBackKeyPressedTime + BACK_KEY_DELAY)) {
            mBackKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(activity, activity.getString(R.string.msg_confirm_double_back_pressed), Toast.LENGTH_SHORT).show();
            return;
        }

        activity.finish();
    }
}