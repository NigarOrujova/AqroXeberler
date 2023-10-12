package com.yelload.agro_news.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "D*G-KaPdSgUkZp2s5v8y/B?E(H+MbLeThWmYq3t6w9z$C&F)J@NcYfUjXn2r4u7x";
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/user/register"; // Public path that clients can use to register.
}
