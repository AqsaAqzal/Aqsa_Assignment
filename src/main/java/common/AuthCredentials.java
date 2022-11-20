package common;

abstract public class AuthCredentials {

    public static final String authUsername = System.getenv("AUTH_USERNAME");
    public static final String authPassword = System.getenv("AUTH_PASSWORD");

}
