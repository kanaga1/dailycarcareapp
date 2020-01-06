package com.example.myapplication;

public class history {
    public String Date,monthly_pay,total_wash,completed,balance,address,paid;

    public history(String Date,String monthly_pay,String address,String total_wash,String paid,String completed,String balance)
    {
            this.Date = Date;
            this.monthly_pay=monthly_pay;
            this.total_wash=total_wash;
            this.completed=completed;
            this.balance=balance;
            this.address=address;
            this.paid=paid;

    }

    public String getDate() {

        return Date;
    }

    public String setDate(String date) {
        Date = date;
        return Date;
    }

    public String getMonthly_pay() {
        return monthly_pay;
    }

    public void setMonthly_pay(String monthly_pay) {
        this.monthly_pay = monthly_pay;
    }

    public String getTotal_wash() {
        return total_wash;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public void setTotal_wash(String total_wash) {
        this.total_wash = total_wash;
    }

    public String getCompleted() {
        return completed;
    }

    public String getBalance() {
        return balance;
    }

    public String getAddress() {
        return address;
    }

    public String getPaid() {
        return paid;
    }
}
