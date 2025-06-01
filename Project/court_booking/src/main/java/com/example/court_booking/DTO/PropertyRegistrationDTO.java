package com.example.Court_Booking.DTO;

public class PropertyRegistrationDTO {
    private Long propertyID;
    private Long user_id;
    private String propertyName;
    private Integer noofCourts;
    private String timings;;
    private Double hourlyRates;
    public PropertyRegistrationDTO() {}
    public PropertyRegistrationDTO(String propertyName, Integer noofCourts,String timings, Double hourlyRates) {
        this.propertyName = propertyName;
        this.noofCourts = noofCourts;
        this.timings = timings;
        this.hourlyRates = hourlyRates;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getPropertyID() {
        return propertyID;
    }
    public void setPropertyID(Long propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getNoofCourts() {
        return noofCourts;
    }

    public void setNoofCourts(Integer noofCourts) {
        this.noofCourts = noofCourts;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public Double getHourlyRates() {
        return hourlyRates;
    }

    public void setHourlyRates(Double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }
}
