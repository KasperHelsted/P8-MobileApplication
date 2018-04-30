package p8project.sw801.data.seeding;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.seeding.Chains.BaseChain;
import p8project.sw801.data.seeding.Chains.Bilka;
import p8project.sw801.data.seeding.Chains.DagliBrugsen;
import p8project.sw801.data.seeding.Chains.Fakta;
import p8project.sw801.data.seeding.Chains.Fotex;
import p8project.sw801.data.seeding.Chains.Irma;
import p8project.sw801.data.seeding.Chains.Kvickly;
import p8project.sw801.data.seeding.Chains.Lidl;
import p8project.sw801.data.seeding.Chains.Lokalbrugsen;
import p8project.sw801.data.seeding.Chains.Menu;
import p8project.sw801.data.seeding.Chains.MyShopMan;
import p8project.sw801.data.seeding.Chains.Netto;
import p8project.sw801.data.seeding.Chains.Salling;
import p8project.sw801.data.seeding.Chains.Spar;
import p8project.sw801.data.seeding.Chains.SuperBrugsen;

public class Seeder {
    public List<BaseChain> baseChains = new ArrayList<>();

    public Seeder() {
        baseChains.add(new Bilka());
        baseChains.add(new DagliBrugsen());
        baseChains.add(new Fakta());
        baseChains.add(new Fotex());
        baseChains.add(new Irma());
        baseChains.add(new Kvickly());
        baseChains.add(new Lidl());
        baseChains.add(new Lokalbrugsen());
        baseChains.add(new Menu());
        baseChains.add(new MyShopMan());
        baseChains.add(new Netto());
        baseChains.add(new Salling());
        baseChains.add(new Spar());
        baseChains.add(new SuperBrugsen());
    }
}
