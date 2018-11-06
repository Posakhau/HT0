import java.util.ArrayList;

public class Duplicate {

    private Duplicate(){}

    public Duplicate (Long incomingUniqueSum,ArrayList<MP3_InfoContainer>primaryLink) {
        setLinkForContainersStorage(primaryLink);
        for (int i=0;i<containers.size();++i) {
            if (incomingUniqueSum==containers.get(i).getCheckSumCRC32()) {
                duplicatePaths.add(containers.get(i).getTrackPath());
            }
        }
    }

    private ArrayList<MP3_InfoContainer>containers = new ArrayList<>();
    private ArrayList<String>duplicatePaths = new ArrayList<>();

    private void setLinkForContainersStorage(ArrayList<MP3_InfoContainer>primaryLink) {
        this.containers = primaryLink;
    }

    public String printDuplicatePaths () {
            StringBuilder reportBuilder = new StringBuilder();
            for (int i = 0; i < duplicatePaths.size(); ++i) {
                System.out.println(" - " + duplicatePaths.get(i));
                reportBuilder.append(" - "+duplicatePaths.get(i)+"<br>");
            }
        reportBuilder.append("<p>");
        return reportBuilder.toString();
    }

} // end of class
