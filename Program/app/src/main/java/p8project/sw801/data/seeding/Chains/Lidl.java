package p8project.sw801.data.seeding.Chains;

public class Lidl extends BaseChain {
    @Override
    public String getBrandName() {
        return "Lidl";
    }

    @Override
    public String storeFile() {
        return "seed/lidl.json";
    }
}
