package com.syakeapps.sprandoom1.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.syakeapps.sprandoom1.jpa.bean.Weapon;
import com.syakeapps.sprandoom1.jpa.bean.WeaponClass;

import lombok.Getter;
import lombok.Setter;
import net.bootsfaces.utils.FacesMessages;

@SuppressWarnings("serial")
@SessionScoped
@Named(value = "BB")
public class BackingBean implements Serializable {

    @Inject
    private ApplicationContext cxt;

    @PersistenceContext(unitName = "sprandoom1PU")
    private EntityManager em;

    private ResourceBundle bundle;

    /* Randomizer User Setting */
    @Getter
    @Setter
    private String selectedClassIds;

    @Getter
    @Setter
    private int selectedSubId = 0;

    @Getter
    @Setter
    private int selectedSpecialId = 0;

    /* Randomized Weapon */
    @Getter
    private Weapon pickupedWeapon;

    @PostConstruct
    private void initialize() {
        bundle = ResourceBundle.getBundle("locale.Messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());

        List<WeaponClass> classes = cxt.getClasses();
        selectedClassIds = classes.stream().map(c -> {
            return String.valueOf(c.getId());
        }).collect(Collectors.joining(","));

        pickupedWeapon = em.find(Weapon.class, 1);

        FacesMessages.info(bundle.getString("MSG_INFO_INITIALIZED"));
    }

    @Transactional
    public void randomizeWeapon() {
        /* filtering by class */
        if (selectedClassIds == null || selectedClassIds.isEmpty()) {
            FacesMessages.warning(bundle.getString("MSG_WARN_CLASS_IS_REQUIRED"));
            return;
        }

        List<Integer> convertedIds = Arrays.asList(selectedClassIds.split(",")).stream().map(strId -> {
            return Integer.valueOf(strId);
        }).collect(Collectors.toList());

        List<Weapon> weapons = new ArrayList<>();
        if (convertedIds.size() == cxt.getClasses().size()) {
            weapons = em.createNamedQuery(Weapon.FIND_ALL, Weapon.class).getResultList();
        } else {
            List<WeaponClass> classes = em.createNamedQuery(WeaponClass.FIND_BY_IDS, WeaponClass.class)
                    .setParameter("ids", convertedIds).getResultList();
            for (int i = 0; i < classes.size(); i++) {
                weapons.addAll(classes.get(i).getWeapons());
            }
        }

        /* filtering by sub */
        if (selectedSubId != 0) {
            Iterator<Weapon> i = weapons.iterator();
            while (i.hasNext()) {
                Weapon w = i.next();
                if (w.getSub().getId() != selectedSubId) {
                    i.remove();
                }
            }
        }

        /* filtering by special */
        if (selectedSpecialId != 0) {
            Iterator<Weapon> i = weapons.iterator();
            while (i.hasNext()) {
                Weapon w = i.next();
                if (w.getSpecial().getId() != selectedSpecialId) {
                    i.remove();
                }
            }
        }

        /* size check */
        if (weapons.size() == 0) {
            FacesMessages.warning(bundle.getString("MSG_WARN_NO_WEAPON_HIT"));
            return;
        }

        /* pick a random number */
        int r = new Random().nextInt(weapons.size());
        pickupedWeapon = weapons.get(r);
    }

    public Locale getUserLocale() {
        return bundle.getLocale();
    }
}
