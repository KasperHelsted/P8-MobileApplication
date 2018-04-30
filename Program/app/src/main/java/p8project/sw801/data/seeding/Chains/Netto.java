package p8project.sw801.data.seeding.Chains;

public class Netto extends BaseChain {
    @Override
    public String getBrandName() {
        return "Netto";
    }

    @Override
    public String storeFile() {
        return "seed/netto.json";
    }
}
