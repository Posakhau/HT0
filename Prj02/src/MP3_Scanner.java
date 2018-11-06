import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MP3_Scanner {

    private static String REPORT_PATH="C:\\TSK\\report.html";
    //private static String REPORT_PATH="D:\\TEST SCANNER\\report.html";

    //DATA STRUCTURES

    //Storage where all necessary files will be accumulated from all paths
    private ArrayList<File> primaryFileStorage = new ArrayList<>();

    //Storage for information containers what needs for sorting by first part of the task
    private ArrayList<MP3_InfoContainer> infoContainers = new ArrayList<>();

    // Storages for objects of Duplicate Class
    private ArrayList<Duplicate>duplicates = new ArrayList<>();
    private HashSet<Long>duplicatedSums = new HashSet<>();
    private Long[]duplicatedSumsArray = {};

    private HashSet<String> artistNames = new HashSet<>();
    private String[] artistNamesArray = {};
    private ArrayList<Artist>artists = new ArrayList<>();

    private ArrayList<StrangeDuplicate> strangeDuplicates = new ArrayList<>();

    //keeps types of files needed to analyze
    private String[] fileTypes = {"mp3"};

    // CONSTRUCTORS
    private MP3_Scanner() {}

    public MP3_Scanner(String[] paths) {

        // Stage I. Collecting all necessary files from address array and placing them into a
        // storage, where we will keep it for further operations

        try {
            getFilesFromPath(paths);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Taking all necessary MP3-attributes from files in storage,
        // and creating another storage of objects with necessary information,
        // which we will use for sorting procedure and for making this procedure more compact and faster
        try {
            for (int i = 0; i < primaryFileStorage.size(); ++i) {
                File mp3Track = new File(primaryFileStorage.get(i).getPath());
                InputStream input = new FileInputStream(mp3Track);
                ContentHandler handler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Parser parser = new Mp3Parser();
                ParseContext parseCtx = new ParseContext();
                parser.parse(input, handler, metadata, parseCtx);
                input.close();

                long currentChecksum = FileUtils.checksumCRC32(mp3Track);

                MP3_InfoContainer container = new MP3_InfoContainer();
                container.setArtistName(metadata.get("xmpDM:artist"));
                container.setAlbumName(metadata.get("xmpDM:album"));
                container.setTrackName(metadata.get("title"));
                container.setTrackPath(primaryFileStorage.get(i).getPath());
                container.setTrackDuration(formatTrackDuration(metadata.get("xmpDM:duration")));
                container.setCheckSumCRC32(currentChecksum);

                infoContainers.add(container);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
        // Stage II. Forming structure by task directions using information what we got from files attributes
        buildMainStructure();
        formDuplicatesList();
        formStrangeDuplicatesList();

        try {
        formFileReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //SERVICE METHODS

    private void getFilesFromPath (String []paths) {
        for (int i = 0; i < paths.length; ++i) {
            File file = new File(paths[i]);
            Collection<File> currentFilesList = FileUtils.listFiles(file, fileTypes, true);
            primaryFileStorage.addAll(currentFilesList);
        }
    }

   //returns track duration in format hours:minutes:seconds
    private String formatTrackDuration(String millisecondsDuration) {
        Double tempMill = Double.parseDouble(millisecondsDuration);
        long millDuration = Math.round(tempMill);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millDuration);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return new String(format.format(calendar.getTime()));
    }

    //builds an array of objects with necessary information fields
    private void buildMainStructure () {
        for(MP3_InfoContainer c:infoContainers) {
            artistNames.add(c.getArtistName());
        }

        artistNamesArray = artistNames.toArray(new String[artistNames.size()]);
        for(String s:artistNamesArray) {
            Artist artist = new Artist(s,infoContainers);
            artists.add(artist);
        }
    }

    //forms report string of part I of the task
    private String formArtistsReport() {
        StringBuilder reportString = new StringBuilder();
        for (Artist a:artists) {
            reportString.append(a.report());
        }
        return reportString.toString();
    }

    //forms array of objects by part II of the task
    private void formDuplicatesList() {
        for (int i=0;i<infoContainers.size();++i) {
            for (int j=0;j<infoContainers.size();++j) {
                if (i!=j) {
                    if (infoContainers.get(i).getCheckSumCRC32()==infoContainers.get(j).getCheckSumCRC32()) {
                        duplicatedSums.add(infoContainers.get(i).getCheckSumCRC32());
                    }
                }
            }
        }

        duplicatedSumsArray = duplicatedSums.toArray(new Long[duplicatedSums.size()]);

        for (int i=0;i<duplicatedSumsArray.length;++i) {
            Duplicate duplicate = new Duplicate(duplicatedSumsArray[i],infoContainers);
            duplicates.add(duplicate);
        }
    }

    //forms report string of part II of the task
    private String printDuplicatesReport() {
        int j=1;
        StringBuilder reportBuilder = new StringBuilder();
        for (int i=0;i<duplicates.size();++i) {
            System.out.println("Duplicate "+j);
            reportBuilder.append("Duplicate "+j+"<br>");
            reportBuilder.append(duplicates.get(i).printDuplicatePaths());
            j++;
        }
        return reportBuilder.toString();
    }

    //forms array of objects by part III of the task
    private void formStrangeDuplicatesList() {
        for (int i=0;i<infoContainers.size();++i) {
            for (int j=0;j<infoContainers.size();++j) {
                if (i!=j) {
                    if ((infoContainers.get(i).getArtistName().equals(infoContainers.get(j).getArtistName())&&
                          (infoContainers.get(i).getAlbumName().equals(infoContainers.get(j).getAlbumName())&&
                          (infoContainers.get(i).getTrackName().equals(infoContainers.get(j).getTrackName())&&
                          (infoContainers.get(i).getCheckSumCRC32()!=infoContainers.get(j).getCheckSumCRC32())
                    ))))
                    {
                        StrangeDuplicate strangeDuplicate = new StrangeDuplicate(infoContainers.get(i),infoContainers);
                        if (!isInStrangeContainer(strangeDuplicate)) {
                            strangeDuplicates.add(strangeDuplicate);
                        }
                        break;
                    }
                }
            }
        }
    }

    //forms report string of part III of the task
    private String printStrangeDuplicatesReport() {
        StringBuilder reportStringBuilder = new StringBuilder();
        for (StrangeDuplicate str:strangeDuplicates) {
            reportStringBuilder.append(str.report());
            //str.report();
        }
        reportStringBuilder.append("<p>");
        return reportStringBuilder.toString();
    }

    private boolean isInStrangeContainer (StrangeDuplicate strangeDuplicate) {
        for (int i=0;i<strangeDuplicates.size();++i) {
            if ((strangeDuplicate.getStrAlbumName().equals(strangeDuplicates.get(i).getStrAlbumName()))&&
                (strangeDuplicate.getStrTrackName().equals(strangeDuplicates.get(i).getStrTrackName()))&&
                (strangeDuplicate.getStrArtistName().equals(strangeDuplicates.get(i).getStrArtistName()))&&
                (strangeDuplicate.getStrCheckSumCRC32()==strangeDuplicates.get(i).getStrCheckSumCRC32())
            ) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // forms report file by all parts of the task
    private void formFileReport() throws IOException {

            FileWriter reportWriter = new FileWriter(REPORT_PATH);

            StringBuilder mainReportStringBuilder = new StringBuilder();

        if (primaryFileStorage.size()==0) {
            mainReportStringBuilder.append("In current paths no files of requesting type to form report");
            System.out.println("In current paths no files of requesting type to form report");
        } else {

            mainReportStringBuilder.append("<b>REPORT FOR TASK ONE</b> <p>");
            String partI = formArtistsReport();
            mainReportStringBuilder.append(partI);

            mainReportStringBuilder.append("<p style=\"margin-top:50px;\">");
            mainReportStringBuilder.append("<b>REPORT FOR TASK TWO</b> <p>");
            String partII = printDuplicatesReport();
            mainReportStringBuilder.append(partII);

            mainReportStringBuilder.append("<p style=\"margin-top:50px;\">");
            mainReportStringBuilder.append("<b>REPORT FOR TASK THREE</b> <p>");
            String partIII = printStrangeDuplicatesReport();
            mainReportStringBuilder.append(partIII);

            //reportWriter.write(mainReportStringBuilder.toString());

        }
        reportWriter.write(mainReportStringBuilder.toString());
        reportWriter.close();
    }

} // end of class
