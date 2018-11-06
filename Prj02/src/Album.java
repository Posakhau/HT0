import java.util.ArrayList;
import java.util.HashSet;

public class Album {

    //CONSTRUCTORS
    private Album() {}

    public Album(String name, ArrayList<MP3_InfoContainer>allArtistTracks) {
        setName(name);
        setAllArtistTracks(allArtistTracks);

        for (MP3_InfoContainer c:allArtistTracks) {
            if(this.getName().equals(c.getAlbumName())) {
                artistTracksInAlbum.add(c);
            }
        }
    }

    private String name="Unknown Album";

    private ArrayList<MP3_InfoContainer>allArtistTracks;
    private HashSet<MP3_InfoContainer>artistTracksInAlbum = new HashSet<>();

    //GETTERS & SETTERS

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAllArtistTracks(ArrayList<MP3_InfoContainer> allArtistTracks) {
        this.allArtistTracks = allArtistTracks;
    }


    //SERVICE METHODS
    public String report() {
        StringBuilder reportString = new StringBuilder();

        reportString.append("Album: "+this.getName()+"<br>");
        for(MP3_InfoContainer c:artistTracksInAlbum) {
            reportString.append(c.getTrackName()+"&nbsp&nbsp"
            +c.getTrackDuration()+"&nbsp&nbsp"
            +c.getTrackPath()+"<br>");
        }
        reportString.append("<p>");
        return reportString.toString();
    }
}
