package xyz.Extencion.extencion.modules.impl.player;

import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventPushBlock;
import  xyz.Extencion.extencion.event.impl.EventPushPlayer;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;

public class NoPush extends Module {

    public NoPush() {
        super("NoPush", "Убирает отталкивания от игроков/блоков", Category.Player, true, false, false);
    }

    @EventTarget
    public void onPushPlayer(EventPushPlayer eventPushPlayer) {
        eventPushPlayer.cancel();
    }
    @EventTarget
    public void onPushBlock(EventPushBlock eventPushBlock) {
        eventPushBlock.cancel();
    }
}