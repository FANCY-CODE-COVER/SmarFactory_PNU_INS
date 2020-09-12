package inc.app.mes.util;

import android.util.Log;

public class respnoseLogger {
    public static void doPrint(int code, String s) {
        if (code == 500) {
            Log.i("SINSIN", "500실패");
        } else if (code == 503) {
            Log.i("SINSIN", "503 실패");
        } else if (code == 401) {
            Log.i("SINSIN", "401 실패");
        }
        Log.i("SINSIN", code + " 실패");
        Log.i("SINSIN", s + " 실패");
    }

}
