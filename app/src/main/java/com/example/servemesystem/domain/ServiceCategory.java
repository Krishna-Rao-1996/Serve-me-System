package com.example.servemesystem.domain;

public class ServiceCategory {
    private String serviceCategoryName;
    private String serviceCategoryDescription;

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public String getServiceCategoryDescription() {
        return serviceCategoryDescription;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public void setServiceCategoryDescription(String serviceCategoryDescription) {
        this.serviceCategoryDescription = serviceCategoryDescription;
    }


    @Override
    public String toString() {
        return "ServiceCategory{" +
                "serviceCategoryName='" + serviceCategoryName + '\'' +
                ", serviceCategoryDescription='" + serviceCategoryDescription + '\'' +
                '}';
    }
}
