package p8project.sw801.data.seeding.Chains;

public class Fakta extends BaseChain {
    @Override
    public String getBrandName() {
        return "Fakta";
    }

    @Override
    public String storeFile() {
        return "seed/fakta.json";
    }
}
