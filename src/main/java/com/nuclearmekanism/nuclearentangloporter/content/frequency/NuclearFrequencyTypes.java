package com.nuclearmekanism.nuclearentangloporter.content.frequency;

import com.mojang.serialization.Codec;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import mekanism.api.security.SecurityMode;
import mekanism.common.lib.frequency.FrequencyManagerWrapper;
import mekanism.common.lib.frequency.FrequencyType;
import mekanism.common.lib.frequency.IdentitySerializer;
import net.minecraft.network.codec.StreamCodec;

/**
 * Reflection-backed registration of the nuclear frequency type so we can coexist with Mekanism's private registry API.
 */
public final class NuclearFrequencyTypes {

    public static final FrequencyType<NuclearInventoryFrequency> NUCLEAR_INVENTORY = register();

    private NuclearFrequencyTypes() {
    }

    @SuppressWarnings("unchecked")
    private static FrequencyType<NuclearInventoryFrequency> register() {
        try {
            Class<?> frequencyConstructorClass = Class.forName("mekanism.common.lib.frequency.FrequencyType$FrequencyConstructor");
            Method registerMethod = FrequencyType.class.getDeclaredMethod(
                  "register",
                  String.class,
                  frequencyConstructorClass,
                  Codec.class,
                  StreamCodec.class,
                  FrequencyManagerWrapper.Type.class,
                  IdentitySerializer.class
            );
            registerMethod.setAccessible(true);
            Object constructorProxy = Proxy.newProxyInstance(
                  NuclearFrequencyTypes.class.getClassLoader(),
                  new Class<?>[]{frequencyConstructorClass},
                  (proxy, method, args) -> {
                      if ("create".equals(method.getName()) && args.length == 3) {
                          Object key = args[0];
                          UUID owner = (UUID) args[1];
                          SecurityMode security = (SecurityMode) args[2];
                          return new NuclearInventoryFrequency((String) key, owner, security);
                      }
                      throw new UnsupportedOperationException("Unknown method: " + method);
                  }
            );
            return (FrequencyType<NuclearInventoryFrequency>) registerMethod.invoke(
                  null,
                  "NuclearInventory",
                  constructorProxy,
                  NuclearInventoryFrequency.CODEC,
                  NuclearInventoryFrequency.STREAM_CODEC,
                  FrequencyManagerWrapper.Type.PUBLIC_PRIVATE_TRUSTED,
                  IdentitySerializer.NAME
            );
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to register nuclear inventory frequency", e);
        }
    }
}
