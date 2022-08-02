package com.syakeapps.sprandoom1.jpa.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "special")
@NamedQuery(name = Special.FIND_ALL, query = "SELECT s FROM Special s")
public class Special extends MultilingualEntityBase {
    public static final String FIND_ALL = "Special.FIND_ALL";

    @Id
    @Column(name = "spc_id")
    private int id;

    @Column(name = "spc_jpn_name")
    private String jpnName;

    @Column(name = "spc_eng_name")
    private String engName;

    @OneToMany
    @JoinColumn(name = "special")
    private List<Weapon> weapons;

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

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
