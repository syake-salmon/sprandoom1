package com.syakeapps.sprandoom1.jpa.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Setter;

@Entity
@Table(name = "class")
@NamedQueries({ @NamedQuery(name = WeaponClass.FIND_ALL, query = "SELECT c FROM WeaponClass c"),
        @NamedQuery(name = WeaponClass.FIND_BY_IDS, query = "SELECT c FROM WeaponClass c WHERE c.id IN :ids") })
public class WeaponClass extends MultilingualEntityBase {
    public static final String FIND_ALL = "WeaponClass.FIND_ALL";
    public static final String FIND_BY_IDS = "WeaponClass.FIND_BY_IDS";

    @Id
    @Column(name = "cls_id")
    @Setter(onMethod_ = { @lombok.Generated })
    private int id;

    @Column(name = "cls_jpn_name")
    @Setter(onMethod_ = { @lombok.Generated })
    private String jpnName;

    @Column(name = "cls_eng_name")
    @Setter(onMethod_ = { @lombok.Generated })
    private String engName;

    @OneToMany(mappedBy = "wpnClass")
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
