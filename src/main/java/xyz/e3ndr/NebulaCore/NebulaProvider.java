package xyz.e3ndr.NebulaCore;

import java.util.UUID;

import xyz.e3ndr.NebulaCore.api.AbstractLang;

public class NebulaProvider {

    public static void init() {
        new NebulaPlayer();
        new NebulaLangProvider();

    }

}

class NebulaLangProvider extends AbstractLang {

    protected NebulaLangProvider() {
        super();
    }

    @Override
    protected String getLang0(String key, UUID uuid, boolean prefix) {
        return NebulaCore.getLang(key, uuid, prefix);
    }

}
