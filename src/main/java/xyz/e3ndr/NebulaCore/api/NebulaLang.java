package xyz.e3ndr.NebulaCore.api;

import java.util.UUID;

public abstract class NebulaLang {
    private static NebulaLang provider;

    protected NebulaLang() {
        if (provider == null) provider = this;
    }

    public static String getLang(String key, UUID uuid, boolean prefix) {
        return provider.getLang0(key, uuid, prefix);
    }

    protected abstract String getLang0(String key, UUID uuid, boolean prefix);

}
