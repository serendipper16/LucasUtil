package com.lucaskim.lucasutil;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2017-07-07.
 */

public class BasicUtil {
    private BasicUtil() {
        // 인스턴스 생성불가 처리
    }

    /**
     * 전화걸기(전화번호에서 문자만 추출)
     * permissions: <uses-permission android:name="android.permission.CALL_PHONE" />
     **/
    public static void sendCall(Context context, String tel, boolean isDirect) {
        try {
            if (ActivityCompat.checkSelfPermission(context, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "permission not allow", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(exportOnlyNumber(tel));

            Intent intent = new Intent(isDirect ? Intent.ACTION_CALL : Intent.ACTION_DIAL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(sb.toString()));

            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e(e.toString());
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /** SMS 전송 **/
    public static void sendSms(Context context, String tel, String message) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(tel);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
            intent.putExtra("address", tel);
            intent.putExtra("sms_body", message);
            intent.setType("vnd.android-dir/mms-sms");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /** 이메일 전송 **/
    public static void sendEmail(Context context, String email, String subject, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/contents");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent, "이메일 전송"));
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /** 공유하기(문자열 공유) **/
    public static void sharing(Context context, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("contents/plain");
            context.startActivity(Intent.createChooser(intent, "공유하기"));
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /** 웹사이트 실행 **/
    public static void launchWebsite(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
                context.startActivity(intent);
            } catch (Exception e2) {
                Toast.makeText(context, e2.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** 플레이스토어 실행 **/
    public static void launchPlayStore(Context context, String url) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("market://details?id=");
            sb.append(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /** 현재앱의 플레이스토어 실행 **/
    public static void launchMyPlayStore(Context context) {
        launchPlayStore(context, context.getPackageName());
    }

    /** 맵 실행 **/
    public static void launchMaps(Context context, double latitude, double longitude) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String data = String.format("geo:%s,%s", latitude, longitude);

            intent.setData(Uri.parse(data));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /** 생년월일 포멧으로 변경 **/
    public static String convertFormatBirth(String date) {
        if ((date == null) || (date.length() != 8)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(date);
        sb.insert(4, "-");
        sb.insert(7, "-");
        return sb.toString();
    }

    /** 전화번호 포멧으로 변경 **/
    public static String convertFormatTel(String tel) {
        if ((tel == null) || (tel.length() < 4)) {
            return tel;
        }

        switch (tel.length()) {
            case 5:
                return String.format("%s-%s", tel.substring(0, 1), tel.substring(1, 5)).toString();

            case 6:
                return String.format("%s-%s", tel.substring(0, 2), tel.substring(2, 6)).toString();

            case 7:
                return String.format("%s-%s", tel.substring(0, 3), tel.substring(3, 7)).toString();

            case 8:
                return String.format("%s-%s", tel.substring(0, 4), tel.substring(4, 8)).toString();

            case 9:
                return String.format("%s-%s-%s", tel.substring(0, 2), tel.substring(2, 5), tel.substring(5, 9)).toString();

            case 10:
                return String.format("%s-%s-%s", tel.substring(0, 3), tel.substring(3, 6), tel.substring(6, 10)).toString();

            default:
                return String.format("%s-%s-%s", tel.substring(0, tel.length() - 8), tel.substring(tel.length() - 8, tel.length() - 4), tel.substring(tel.length() - 4, tel.length())).toString();
        }
    }

    /** 문자열에서 숫자만 추출 **/
    public static String exportOnlyNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    /** 디바이스 전화번호 가져오기 **/
    public static String getLine1Number(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String line1Number = mTelephonyManager.getLine1Number();

        if (line1Number != null) {
            // TODO: 한국 국가코드 "+82" 에 대해서만 처리를 함.
            // TODO: 추후 다른 국가코드에 대해서 처리를 하려면 Locale 넣어주면 국가코드 받아와서 제거하는 코드로 변경
            if (line1Number.contains("+82")) {
                line1Number = line1Number.replace("+82", "0");
            }
        }

        return line1Number;
    }

    /** DP를 PX값으로 변환 **/
    public static float dpToPx(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /** PX를 DP로 변환 **/
    public static float pxToDp(Context context, float px) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / 160f);
    }

    /** 권한 승인여부 체크 **/
    public static boolean isPermissionGranted(Context context, String permission) {
        return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    /** 폴더 및 하위파일 전체 삭제 **/
    public static boolean removeDir(String dirPath) {
        File file = new File(dirPath);
        File chileFileList[] = file.listFiles();

        for (File childFile : chileFileList) {
            if (childFile.isDirectory()) {
                removeDir(childFile.getAbsolutePath());
            } else {
                childFile.delete();
            }
        }

        if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /** 폴더 용량 체크 **/
    public static long dirSize(String dirPath) {
        long totalMemory = 0;
        File file = new File(dirPath);
        File[] childFileList = file.listFiles();

        if (childFileList == null) {
            return 0;
        }

        for (File childFile : childFileList) {
            if (childFile.isDirectory()) {
                totalMemory += dirSize(childFile.getAbsolutePath());
            } else {
                totalMemory += childFile.length();
            }
        }

        return totalMemory;
    }

    /** 설정화면으로 이동 **/
    public static void launchAppSettings(Context context){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /** 설치되어있는 어플리케이션 실행(설치되어있지 않다면 마켓으로 이동) **/
    public static void launchInstalledApp(Context context, String packageName){
        PackageManager pm = context.getPackageManager();

        try {
            pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            context.startActivity(pm.getLaunchIntentForPackage(packageName));
        } catch (Exception e) {
            e.printStackTrace();
            launchPlayStore(context, packageName);
        }
    }
}