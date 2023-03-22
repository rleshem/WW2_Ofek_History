package ww2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import ww2.util.Setup;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConvertBibToCsv {
    File csvFile;
    private CSVPrinter csvPrinter;

    public void exportEntries(List<Entry> entries) throws IOException {
        initCsv();
        for (Entry entry : entries) {
            csvPrinter.printRecord(
                    entry.getTitle(),
                    entry.getSeqNum(),
                    entry.getFirstPage(),
                    entry.getLastPage(),
                    entry.getHasAbstract(),
                    entry.getKeywords(),
                    entry.getAnnotationReason()
            );
            csvPrinter.flush();
        }
    }

    private void initCsv() throws IOException {
        String fileName =
                Setup.getSetup().getStringProperty(Setup.csvFileName) +
                        getCurrentTimeAsString() +
                        ".csv";
        csvFile = new File(fileName);

        String[] HEADERS = {
                "title",
                "seqNum",
                "firstPage",
                "lastPage",
                "hasAbs",
                "keywords",
                "annote"
        };

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .build();

        Writer writer = Files.newBufferedWriter(Path.of(fileName));
        csvPrinter = new CSVPrinter(writer, csvFormat);
    }

    public String getCurrentTimeAsString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm-");
        LocalDateTime now = LocalDateTime.now();
        String result = dtf.format(now)
                .replaceAll("/", "_")
                .replaceAll(":", "_");
        return result;
    }

}
