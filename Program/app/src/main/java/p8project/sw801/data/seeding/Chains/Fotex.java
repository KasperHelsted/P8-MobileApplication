package p8project.sw801.data.seeding.Chains;

public class Fotex extends BaseChain {
    @Override
    public String getBrandName() {
        return "Føtex";
    }

    @Override
    public String storeFile() {
        return "seed/fotex.json";
    }
}
