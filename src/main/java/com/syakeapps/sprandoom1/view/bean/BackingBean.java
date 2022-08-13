package com.syakeapps.sprandoom1.view.bean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
    SecureRandom rand;
    private Weapon pickupedWeapon;

    @PostConstruct
    public void postConstruct() throws NoSuchAlgorithmException {
        bundle = ResourceBundle.getBundle("locale.Messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale(),
                Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT));

        List<WeaponClass> classes = context.getClasses();
        selectedClassIds = classes.stream().map(c -> String.valueOf(c.getId())).collect(Collectors.joining(","));

        rand = SecureRandom.getInstanceStrong();
        pickupedWeapon = context.getWeapons().get(0);

        FacesMessages.info(bundle.getString("MSG_INFO_INITIALIZED"));
    }

    @Transactional
    public void randomizeWeapon() {
        /* class setting check */
        if (selectedClassIds == null || selectedClassIds.isEmpty()) {
            FacesMessages.warning(bundle.getString("MSG_WARN_CLASS_IS_REQUIRED"));
            return;
        }

        /* filtering by settings */
        List<Weapon> candidates = createCandidates();

        /* size check */
        if (candidates.size() == 0) {
            FacesMessages.warning(bundle.getString("MSG_WARN_NO_WEAPON_HIT"));
            return;
        }

        /* pick up a random weapon */
        pickupedWeapon = pickupRandom(candidates);
    }

    private List<Weapon> createCandidates() {
        List<Weapon> candidate = new ArrayList<>();

        List<Integer> convertedIds = Arrays.asList(selectedClassIds.split(",")).stream().map(Integer::valueOf).toList();

        if (convertedIds.size() == context.getClasses().size()) {
            candidate = context.getWeapons();
        } else {
            List<WeaponClass> classes = em.createNamedQuery(WeaponClass.FIND_BY_IDS, WeaponClass.class)
                    .setParameter("ids", convertedIds).getResultList();

            for (int i = 0; i < classes.size(); i++) {
                candidate.addAll(classes.get(i).getWeapons());
            }
        }

        /* filtering by sub */
        if (selectedSubId != 0) {
            candidate = candidate.stream().filter(w -> w.getSub().getId() == selectedSubId)
                    .collect(Collectors.toList());
        }

        /* filtering by special */
        if (selectedSpecialId != 0) {
            candidate = candidate.stream().filter(w -> w.getSpecial().getId() == selectedSpecialId)
                    .collect(Collectors.toList());
        }

        return candidate;
    }

    private <T> T pickupRandom(List<T> candidates) {
        return candidates.get(rand.nextInt(candidates.size()));
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
