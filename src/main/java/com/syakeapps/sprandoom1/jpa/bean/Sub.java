package com.syakeapps.sprandoom1.jpa.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sub")
@NamedQuery(name = Sub.FIND_ALL, query = "SELECT s FROM Sub s")
public class Sub extends MultilingualEntityBase {
    public static final String FIND_ALL = "Sub.FIND_ALL";

    @Id
    @Column(name = "sub_id")
    private int id;

    @Column(name = "sub_jpn_name")
    private String jpnName;

    @Column(name = "sub_eng_name")
    private String engName;

    @OneToMany(mappedBy = "sub")
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
