package com.syakeapps.sprandoom1.view.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.syakeapps.sprandoom1.jpa.bean.Special;
import com.syakeapps.sprandoom1.jpa.bean.Sub;
import com.syakeapps.sprandoom1.jpa.bean.WeaponClass;

import lombok.Getter;

@ApplicationScoped
@Named(value = "APP")
public class ApplicationContext {
    private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class.getName());

    @PersistenceContext(unitName = "sprandoom1PU")
    private EntityManager em;

    /* CONST */
    @Getter
    private List<WeaponClass> classes;

    @Getter
    private List<Sub> subs;

    @Getter
    private List<Special> specials;

    @PostConstruct
    private void initialize() {
        LOGGER.info("Initializing application.");
        classes = em.createNamedQuery(WeaponClass.FIND_ALL, WeaponClass.class).getResultList();
        subs = em.createNamedQuery(Sub.FIND_ALL, Sub.class).getResultList();
        specials = em.createNamedQuery(Special.FIND_ALL, Special.class).getResultList();
        LOGGER.info("Initializing application is done.");
    }

    public List<WeaponClass> getClasses() {
        return classes;
    }
}
