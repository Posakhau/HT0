public class MP3_InfoContainer {

    // VARIABLES
    private String artistName="Undefined Artist";
    private String albumName="Undefined Album";
    private String trackName="Undefined Track Name";
    private String trackPath="Undefined Track Path";
    private String trackDuration="Undefined Duration";
    private long checkSumCRC32=0;


    // GETTERS & SETTERS
    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getTrackName() {
        return trackName;
    }
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackPath() {
        return trackPath;
    }
    public void setTrackPath(String trackPath) {
        this.trackPath = trackPath;
    }

    public String getTrackDuration() {
        return trackDuration;
    }
    public void setTrackDuration(String trackDuration) {
        this.trackDuration = trackDuration;
    }

    public long getCheckSumCRC32() {return checkSumCRC32;}
    public void setCheckSumCRC32(long checkSumCRC32) {this.checkSumCRC32 = checkSumCRC32;}
}
