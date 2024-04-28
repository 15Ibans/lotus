package me.steinborn.minecraft.lotus;

import lilypad.client.connect.api.ConnectSettings;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

import java.net.InetSocketAddress;
import java.util.List;

@ConfigSerializable
public class LotusConfig implements ConnectSettings {
    private static final ObjectMapper<LotusConfig> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.factory().get(LotusConfig.class); // We hold on to the instance of our ObjectMapper
        } catch (SerializationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public LotusConfig() {
    }

    public static LotusConfig loadFrom(ConfigurationNode node) throws SerializationException {
        return MAPPER.load(node);
    }

    @Setting(value = "connect-host")
    private String connectHost;

    @Setting(value = "connect-port")
    private int connectPort;

    @Setting(value = "connect-username")
    private String connectUsername;

    @Setting(value = "connect-password")
    private String connectPassword;

    @Setting(value = "initial-route")
    private List<String> initialRoute;

    @Setting(value = "use-max-players-from-proxy")
    private boolean useMaxPlayersFromProxy = false;

    @Setting(value = "messages")
    private LotusMessages messages = new LotusMessages();

    @Override
    public InetSocketAddress getOutboundAddress() {
        return new InetSocketAddress(connectHost, connectPort);
    }

    @Override
    public String getUsername() {
        return connectUsername;
    }

    @Override
    public String getPassword() {
        return connectPassword;
    }

    public List<String> getInitialRoute() {
        return initialRoute;
    }

    public boolean isUseMaxPlayersFromProxy() {
        return useMaxPlayersFromProxy;
    }

    public LotusMessages getMessages() {
        return messages;
    }

    @ConfigSerializable
    public static class LotusMessages {
        @Setting(value = "unable-to-register")
        private String unableToRegister = "&cUnable to register you with the network, try again later";

        @Setting(value = "server-fallback")
        private String serverFallback = "&cOops, we encountered a problem with the server you were on! You have been sent to the lobby.";

        public String getUnableToRegister() {
            return unableToRegister;
        }

        public String getServerFallback() {
            return serverFallback;
        }
    }
}
