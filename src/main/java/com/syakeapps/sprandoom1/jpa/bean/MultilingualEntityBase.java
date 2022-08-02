package com.syakeapps.sprandoom1.jpa.bean;

import java.util.Locale;

public abstract class MultilingualEntityBase {

    protected abstract String getJpnName();

    protected abstract String getEngName();

    public String getLocaleName(Locale locale) {
        return locale.equals(Locale.JAPANESE) ? getJpnName() : getEngName();
    }
}
