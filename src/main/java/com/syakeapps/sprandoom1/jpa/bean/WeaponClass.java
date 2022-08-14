package com.syakeapps.sprandoom1.jpa.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "class")
@NamedQuery(name = WeaponClass.FIND_ALL, query = "SELECT c FROM WeaponClass c")
@NamedQuery(name = WeaponClass.FIND_BY_IDS, query = "SELECT c FROM WeaponClass c WHERE c.id IN :ids")
public class WeaponClass extends MultilingualEntityBase {
    public static final String FIND_ALL = "WeaponClass.FIND_ALL";
    public static final String FIND_BY_IDS = "WeaponClass.FIND_BY_IDS";

    @Id
    @Column(name = "cls_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private int id;

    @Column(name = "cls_jpn_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String jpnName;

    @Column(name = "cls_eng_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String engName;

    @OneToMany(mappedBy = "wpnClass")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private List<Weapon> weapons;
}
