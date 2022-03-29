package com.example;

public class Rectangle {
    private String name;
    private String color;
    private Float height;
    private Float width;
    private Integer ID;

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public Float getHeight() {
        return this.height;
    }

    public Float getWidth() {
        return this.width;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getID2() {
        String ID2 = Integer.toString(ID);
        return ID2;
    }

    public void setName(String N) {
        this.name = N;
    }

    public void setColor(String C) {
        this.color = C;
    }

    public void setHeight(Float H) {
        this.height = H;
    }

    public void setWidth(Float H) {
        this.width = H;
    }

    public void setID(Integer I) {
        this.ID = I;
    }


}
