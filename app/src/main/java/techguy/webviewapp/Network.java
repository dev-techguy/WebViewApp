package techguy.webviewapp;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bugsnag.android.Bugsnag;

class Network {
    /**
     * check network status
     */
    String networkChecker(Context context) {
        String status = null;
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null) {
                //do something
                status = activeNetworkInfo.getTypeName();

            }  //do nothing

        } catch (NullPointerException exception) {
            Bugsnag.notify(new NetworkErrorException(new Url().appUrl + "Exception \n" + exception.getMessage()));
        }
        return status;
    }
}
