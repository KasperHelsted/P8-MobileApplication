package p8project.sw801.data.seeding.Chains;

public class Irma extends BaseChain {
    @Override
    public String getBrandName() {
        return "Irma";
    }

    @Override
    public String storeFile() {
        return "seed/irma.json";
    }
}
