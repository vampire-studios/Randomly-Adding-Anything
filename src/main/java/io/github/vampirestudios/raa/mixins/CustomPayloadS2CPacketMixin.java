package io.github.vampirestudios.raa.mixins;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(CustomPayloadS2CPacket.class)
public class CustomPayloadS2CPacketMixin {

    @Shadow private Identifier channel;

    @Shadow private PacketByteBuf data;

    @Inject(method = "<init>(Lnet/minecraft/util/Identifier;Lnet/minecraft/network/PacketByteBuf;)V", at = @At("RETURN"))
    public void onInit(Identifier channel, PacketByteBuf data, CallbackInfo info) {
        if (data.writerIndex() > 1048576 * 30) {
            throw new IllegalArgumentException("Payload may not be larger than " + 1048576 * 30 + " bytes");
        }
    }

    /**
     * @author OliviaTheVampire
     */
    @Overwrite
    public void read(PacketByteBuf buf) throws IOException {
        this.channel = buf.readIdentifier();
        int i = buf.readableBytes();
        if (i >= 0 && i <= 1048576 * 30) {
            this.data = new PacketByteBuf(buf.readBytes(i));
        } else {
            throw new IOException("Payload may not be larger than " + 1048576 * 30 + " bytes");
        }
    }
}
