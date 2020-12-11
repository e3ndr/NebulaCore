package xyz.e3ndr.NebulaCore;

import java.util.UUID;

import xyz.e3ndr.NebulaCore.api.NebulaLang;

public class NebulaProvider {

    public static void init() {
        new PlayerImpl();
        new NebulaLangProvider();

    }

}

class NebulaLangProvider extends NebulaLang {

    protected NebulaLangProvider() {
        super();
    }

    @Override
    protected String getLang0(String key, UUID uuid, boolean prefix) {
        return NebulaCore.getLang(key, uuid, prefix);
    }

}
