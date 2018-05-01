package p8project.sw801.data.seeding.Chains;

public class MyShopMan extends BaseChain {
    @Override
    public String getBrandName() {
        return "MIN KØBMAND";
    }

    @Override
    public String storeFile() {
        return "seed/my_shop_man.json";
    }
}
