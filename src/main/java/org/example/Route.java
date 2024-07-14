package org.example;

import java.util.Arrays;

public class Route {
    private int id;
    private String name;
    private double lowPrice;
    private String startTime;
    private String endTime;
    private String interval;
    private String company;
    private double highPrice;
    private String[] stations; // 用于存储站点的字符串数组
    public String[] getStations() {
        return stations;
    }

    public void setStations(String[] stations) {
        this.stations = stations;
    }

    // 构造函数
    public Route() {
    }

    // getId 和 setId
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // getName 和 setName
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getLowPrice 和 setLowPrice
    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    // getStartTime 和 setStartTime
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    // getEndTime 和 setEndTime
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // getInterval 和 setInterval
    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    // getCompany 和 setCompany
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    // getHighPrice 和 setHighPrice
    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    // 可选：添加一个 toString 方法来打印 Route 对象的信息，便于调试
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lowPrice=" + lowPrice +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", interval='" + interval + '\'' +
                ", company='" + company + '\'' +
                ", highPrice=" + highPrice +
                ", stations=" + Arrays.toString(stations) +
                '}';
    }

    // 如果需要，可以添加其他辅助方法，例如 equals, hashCode 等
}