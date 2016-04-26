package fr.partiel.partiel;

/**
 * Created by guillaumeboutin on 26/04/2016.
 */
public class UrlSwitch {

    String url;

    public String urlChoice(String choice){
        switch (choice){
            case "CONNECT" :
                url = "http://192.168.1.19:8888/login";
                break;
            case "TRACKS" :
                url = "http://192.168.1.19:8888/musics";
                break;
            case "ARTIST" :
                url = "http://192.168.1.19:8888/artists";
                break;
            case "ALBUM" :
                url = "http://192.168.1.19:8888/albums";
                break;
            case "GENRE" :
                url = "http://192.168.1.19:8888/genres";
                break;
        }
        return url;
    }
}
