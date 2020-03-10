package com.example.servemesystem.domain;

public class ServiceRequests {
    String Description;
    String ServiceProblemImage;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getServiceProblemImage() {
        return ServiceProblemImage;
    }

    public void setServiceProblemImage(String serviceProblemImage) {
        ServiceProblemImage = serviceProblemImage;
    }

    @Override
    public String toString() {
        return "ServiceRequests{" +
                "Description='" + Description + '\'' +
                ", ServiceProblemImage='" + ServiceProblemImage + '\'' +
                '}';
    }



}
