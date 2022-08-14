package com.syakeapps.sprandoom1.jpa.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "weapon")
@NamedQuery(name = Weapon.FIND_ALL, query = "SELECT w FROM Weapon w")
public class Weapon extends MultilingualEntityBase {
    public static final String FIND_ALL = "Weapon.FIND_ALL";

    @Id
    @Column(name = "wpn_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private int id;

    @Column(name = "wpn_jpn_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String jpnName;

    @Column(name = "wpn_eng_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String engName;

    @ManyToOne
    @JoinColumn(name = "wpn_category_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private WeaponClass wpnClass;

    @ManyToOne
    @JoinColumn(name = "wpn_sub_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private Sub sub;

    @ManyToOne
    @JoinColumn(name = "wpn_special_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private Special special;
}
