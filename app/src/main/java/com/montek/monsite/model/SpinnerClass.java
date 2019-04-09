package com.montek.monsite.model;
public class SpinnerClass {
       public String str;
        public String id;
        public SpinnerClass(String  id, String str) {
            super();
            this.str = str;
            this.id = id;
        }
        public String getStr() {
            return str;
        }
//        public void setstr(String str) {
//            this.str = str;
//        }
        public String getid() {
            return id;
        }
//        public void setid(String id) {
//            this.id = id;
//        }
        @Override
        public String toString() {
            return str;
        }
}
