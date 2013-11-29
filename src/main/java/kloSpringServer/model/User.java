package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 0:17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 4683845L;
    private String username = null;
    private String password = null;
    private String email = null;

    @JsonIgnore
    private List<String> errors = new ArrayList<>();

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public boolean validate() {
        boolean valid = true;
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(username);
        boolean foundWhiteSpace = matcher.find();

        if (username == null || username.length() > 20 || foundWhiteSpace || username.length() < 3) {
            valid = false;
            addError("Invalid username");
        }
        if (password == null || password.length() > 30 || password.length() < 5) {
            valid = false;
            addError("Invalid password");
        }
        return valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        errors.add(error);
    }
}
