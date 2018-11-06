import java.util.ArrayList;
import java.util.*;


public class Artist {

    //VARIABLES
    private String name="Unknown artist";

    private ArrayList<MP3_InfoContainer>Albums = new ArrayList<>();
    private ArrayList<MP3_InfoContainer>infoContainers;
    private ArrayList<MP3_InfoContainer>allArtistTracks = new ArrayList<>();

    private HashSet<String> albumNames = new HashSet<>();
    private String[] albumNamesArray = {};
    private ArrayList<Album>albums = new ArrayList<>();

    //CONSTRUCTORS
    private Artist() {}

    public Artist(String name, ArrayList<MP3_InfoContainer>primaryStorageLink) {
        setName(name);
        setInfoContainers(primaryStorageLink);
        selectAllArtistTracks();

        for(MP3_InfoContainer c:allArtistTracks) {
            albumNames.add(c.getAlbumName());
        }

        albumNamesArray = albumNames.toArray(new String[albumNames.size()]);
        for(String s:albumNamesArray) {
            Album album = new Album(s,allArtistTracks);
            albums.add(album);
        }
    }

    // GETTERS & SETTERS
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setInfoContainers(ArrayList<MP3_InfoContainer> infoContainers) {
        this.infoContainers = infoContainers;
    }


    //SERVICE METHODS
    private void selectAllArtistTracks() {
        for (MP3_InfoContainer c:infoContainers) {
            if (this.getName().equals(c.getArtistName())) {
                allArtistTracks.add(c);
            }
        }
    }

    public String report() {
        System.out.println("Artist: "+getName());
        StringBuilder reportString = new StringBuilder();
        reportString.append("Artist: "+getName()+"<br>");

        for (Album a:albums) {
            reportString.append(a.report());
        }
        reportString.append("<p>");
        return reportString.toString();
    }
} // end of class
