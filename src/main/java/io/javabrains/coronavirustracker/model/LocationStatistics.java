package io.javabrains.coronavirustracker.model;
/*
 * Class to keep record of total cases reported by each country and its state and makes required calculation.
 */
public class LocationStatistics {
    private String state;
    private String country;
    private int totalNewCases;
    private int diffFromPrevDay;

    public String getState() {
        return state;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int  getTotalNewCases() {
        return totalNewCases;
    }

    public void setTotalNewCases(int totalNewCases) {
        this.totalNewCases = totalNewCases;
    }

    @Override
    public String toString() {
        return "LocationStatistics{" + "state='" + state + '\'' + ", country='" + country + ", totalNewCases=" + totalNewCases + }';
    }
}
