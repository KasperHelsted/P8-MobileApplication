package p8project.sw801.data.seeding.Chains;

public class Spar extends BaseChain {
    @Override
    public String getBrandName() {
        return "SPAR";
    }

    @Override
    public String storeFile() {
        return "seed/spar.json";
    }
}
