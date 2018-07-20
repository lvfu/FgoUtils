package com.fgo.utils.bean;

public class SourcesPlanBean {

    private String SourceName;
    private int SourceCount;

    private int SourceCountHave;
    private String SourceImage;

    private boolean isExpand;


    public int getSourceCountHave() {
        return SourceCountHave;
    }

    public void setSourceCountHave(int sourceCountHave) {
        SourceCountHave = sourceCountHave;
    }

    public String getSourceImage() {
        return SourceImage;
    }

    public void setSourceImage(String sourceImage) {
        SourceImage = sourceImage;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public int getSourceCount() {
        return SourceCount;
    }

    public void setSourceCount(int sourceCount) {
        SourceCount = sourceCount;
    }

}
