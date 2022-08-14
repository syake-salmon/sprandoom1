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
@Table(name = "sub")
@NamedQuery(name = Sub.FIND_ALL, query = "SELECT s FROM Sub s")
public class Sub extends MultilingualEntityBase {
    public static final String FIND_ALL = "Sub.FIND_ALL";

    @Id
    @Column(name = "sub_id")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private int id;

    @Column(name = "sub_jpn_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String jpnName;

    @Column(name = "sub_eng_name")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private String engName;

    @OneToMany(mappedBy = "sub")
    @Getter(onMethod_ = { @lombok.Generated })
    @Setter(onMethod_ = { @lombok.Generated })
    private List<Weapon> weapons;
}
