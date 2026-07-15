package sqyro.classessmp.core;

public interface PlayerClassHolder {
    PlayerClass getPlayerClass();

    void setPlayerClass(PlayerClass playerClass);

    String getSavedClassID();

    void setSavedClassID(String ID);
}