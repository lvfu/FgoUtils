package com.fgo.utils.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2016/8/11.
 */
public class PermissionUtils {

    private static final String TAG = "PermissionUtils";
    public static final int PERMISSION_REQUEST_CODE = 2;
    public static final int PERMISSION_SETTING_REQ_CODE = 1;

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(Object cxt, String permission, int requestCode) {
        if (!checkSelfPermissionWrapper(cxt, permission)) {
            if (!shouldShowRequestPermissionRationaleWrapper(cxt, permission)) {
                requestPermissionsWrapper(cxt, new String[]{permission}, requestCode);
            } else {
                Log.d(TAG, "should show rational");
            }
            return false;
        }

        return true;
    }


    private static void requestPermissionsWrapper(Object cxt, String[] permission, int requestCode) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            fragment.requestPermissions(permission, requestCode);
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }


    private static boolean shouldShowRequestPermissionRationaleWrapper(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission);
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.shouldShowRequestPermissionRationale(permission);
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    public static boolean shouldShowRequestPermissionRationaleWrapper(Object cxt, String[] permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;

            for (String result:permission) {

              if(!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                      result) ){

                  return false;
              }
            }


        }
        return true;
    }

    @TargetApi(23)
    private static boolean checkSelfPermissionWrapper(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            return ActivityCompat.checkSelfPermission(activity,
                    permission) == PackageManager.PERMISSION_GRANTED;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    public static String[] checkSelfPermissionArray(Object cxt, String[] permission) {
        ArrayList<String> permiList = new ArrayList<>();
        for (String p : permission) {
            if (!checkSelfPermissionWrapper(cxt, p)) {
                permiList.add(p);
            }
        }

        return permiList.toArray(new String[permiList.size()]);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermissionArray(Object cxt, String[] permission, int requestCode) {
        String[] permissionNo = checkSelfPermissionArray(cxt, permission);
        if (permissionNo.length > 0) {
            requestPermissionsWrapper(cxt, permissionNo, requestCode);
            return false;
        } else
            return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermissionArray(Context cxt, String[] permission) {
        String[] permissionNo = checkSelfPermissionArray(cxt, permission);


        for (String pmission : permissionNo) {
            if (ContextCompat.checkSelfPermission(cxt, pmission) != PackageManager.PERMISSION_GRANTED
                    ) {
                return false;
            }

        }

        return true;


    }


    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise back false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> verifyPermissionsData(int[] grantResults) {

        List<Integer> list=new ArrayList<>();

        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){

                list.add(i);
            }

        }
        return list;

    }


    @TargetApi(Build.VERSION_CODES.M)
    public static List<String> verifyPermissionsData(String[] permissionArray,Object ctx) {

        List<String> list=new ArrayList<>();

        for(int i=0;i<permissionArray.length;i++){
            if(!checkSelfPermissionWrapper(ctx,permissionArray[i])){

                list.add(permissionArray[i]);
            }

        }
        return list;

    }

//    public static List<String> verifyPermissionsData(int[] grantResults,String[] permissionArray,Object cxt) {
//
//        List<String> list=new ArrayList<>();
////        List<String> array=permissionArray.
//
//        for(int i=0;i<grantResults.length;i++){
//            if(checkSelfPermissionWrapper(cxt,permissionArray[i]))!=PackageManager.PERMISSION_GRANTED){
//
//                list.add(i);
//            }
//
//        }
//        back list;
//
//    }

    /**
     * 检测系统弹出权限
     *
     * @param cxt
     * @param req
     * @return
     */
    @TargetApi(23)
    public static boolean checkSettingAlertPermission(Object cxt, int req) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            if (!Settings.canDrawOverlays(activity.getBaseContext())) {
                Log.i(TAG, "Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, req);
                return false;
            }
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            if (!Settings.canDrawOverlays(fragment.getActivity())) {
                Log.i(TAG, "Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + fragment.getActivity().getPackageName()));
                fragment.startActivityForResult(intent, req);
                return false;
            }
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }

        return true;
    }

}
