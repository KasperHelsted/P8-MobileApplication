package p8project.sw801.data.seeding.Chains;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import p8project.sw801.data.model.db.Chain;
import p8project.sw801.data.model.db.Store;
import p8project.sw801.utils.CommonUtils;

public abstract class BaseChain {
    public String getBrandName() {
        return null;
    }

    public String storeFile() {
        return null;
    }

    public Chain chain() {
        Chain chain = new Chain();

        chain.setBrandName(getBrandName());
        chain.setActive(true);

        return chain;
    }

    public List<Store> stores(Context context) {
        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Store.class);

        try {
            return new Gson().fromJson(CommonUtils.loadJSONFromAsset(context, storeFile()), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
