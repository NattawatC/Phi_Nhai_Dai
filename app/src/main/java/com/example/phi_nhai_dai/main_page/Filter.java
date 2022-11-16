package com.example.phi_nhai_dai.main_page;

public class Filter {
    private String category;
    private String value;

    public Filter(String category, String value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
