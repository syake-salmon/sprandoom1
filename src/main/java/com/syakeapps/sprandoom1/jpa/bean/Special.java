package com.syakeapps.sprandoom1.jpa.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Setter;

@Entity
@Table(name = "special")
@NamedQuery(name = Special.FIND_ALL, query = "SELECT s FROM Special s")
public class Special extends MultilingualEntityBase {
    public static final String FIND_ALL = "Special.FIND_ALL";

    @Id
    @Column(name = "spc_id")
    @Setter(onMethod_ = { @lombok.Generated })
    private int id;

    @Column(name = "spc_jpn_name")
    @Setter(onMethod_ = { @lombok.Generated })
    private String jpnName;

    @Column(name = "spc_eng_name")
    @Setter(onMethod_ = { @lombok.Generated })
    private String engName;

    @OneToMany
    @JoinColumn(name = "special")
    @Setter(onMethod_ = { @lombok.Generated })
    private List<Weapon> weapons;

    public int getId() {
        return id;
    }

    public String getJpnName() {
        return jpnName;
    }

    public String getEngName() {
        return engName;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }
}
