package com.fgo.utils.bean;

import java.io.Serializable;
import java.util.List;

public class ServantListNBean implements Serializable {



        private int id;
        private String name;
        private String classType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassType() {
            return classType;
        }

        public void setClassType(String classType) {
            this.classType = classType;
        }

}
