package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Shopping {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "fotex")
    private boolean fotex;

    @ColumnInfo(name = "netto")
    private boolean netto;

    @ColumnInfo(name = "bilka")
    private boolean bilka;

    @ColumnInfo(name = "salling")
    private boolean salling;

    @ColumnInfo(name = "meny")
    private boolean meny;

    @ColumnInfo(name = "spar")
    private boolean spar;

    @ColumnInfo(name = "minKobmand")
    private boolean minKobmand;

    @ColumnInfo(name = "letKob")
    private boolean letKob;

    @ColumnInfo(name = "kvickly")
    private boolean kickly;

    @ColumnInfo(name = "superBrugsen")
    private boolean superBrugsen;

    @ColumnInfo(name = "dagliBrugsen")
    private boolean dagliBrugsen;

    @ColumnInfo(name = "lokalBrugsen")
    private boolean lokalBrugsen;

    @ColumnInfo(name = "irma")
    private boolean irma;

    @ColumnInfo(name = "fakta")
    private boolean fakta;

    @ColumnInfo(name = "faktaQ")
    private boolean faktaQ;

    @ColumnInfo(name = "aldi")
    private boolean aldi;

    @ColumnInfo(name = "lidl")
    private boolean lidl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFotex() {
        return fotex;
    }

    public void setFotex(boolean fotex) {
        this.fotex = fotex;
    }

    public boolean isNetto() {
        return netto;
    }

    public void setNetto(boolean netto) {
        this.netto = netto;
    }

    public boolean isBilka() {
        return bilka;
    }

    public void setBilka(boolean bilka) {
        this.bilka = bilka;
    }

    public boolean isSalling() {
        return salling;
    }

    public void setSalling(boolean salling) {
        this.salling = salling;
    }

    public boolean isMeny() {
        return meny;
    }

    public void setMeny(boolean meny) {
        this.meny = meny;
    }

    public boolean isSpar() {
        return spar;
    }

    public void setSpar(boolean spar) {
        this.spar = spar;
    }

    public boolean isMinKobmand() {
        return minKobmand;
    }

    public void setMinKobmand(boolean minKobmand) {
        this.minKobmand = minKobmand;
    }

    public boolean isLetKob() {
        return letKob;
    }

    public void setLetKob(boolean letKob) {
        this.letKob = letKob;
    }

    public boolean isKickly() {
        return kickly;
    }

    public void setKickly(boolean kickly) {
        this.kickly = kickly;
    }

    public boolean isSuperBrugsen() {
        return superBrugsen;
    }

    public void setSuperBrugsen(boolean superBrugsen) {
        this.superBrugsen = superBrugsen;
    }

    public boolean isDagliBrugsen() {
        return dagliBrugsen;
    }

    public void setDagliBrugsen(boolean dagliBrugsen) {
        this.dagliBrugsen = dagliBrugsen;
    }

    public boolean isLokalBrugsen() {
        return lokalBrugsen;
    }

    public void setLokalBrugsen(boolean lokalBrugsen) {
        this.lokalBrugsen = lokalBrugsen;
    }

    public boolean isIrma() {
        return irma;
    }

    public void setIrma(boolean irma) {
        this.irma = irma;
    }

    public boolean isFakta() {
        return fakta;
    }

    public void setFakta(boolean fakta) {
        this.fakta = fakta;
    }

    public boolean isFaktaQ() {
        return faktaQ;
    }

    public void setFaktaQ(boolean faktaQ) {
        this.faktaQ = faktaQ;
    }

    public boolean isAldi() {
        return aldi;
    }

    public void setAldi(boolean aldi) {
        this.aldi = aldi;
    }

    public boolean isLidl() {
        return lidl;
    }

    public void setLidl(boolean lidl) {
        this.lidl = lidl;
    }

    /*public Shopping(Integer id, boolean fotex, boolean netto, boolean bilka, boolean salling, boolean meny, boolean spar, boolean minKobmand, boolean letKob, boolean kickly, boolean superBrugsen, boolean dagliBrugsen, boolean lokalBrugsen, boolean irma, boolean fakta, boolean faktaQ, boolean aldi, boolean lidl) {
        this.id = id;
        this.fotex = fotex;
        this.netto = netto;
        this.bilka = bilka;
        this.salling = salling;
        this.meny = meny;
        this.spar = spar;
        this.minKobmand = minKobmand;
        this.letKob = letKob;
        this.kickly = kickly;
        this.superBrugsen = superBrugsen;
        this.dagliBrugsen = dagliBrugsen;
        this.lokalBrugsen = lokalBrugsen;
        this.irma = irma;
        this.fakta = fakta;
        this.faktaQ = faktaQ;
        this.aldi = aldi;
        this.lidl = lidl;
    }  */
}
