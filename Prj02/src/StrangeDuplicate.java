import java.util.ArrayList;

public class StrangeDuplicate {

    private String strArtistName="Undefined Artist";
    private String strAlbumName="Undefined Album";
    private String strTrackName="Undefined Track";

    private long strCheckSumCRC32=0;

    //CONSTRUCTORS
    private StrangeDuplicate() {}

    public StrangeDuplicate (MP3_InfoContainer strangeContainer, ArrayList<MP3_InfoContainer>primaryLink) {
        setContainers(primaryLink);
        setStrArtistName(strangeContainer.getArtistName());
        setStrAlbumName(strangeContainer.getAlbumName());
        setStrTrackName(strangeContainer.getTrackName());

        for (int i=0;i<containers.size();++i) {
            if((strangeContainer.getArtistName().equals(containers.get(i).getArtistName()))
                    &&(strangeContainer.getAlbumName().equals(containers.get(i).getAlbumName()))
                    &&(strangeContainer.getTrackName().equals(containers.get(i).getTrackName()))
                    &&(strangeContainer.getCheckSumCRC32()!=containers.get(i).getCheckSumCRC32())) {
                strangeDuplicates.add(containers.get(i));
            }
        }
    }

    // DATA STORAGES
    private ArrayList<MP3_InfoContainer>containers = new ArrayList<>();
    private ArrayList<MP3_InfoContainer>strangeDuplicates = new ArrayList<>();

    // GETTERS & SETTERS

    public String getStrArtistName() {
        return strArtistName;
    }
    public void setStrArtistName(String strArtistName) {
        this.strArtistName = strArtistName;
    }

    public String getStrAlbumName() {
        return strAlbumName;
    }
    public void setStrAlbumName(String strAlbumName) {
        this.strAlbumName = strAlbumName;
    }

    public String getStrTrackName() {
        return strTrackName;
    }
    public void setStrTrackName(String strTrackName) {
        this.strTrackName = strTrackName;
    }

    public long getStrCheckSumCRC32() {
        return strCheckSumCRC32;
    }
    public void setStrCheckSumCRC32(long strCheckSumCRC32) {
        this.strCheckSumCRC32 = strCheckSumCRC32;
    }

    public void setContainers(ArrayList<MP3_InfoContainer> containers) {
        this.containers = containers;
    }

    // SERVICE METHODS
    public String report() {
        StringBuilder reportStringBuilder = new StringBuilder();
        for (MP3_InfoContainer c:strangeDuplicates) {
            reportStringBuilder.append(c.getTrackPath()+"<br>");
        }
        return reportStringBuilder.toString();
    }
}
