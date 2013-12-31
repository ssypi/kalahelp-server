package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session implements Serializable {
    private static final long serialVersionUID = 3542845L;
    private String token;
    private String ipAddress;
    private Date expireDate;
    private String user;

    private static final int EXPIRE_HOURS = 6;

    public Session() {
    }

    public static Date newExpireDate() {
        Date currentDate = new Date();
        long expireHoursInMillis = EXPIRE_HOURS * 3600000L;
        currentDate.setTime(currentDate.getTime() + expireHoursInMillis);
        return currentDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

//        if (expireDate != null ? !expireDate.equals(session.expireDate) : session.expireDate != null) return false;
        if (!ipAddress.equals(session.ipAddress)) return false;
        if (!token.equals(session.token)) return false;

        return true;
    }

    public boolean validate() {
        if (token == null || token.isEmpty() || token.equals("0")) return false;
        return true;
    }

    public boolean isExpired() {
        Date currentTime = new Date();
        return expireDate.before(currentTime);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


//    private static class SessionTokenGenerator {
//        private SecureRandom random = new SecureRandom();
//
//        public String nextSessionToken() {
//            return new BigInteger(130, random).toString();
//        }
//    }
}
