public class DeezerTrack {
    public long id;
    public String title;
    public String title_short;
    public int duration;
    public long rank;
    public String releaseDate;
    public String preview;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_short() {
        return title_short;
    }

    public void setTitle_short(String title_short) {
        this.title_short = title_short;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "{" + "\n"+
                "\t'id': " + getId() + "\n"+
                "\t'title': "+getTitle()+ "\n"+
                "\t'duration': "+getDuration()+ "\n"+
                "\t'rank': "+getRank()+ "\n"+
                "\t'releaseDate': "+getReleaseDate()+ "\n"+
                "\t'preview': "+getPreview()+ "\n"+
                "}";
    }
}
