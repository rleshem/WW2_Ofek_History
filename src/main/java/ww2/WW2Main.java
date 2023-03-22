package ww2;

import ww2.util.Setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WW2Main {
    public static void main(String[] args) throws IOException {
        Loader loader = Loader.getLoader();
        List<Entry> entries = new ArrayList<>();
        int seqEntry = 0;
        while (true) {
            Entry entry = loader.loadEntry();
            if (entry == null) {
                Logger.log(0, "loaded [" + entries.size() + "] entries");
                ConvertBibToCsv convertBibToCsv = new ConvertBibToCsv();
                convertBibToCsv.exportEntries(entries);
                System.exit(0);
            }
            entry.setSeqNum(seqEntry++);
            entry.dump();
            entries.add(entry);
        }



//        Entry entry =
    }

}
