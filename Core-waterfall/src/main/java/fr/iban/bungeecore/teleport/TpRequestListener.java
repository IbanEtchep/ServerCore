package fr.iban.bungeecore.teleport;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.RequestType;
import fr.iban.common.teleport.TpRequest;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.redisson.api.listener.MessageListener;

public class TpRequestListener implements MessageListener<TpRequest> {

    private final CoreBungeePlugin plugin;

    public TpRequestListener(CoreBungeePlugin coreBungeePlugin) {
        this.plugin = coreBungeePlugin;
    }

    @Override
    public void onMessage(CharSequence channel, TpRequest request) {
        ProxiedPlayer from = plugin.getProxy().getPlayer(request.getPlayerFrom());
        ProxiedPlayer to = plugin.getProxy().getPlayer(request.getPlayerTo());

        if(request.getRequestType() == RequestType.TP){
            plugin.getTeleportManager().sendTeleportRequest(from, to);
        }else if(request.getRequestType() == RequestType.TPHERE){
            plugin.getTeleportManager().sendTeleportHereRequest(from, to);
        }
    }
}
