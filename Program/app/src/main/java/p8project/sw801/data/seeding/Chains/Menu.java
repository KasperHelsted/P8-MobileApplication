package p8project.sw801.data.seeding.Chains;

public class Menu extends BaseChain {
    @Override
    public String getBrandName() {
        return "Menu";
    }

    @Override
    public String storeFile() {
        return "seed/menu.json";
    }
}
