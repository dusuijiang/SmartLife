package cn.com.example.smartlife.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/5/9
 * Description: 单例模式  崩溃信息捕捉处理
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    private String TAG = "ExceptionCrashHandler";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;


    public static class ExceptionCrashHandlerHolder {
        public static ExceptionCrashHandler INSTANCE = new ExceptionCrashHandler();
    }

    public static ExceptionCrashHandler getInstance() {
        return ExceptionCrashHandlerHolder.INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultUncaughtExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "报错了");
        // 1. 获取信息
        // 1.1 崩溃信息
        // 1.2 手机信息
        // 1.3 版本信息

        // 2.写入文件
        String crashFileName = saveInfoToSD(ex);
        Log.e(TAG, "fileName --> " + crashFileName);
        // 3. 缓存崩溃日志文件
        cacheCrashFile(crashFileName);
        //系统默认处理
        mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
    }

    /**
     * 缓存崩溃日志文件
     *
     * @param fileName
     */
    private void cacheCrashFile(String fileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME", fileName).commit();
    }


    /**
     * 获取崩溃文件名称
     *
     * @return
     */
    public File getCrashFile() {
        String crashFileName = mContext.getSharedPreferences("crash",
                Context.MODE_PRIVATE).getString("CRASH_FILE_NAME", "");
        return new File(crashFileName);
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     *
     * @param ex
     * @return
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        //1. 手机信息  +    应用信息
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext)
                .entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        //2. 异常的详细信息
        sb.append(obtainExceptionInfo(ex));

        //保存文件  手机应用目录  并不是SD卡目录（6.0需要动态申请权限）

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            //    File dir = new File(mContext.getFilesDir() + File.separator + "crash"
            //             + File.separator);
            File dir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator );
            Log.i(TAG, "saveInfoToSD: " + dir.getAbsolutePath());
            // 先删除之前的异常信息
            if (dir.exists()) {
                //删除该目录下所有子文件
                deleteDir(dir);
            }

            // 再从新创建文件夹
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                fileName = dir.toString()
                        + File.separator
                        + getAssignTime("yyyy_MM_dd_HH_mm") + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 返回当前日期根据格式
     **/
    private String getAssignTime(String dateFormatStr) {
        DateFormat dataFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dataFormat.format(currentTime);
    }


    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     *
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);
        map.put("MOBLE_INFO", getMobileInfo());
        return map;
    }


    /**
     * Cell phone information
     *
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    private boolean deleteDir(File dir) {
        if (!dir.exists()) {
            return false;
        }
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            // 递归删除目录中的子目录下
            for (File child : children) {
                if (child.isDirectory()) {
                    deleteDir(child);
                } else {
                    child.delete();
                }
            }
        }
        dir.delete();
        // 目录此时为空，可以删除
        return true;
    }

    public static void deleteDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    if (subFile.isDirectory())
                        deleteDir(subFile.getPath());
                    else
                        subFile.delete();
                }
            }
            file.delete();
        }
    }

}
