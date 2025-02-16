package de.dafuqs.spectrum.blocks.pastel_network;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.particle.render.*;
import net.fabricmc.api.*;

public class Pastel {

    @Environment(EnvType.CLIENT)
    private static ClientPastelNetworkManager clientManager;
    private static ServerPastelNetworkManager serverManager;

    @Environment(EnvType.CLIENT)
    public static ClientPastelNetworkManager getClientInstance() {
        if (clientManager == null) {
            clientManager = new ClientPastelNetworkManager();
        }
        return clientManager;
    }

    public static ServerPastelNetworkManager getServerInstance() {
        if (serverManager == null) {
            serverManager = ServerPastelNetworkManager.get(SpectrumCommon.minecraftServer.getOverworld());
        }
        return serverManager;
    }
	
	public static PastelNetworkManager<?, ?> getInstance(boolean client) {
        if (client) {
            return getClientInstance();
        } else {
            return getServerInstance();
        }
    }
    
    @Environment(EnvType.CLIENT)
    public static void clearClientInstance() {
		getClientInstance().clear();
        EarlyRenderingParticleContainer.clear();
    }

    public static void clearServerInstance() {
        serverManager = null;
    }

}
