package com.doubleclick.doctor.model;

import java.util.Objects;

public class F1 {

    private String F1;


    public String getF1() {
        return F1;
    }

    public void setF1(String f1) {
        F1 = f1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof F1)) return false;
        F1 f1 = (F1) o;
        return getF1().equals(f1.getF1());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getF1());
    }
}
