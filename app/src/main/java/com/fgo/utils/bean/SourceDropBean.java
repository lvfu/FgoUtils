package com.fgo.utils.bean;

import java.util.List;

public class SourceDropBean {


    private String name;
    private String img;
    private List<DropInfo> dropInfoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<DropInfo> getDropInfoList() {
        return dropInfoList;
    }

    public void setDropInfoList(List<DropInfo> dropInfoList) {
        this.dropInfoList = dropInfoList;
    }

    public static class DropInfo {
        private String location,
                address,
                ap,
                dropRate;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAp() {
            return ap;
        }

        public void setAp(String ap) {
            this.ap = ap;
        }

        public String getDropRate() {
            return dropRate;
        }

        public void setDropRate(String dropRate) {
            this.dropRate = dropRate;
        }
    }
}
