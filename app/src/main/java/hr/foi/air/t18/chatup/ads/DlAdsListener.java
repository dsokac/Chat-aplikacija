package hr.foi.air.t18.chatup.Ads;


import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * An ad listener that toasts all ad events.
 */
public class DlAdsListener extends AdListener {
    private Context mContext;

    public DlAdsListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onAdLoaded() {
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        String errorReason = "";
        switch (errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
        }
        Toast.makeText(mContext, String.format("onAdFailedToLoad(%s)", errorReason),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdOpened() {

    }

    @Override
    public void onAdClosed() {
    }

    @Override
    public void onAdLeftApplication() {
        Toast.makeText(mContext, "onAdLeftApplication()", Toast.LENGTH_SHORT).show();
    }
}
