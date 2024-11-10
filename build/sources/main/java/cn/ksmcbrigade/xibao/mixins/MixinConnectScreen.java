package cn.ksmcbrigade.xibao.mixins;

import cn.ksmcbrigade.xibao.XiBaoMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GuiDisconnected.class)
public abstract class MixinConnectScreen extends GuiScreen {

    @Shadow private int field_175353_i;
    @Unique
    private final ResourceLocation template_forge_mixin_1_8_9$xibao = new ResourceLocation("xibao","gui/xibao.png");

    @Unique
    private final ResourceLocation template_forge_mixin_1_8_9$beibao = new ResourceLocation("xibao","gui/beibao.png");

    @Redirect(method = "drawScreen",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/GuiDisconnected;drawDefaultBackground()V"))
    private void drawBackground(GuiDisconnected instance){
        if(XiBaoMod.instance.show){
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(XiBaoMod.instance.xibao? template_forge_mixin_1_8_9$xibao : template_forge_mixin_1_8_9$beibao);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(0.0D, this.height, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldrenderer.pos(this.width, this.height, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldrenderer.pos(this.width, 0.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
            tessellator.draw();
        }
        else{
            instance.drawDefaultBackground();
        }
    }

    @Inject(method = "initGui",at = @At("TAIL"))
    private void init(CallbackInfo ci){
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 20, I18n.format("xibao.gui.case")));
        GuiButton never = new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 20 + 20, I18n.format("xibao.gui.never"));
        never.enabled = XiBaoMod.instance.show;
        this.buttonList.add(never);
    }

    @Inject(method = "actionPerformed",at = @At("TAIL"))
    private void button(GuiButton button, CallbackInfo ci) throws IOException {
        if(button.id==1){
            XiBaoMod.instance.xibao = !XiBaoMod.instance.xibao;
            XiBaoMod.instance.save();
        }

        if(button.id==2){
            XiBaoMod.instance.show = false;
            button.enabled = false;
            XiBaoMod.instance.save();
        }
    }
}
