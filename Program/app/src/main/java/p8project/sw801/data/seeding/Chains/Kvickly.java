package p8project.sw801.data.seeding.Chains;

public class Kvickly extends BaseChain {
    @Override
    public String getBrandName() {
        return "Kvickly";
    }

    @Override
    public String storeFile() {
        return "seed/kvickly.json";
    }
}
