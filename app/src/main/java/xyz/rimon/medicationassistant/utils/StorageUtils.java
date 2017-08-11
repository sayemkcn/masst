package xyz.rimon.medicationassistant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.domains.Drug;

/**
 * Created by SAyEM on 8/11/17.
 */

public class StorageUtils {

    private static final String TAG = "StorageUtils";

    public static final String ALL_DRUGS_FILE = "all.m";
    public static final String OFFLINE_OBJECT_FILE = "offline.m";

    public static boolean storageAvailable(Context context) {
        File dir = context.getExternalFilesDir(null);
        if (dir != null) {
            return dir.exists() && dir.canRead() && dir.canWrite();
        } else {
            Logger.e("storageAvailable()", "Storage not available: data folder is null");
            return false;
        }
    }

    /**
     * Checks if external storage is available. If external storage isn't
     * available, the current activity is finsished an an error activity is
     * launched.
     *
     * @param activity the activity which would be finished if no storage is
     *                 available
     * @return true if external storage is available
     */
    public static boolean checkStorageAvailability(Activity activity) {
        boolean storageAvailable = storageAvailable(activity);
        if (!storageAvailable) {
            activity.finish();
            Toaster.showToast(activity,"Insufficient storage!");
        }
        return storageAvailable;
    }

    /**
     * Get the number of free bytes that are available on the external storage.
     */
    public static long getFreeSpaceAvailable(Context context) {
        StatFs stat = new StatFs(context.getExternalFilesDir(null).getAbsolutePath());
        long availableBlocks;
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
            blockSize = stat.getBlockSizeLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
            blockSize = stat.getBlockSize();
        }
        return availableBlocks * blockSize;
    }

    public static String getCmedDirPath() {
        File rootPath = Environment.getExternalStorageDirectory();
        File cmedDir = new File(rootPath.getAbsolutePath() + "/cmedhealth");
        if (!cmedDir.exists()) cmedDir.mkdirs();
        return cmedDir.getAbsolutePath();
    }

    public static String getCmedReportDirPath() {
        File reportDir = new File(getCmedDirPath() + "/reports");
        if (!reportDir.exists()) reportDir.mkdirs();
        return reportDir.getAbsolutePath();
    }

    public static void writeObjects(String fileName, List<Drug> drugList) {
        File rootPath = Environment.getExternalStorageDirectory();
        File cmedDir = new File(rootPath.getAbsolutePath() + "/cmedhealth");
        if (!cmedDir.exists()) cmedDir.mkdirs();
        File measurementsFile = new File(cmedDir, fileName);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(measurementsFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(drugList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<Drug> readObjects(String fileName) {
        List<Drug> drugList = new ArrayList<>();
        File rootPath = Environment.getExternalStorageDirectory();
        File cmedDir = new File(rootPath.getAbsolutePath() + "/cmedhealth");
        if (!cmedDir.exists()) cmedDir.mkdirs();
        File measurementsFile = new File(cmedDir, fileName);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(measurementsFile);
            ois = new ObjectInputStream(fis);
            drugList = (List<Drug>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.e("readObjects()", "CAN\'T READ OBJECTS " + e.toString());
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("MEASUREMENT_LIST_READ", drugList.size() + "");
        return drugList;
    }

    public static void writeObject(String fileName, Drug drug) {
        List<Drug> drugList = StorageUtils.readObjects(fileName);
        if (drugList == null)
            drugList = new ArrayList<>();
        drugList.add(drug);
        StorageUtils.writeObjects(fileName, drugList);
        Log.i("OFFLINE_MEASUREMENTS", String.valueOf(drugList.size()));
    }

    public static void openFileIntent(Context context, String dir, String fileName) {
        File file = new File(dir + "/" + fileName);

// Just example, you should parse file name for extension
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".pdf");

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), mime);
        context.startActivity(intent);
    }

}
