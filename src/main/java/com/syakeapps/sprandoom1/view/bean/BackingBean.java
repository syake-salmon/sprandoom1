package com.syakeapps.sprandoom1.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
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

import net.bootsfaces.utils.FacesMessages;

@SuppressWarnings("serial")
@SessionScoped
@Named(value = "BB")
public class BackingBean implements Serializable {

    @Inject
    private ApplicationContext context;

    @PersistenceContext
    private EntityManager em;

    private ResourceBundle bundle;

    /* Randomizer User Setting */
    private String selectedClassIds;
    private int selectedSubId = 0;
    private int selectedSpecialId = 0;

    /* Randomized Weapon */
    private Weapon pickupedWeapon;

    @PostConstruct
    public void postConstruct() {
        bundle = ResourceBundle.getBundle("locale.Messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale(),
                Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT));

        List<WeaponClass> classes = context.getClasses();
        selectedClassIds = classes.stream().map(c -> {
            return String.valueOf(c.getId());
        }).collect(Collectors.joining(","));

        pickupedWeapon = context.getWeapons().get(0);

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
        if (convertedIds.size() == context.getClasses().size()) {
            weapons = context.getWeapons();
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

    /* GETTER & SETTER */
    public Locale getUserLocale() {
        return bundle.getLocale();
    }

    public String getSelectedClassIds() {
        return selectedClassIds;
    }

    public void setSelectedClassIds(String selectedClassIds) {
        this.selectedClassIds = selectedClassIds;
    }

    public int getSelectedSubId() {
        return selectedSubId;
    }

    public void setSelectedSubId(int selectedSubId) {
        this.selectedSubId = selectedSubId;
    }

    public int getSelectedSpecialId() {
        return selectedSpecialId;
    }

    public void setSelectedSpecialId(int selectedSpecialId) {
        this.selectedSpecialId = selectedSpecialId;
    }

    public Weapon getPickupedWeapon() {
        return pickupedWeapon;
    }
}
