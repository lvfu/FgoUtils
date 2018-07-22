package com.fgo.utils.bean;

public class SourcesPlanBean {


    private boolean isExpand;


    private String sourceName;
    private int sourceCount;
    private int sourceCountHave;
    private String sourceImage;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(int sourceCount) {
        this.sourceCount = sourceCount;
    }

    public int getSourceCountHave() {
        return sourceCountHave;
    }

    public void setSourceCountHave(int sourceCountHave) {
        this.sourceCountHave = sourceCountHave;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
