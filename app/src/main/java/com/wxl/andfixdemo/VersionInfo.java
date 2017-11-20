package com.wxl.andfixdemo;

/**
 * Created by wxl on 2017/11/20.
 */

public class VersionInfo {


    /**
     * codevalue : 1.0.2
     * url : http://localhost:8080/fix.apatch
     */

    private String codevalue;
    private String url;

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "codevalue='" + codevalue + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
