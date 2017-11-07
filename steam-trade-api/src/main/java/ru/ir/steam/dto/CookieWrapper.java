package ru.ir.steam.dto;

import org.apache.http.cookie.Cookie;

import java.util.Arrays;
import java.util.Date;

public class CookieWrapper implements Cookie {

    private final String name;

    private final String value;

    private final String comment;

    private final String commentURL;

    private final Date expiryDate;

    private final boolean isPersistent;

    private final String domain;

    private final String path;

    private final int[] ports;

    private final boolean secure;

    private final int version;

    public CookieWrapper(String name, String value, String comment, String commentURL, Date expiryDate, boolean isPersistent,
                         String domain, String path, int[] ports, boolean secure, int version) {
        this.name = name;
        this.value = value;
        this.comment = comment;
        this.commentURL = commentURL;
        this.expiryDate = expiryDate;
        this.isPersistent = isPersistent;
        this.domain = domain;
        this.path = path;
        this.ports = ports;
        this.secure = secure;
        this.version = version;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public String getCommentURL() {
        return commentURL;
    }

    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean isPersistent() {
        return isPersistent;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int[] getPorts() {
        return ports;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public boolean isExpired(Date date) {
        return (expiryDate != null && expiryDate.getTime() <= date.getTime());
    }

    @Override
    public String toString() {
        return "CookieWrapper{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", comment='" + comment + '\'' +
                ", commentURL='" + commentURL + '\'' +
                ", expiryDate=" + expiryDate +
                ", isPersistent=" + isPersistent +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", ports=" + Arrays.toString(ports) +
                ", secure=" + secure +
                ", version=" + version +
                '}';
    }
}
