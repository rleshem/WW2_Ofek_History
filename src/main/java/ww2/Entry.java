package ww2;

public class Entry {
    private String title;
    private int firstPage;
    private int lastPage;
    private boolean hasAbstract = false;
    private String keywords;
    private boolean hasKeywords = false;
    private String annotationReason = null;
    private int seqNumber;

    public String getTitle() {
        return title;
    }

    public void setTitle(String titleLine) {
        this.title = getContent(titleLine);
    }

    public int getFirstPage() {
        return firstPage;
    }

    private void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    private void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isHasAbstract() {
        return hasAbstract;
    }

    public void setHasAbstract(boolean hasAbstract) {
        this.hasAbstract = hasAbstract;
    }

    public boolean isHasKeywords() {
        return hasKeywords;
    }

    public String getAnnotationReason() {
        return annotationReason;
    }

    public void setAnnotationReason(String annotationReason) {
        this.annotationReason = annotationReason;
    }

    public void setPages(String nextLine) {
        String onlyPages = getContent(nextLine);
        String[] parts = onlyPages.split("--");
        if (parts.length != 2)
            Logger.error("pages line - first/last not found: <" + nextLine + ">");
        setFirstPage(Integer.valueOf(parts[0]));
        setLastPage(Integer.valueOf(parts[1]));
    }

    public void setKeywords(String nextLine) {
        String content = getContent(nextLine);
        if (content.length() < 1)
            Logger.error("keywords line with no content: <" + nextLine + ">");
        keywords = content;
        hasKeywords = true;
    }

    private String getContent(String nextLine) {
        int first = nextLine.indexOf('{');
        int last = nextLine.indexOf('}');
        if ((first < 0) || (last < 0))
            Logger.error("line has no proper {...} values: <" + nextLine + ">");
        String content = nextLine.substring(first + 1, last);
        return content;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "title='" + title + '\'' +
                ", seqNumber=" + seqNumber +
                ", firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", hasAbstract=" + hasAbstract +
                ", keywords='" + keywords + '\'' +
                ", hasKeywords=" + hasKeywords +
                ", annotationReason='" + annotationReason + '\'' +
                '}';
    }

    public void dump() {
        Logger.log(1, this.toString());
    }

    public void setSeqNum(int i) {
        seqNumber = i;
    }

    public int getSeqNum() {
        return seqNumber;
    }

    public void setAbstract(String pureLine) {
//        this.abstractValue = getContent(pureLine);
        this.hasAbstract = true;
    }

    public boolean getHasAbstract() {
        return hasAbstract;
    }

    public String getKeywords() {
        return keywords;
    }
}
