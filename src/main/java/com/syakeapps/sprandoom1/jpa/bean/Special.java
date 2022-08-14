package com.syakeapps.sprandoom1.jpa.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "special")
@NamedQuery(name = Special.FIND_ALL, query = "SELECT s FROM Special s")
public class Special extends MultilingualEntityBase {
    public static final String FIND_ALL = "Special.FIND_ALL";

    @Id
    @Column(name = "spc_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private int id;

    @Column(name = "spc_jpn_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String jpnName;

    @Column(name = "spc_eng_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String engName;

    @OneToMany
    @JoinColumn(name = "special")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private List<Weapon> weapons;
}
