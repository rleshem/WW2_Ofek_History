package ww2;

import ww2.util.Setup;

import java.io.*;

public class Loader {

    private static Loader loader;
    private BufferedReader reader;
    private int numLines = 0;
    private boolean fileExhausted = false;

    public Entry loadEntry() throws IOException {
        String lookFor = null;
        String pureLine;
        String nextLine = skipToBibLine("@incollection");
        if (nextLine == null)
            return null;
        Entry entry = new Entry();
        while (true) {
            pureLine = nextLine.trim().replaceAll("\"", "");
            if (pureLine.equals("}")) // last line in entry
                return entry;
            if (pureLine.startsWith("title"))
                entry.setTitle(pureLine);
            else if (pureLine.startsWith("pages"))
                entry.setPages(pureLine);
            else if (pureLine.startsWith("keywords"))
                entry.setKeywords(pureLine);
            else if (pureLine.startsWith("abstract")) {
                entry.setAbstract(pureLine);
                lookFor = "},";
            }
            else if (pureLine.startsWith("annote"))
                entry.setAnnotationReason(pureLine);
            if (lookFor != null) {
                nextLine = readToBibLineAfter(lookFor);
                lookFor = null;
            }
            else
                nextLine = readBibLine();
        }
    }

    private String readToBibLineAfter(String lookFor) {
        while (true) {
            String line = readBibLine();
            if (line == null)
                return null;
            if (line.endsWith(lookFor))
                return readBibLine();
        }
    }

    private String skipToBibLine(String required) {
        while (true) {
            String line = readBibLine();
            if (line == null)
                return null;
            if (!line.startsWith(required))
                return line;
        }
    }

    private String readBibLine() {
        while (true) {
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                Logger.log(1, "file exhausted after " + numLines + " lines");
                fileExhausted = true;
                return null;
            }
            numLines++;
            if (line.length() < 1)  // skip empty lines
                continue;
            return line;
        }
    }

    public static Loader getLoader() {
        if (loader == null) {
            loader = new Loader();
            try {
                loader.initLoad();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return loader;
    }

    public void initLoad() throws FileNotFoundException {
        String fileName = Setup.getSetup().getStringProperty(Setup.dataFile);
        File ww2BibFile = new File(fileName);
        if (!ww2BibFile.exists()) {
            Logger.error("file <" + fileName + "> not found");
            System.exit(-1);
        } else
            Logger.log(1, "opened <" + fileName + "> OK!");
        reader = new BufferedReader(new FileReader(ww2BibFile));
    }

}
