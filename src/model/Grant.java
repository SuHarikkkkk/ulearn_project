package model;

public class Grant {
    //создаём поля по названиям колонок из CSV-файла
    private String companyName;
    private String streetName;
    private double grantAmount;
    private Integer fiscalYear;
    private String businessType;
    private Integer jobsCreated;

    public Grant(String companyName, String streetName, double grantAmount, Integer fiscalYear, String businessType, Integer jobsCreated) {
        this.companyName = companyName;
        this.streetName = streetName;
        this.grantAmount = grantAmount;
        this.fiscalYear = fiscalYear;
        this.businessType = businessType;
        this.jobsCreated = jobsCreated;
    }

    //геттеры
    public String getCompanyName() {return companyName;}
    public String getStreetName() {return streetName;}
    public double getGrantAmount() {return grantAmount;}
    public Integer getFiscalYear() {return fiscalYear;}
    public String getBusinessType() {return businessType;}
    public Integer getJobsCreated() {return jobsCreated;}
}
