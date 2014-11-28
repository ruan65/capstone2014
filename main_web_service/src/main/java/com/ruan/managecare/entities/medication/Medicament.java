package com.ruan.managecare.entities.medication;

import org.springframework.data.annotation.Id;

public class Medicament {

    @Id
    private String _id;

    private String name;
    private String type;
    private String code;

    private int dozePerDay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDozePerDay() {
        return dozePerDay;
    }

    public void setDozePerDay(int dozePerDay) {
        this.dozePerDay = dozePerDay;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dozePerDay=" + dozePerDay +
                '}';
    }
}
