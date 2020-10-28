package com.android.mobilebox.core.prefs;
/**
 * @author yhm
 * @date 2017/11/27
 */

public interface PreferenceHelper {
    void setToken(String token);

    String getToken();

    void saveHostUrl(String hostUrl);

    String getHostUrl();

    void setLoginAccount(String account);

    void setLoginPassword(String password);

    String getLoginAccount();

    String getLoginPassword();
}

