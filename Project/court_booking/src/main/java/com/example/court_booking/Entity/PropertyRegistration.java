package com.example.Court_Booking.Entity;

import jakarta.persistence.*;

@Entity
public class PropertyRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long property_id;
    private String property_name;
    private Integer noOfCourts;
    private String timings;
    private Double hourlyRate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public PropertyRegistration() {}

    public PropertyRegistration(String property_name, Integer noOfCourts, String timings, Double hourlyRate) {
        this.property_name = property_name;
        this.noOfCourts = noOfCourts;
        this.timings = timings;
        this.hourlyRate = hourlyRate;
    }

    public Long getProperty_id() {
        return property_id;
    }

    public void setProperty_id(Long property_id) {
        this.property_id = property_id;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public Integer getNoOfCourts() {
        return noOfCourts;
    }

    public void setNoOfCourts(Integer noOfCourts) {
        this.noOfCourts = noOfCourts;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
