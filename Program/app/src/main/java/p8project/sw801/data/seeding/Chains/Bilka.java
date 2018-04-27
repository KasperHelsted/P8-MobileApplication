package p8project.sw801.data.seeding.Chains;

public class Bilka extends BaseChain {
    @Override
    public String getBrandName() {
        return "Bilka";
    }

    @Override
    public String storeFile() {
        return "seed/bilka.json";
    }
}
