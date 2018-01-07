package eu.creapix.louisss13.smartchandoid.utils;

/**
 * Created by arnau on 28-12-17.
 */

public class Constants {


    //DAO Related
    public static final String LOGIN = "login";
    public static final String PWD = "pwd";
    public static final String MATCH_ID = "match_id";
    public static final String MATCH_SCORE = "match_score";
    public static final String GET_TOURNAMENT = "get_tournament";
    public static final String GET_MATCHES = "get_matches";
    public static final String GET_WATCHED = "get_watched";
    public static final String GET_PROFILE = "get_profile";
    public static final String GET_MONITORED_MATCHES = "get_monitored_matches";
    public static final String GET_CLUBS = "get_clubs";
    public static final String POST_POINT = "post_point";


    //Player Management
    public static final int PLAYER_1_POINT = 0;
    public static final int PLAYER_2_POINT = 1;
    public static final String PLAYER_1_NAME = "player_1_name";
    public static final String PLAYER_2_NAME = "player_2_name";
    public static final String ADD_POINT = "add_point";
    public static final String DELETE_POINT = "delete_point";
    public static final String RESULT_PLAYER_1 = "result_player_1";
    public static final String RESULT_PLAYER_2 = "result_player_2";
    public static final String RESULT_MATCH_ID = "result_match_id";
    public static final int RESULT_COUNT_POINT = 200;

    //Error Management - Password
    public static final int HAS_SPECIAL_CHAR = 0;
    public static final int HAS_UPPER_CASE = 1;
    public static final int HAS_LOWER_CASE = 2;
    public static final int HAS_DIGIT = 3;
    public static final int HAS_ENOUGH_UNIQUE_CHAR = 4;

    public static final int MIN_UNIQUE_CHAR_REQUIRED = 6;
    public static final int MIN_CHAR_REQUIRED = 8;

    //Custom Base URL
    public static final String BASE_URL_COUNT_POINT = "http://smartch.azurewebsites.net/api/matchs/";
    public static final String BASE_URL_GET_CLUB_BY_USERID = "http://smartch.azurewebsites.net/api/clubs/user/";


}
