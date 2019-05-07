package pl.aleksl.commandLineGame.model;

import java.util.HashMap;
import java.util.Map;

public enum Weapon {
    SWORD("Sword", 1, 5),
    AXE("Axe", 2,10);


    private static final Map<String, Weapon> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Weapon> BY_NUMBER = new HashMap<>();
    private static final Map<Integer, Weapon> BY_STRENGTH_ATTACK = new HashMap<>();

    static {
        for (Weapon w : values()) {
            BY_LABEL.put(w.label, w);
            BY_NUMBER.put(w.weaponNumber, w);
            BY_STRENGTH_ATTACK.put(w.strengthAttack, w);
        }
    }

    public final String label;
    public final int weaponNumber;
    public final int strengthAttack;

    private Weapon(String label, int weaponNumber, int strengthAttack) {
        this.label = label;
        this.weaponNumber = weaponNumber;
        this.strengthAttack = strengthAttack;
    }

    public static Weapon valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Weapon valueOfWeaponNumber(int number) {
        return BY_NUMBER.get(number);
    }

    public static Weapon valueOfStrengthAttack(int weight) {
        return BY_STRENGTH_ATTACK.get(weight);
    }
}
