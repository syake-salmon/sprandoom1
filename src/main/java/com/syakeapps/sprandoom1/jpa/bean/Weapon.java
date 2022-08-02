package com.syakeapps.sprandoom1.jpa.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "weapon")
@NamedQuery(name = Weapon.FIND_ALL, query = "SELECT w FROM Weapon w")
public class Weapon extends MultilingualEntityBase {
    public static final String FIND_ALL = "Weapon.FIND_ALL";

    @Id
    @Column(name = "wpn_id")
    private int id;

    @Column(name = "wpn_jpn_name")
    private String jpnName;

    @Column(name = "wpn_eng_name")
    private String engName;

    @ManyToOne
    @JoinColumn(name = "wpn_category_id")
    private WeaponClass wpnClass;

    @ManyToOne
    @JoinColumn(name = "wpn_sub_id")
    private Sub sub;

    @ManyToOne
    @JoinColumn(name = "wpn_special_id")
    private Special special;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJpnName() {
        return jpnName;
    }

    public void setJpnName(String jpnName) {
        this.jpnName = jpnName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public WeaponClass getCategory() {
        return wpnClass;
    }

    public void setCategory(WeaponClass wpnClass) {
        this.wpnClass = wpnClass;
    }

    public Sub getSub() {
        return sub;
    }

    public void setSub(Sub sub) {
        this.sub = sub;
    }

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "Weapon [" + jpnName + "]";
    }
}
