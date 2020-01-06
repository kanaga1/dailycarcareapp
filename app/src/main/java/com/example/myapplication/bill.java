package com.example.myapplication;

public class bill {
    public String date,p_type,c_model,time,v_type;
    public bill(String date,String p_type,String  c_model,String time,String v_type){
        this.date=date;
        this.p_type=p_type;
        this.c_model=c_model;
        this.time=time;
        this.v_type=v_type;

    }

    public String getDate() {
        return date;
    }

    public String getC_model() {
        return c_model;
    }

    public String getP_type() {
        return p_type;
    }

    public String getTime() {
        return time;
    }

    public String getV_type() {
        return v_type;
    }

}
