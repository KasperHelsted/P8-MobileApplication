package p8project.sw801.data.seeding.Chains;

public class Lokalbrugsen extends BaseChain {
    @Override
    public String getBrandName() {
        return "Lokalbrugsen";
    }

    @Override
    public String storeFile() {
        return "seed/lokal_brugsen.json";
    }
}
