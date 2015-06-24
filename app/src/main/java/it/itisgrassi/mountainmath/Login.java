package it.itisgrassi.mountainmath;

/**
 * Created by Claudiu on 01/03/2015.
 */
public class Login {
    private static String utente;
    private static String password;
    private static String squadra;

    public Login(){

    }

    public static void setUtente(String s){
        utente = s;
    }

    public static String getUtente(){
        return utente;
    }

    public static void setPassword(String p){
        password=p;
    }

    public static String getPassword(){
        return password;
    }

    public static void setSquadra(String sq) {
        squadra = sq;
    }

    public static String getSquadra() {
        return squadra;
    }
}
