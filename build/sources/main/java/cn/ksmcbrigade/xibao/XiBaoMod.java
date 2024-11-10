package cn.ksmcbrigade.xibao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Mod(modid = XiBaoMod.MODID, version = XiBaoMod.VERSION)
public class XiBaoMod {
    public static final String MODID = "xibao";
    public static final String VERSION = "1.0";

    public File config = new File("config/xibao.json");

    public static XiBaoMod instance;
    public boolean show = true;
    public boolean xibao = true;

    /**
     * If everything goes to plan, you should see this message in the console.
     *
     * This is the main class and method of your mod. It is the entry point for the mod and
     * is used to register all the things that your mod needs to function.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        if(!config.exists()){
            JsonObject object = new JsonObject();
            object.addProperty("show",show);
            object.addProperty("xibao",xibao);
            FileUtils.writeStringToFile(config,object.toString());
        }
        JsonObject object = new JsonParser().parse(FileUtils.readFileToString(config)).getAsJsonObject();
        this.show = object.get("show").getAsBoolean();
        this.xibao = object.get("xibao").getAsBoolean();

        instance = this;

        System.out.println("XiBao mod loaded.");
    }

    public void save() throws IOException {
        JsonObject object = new JsonObject();
        object.addProperty("show",show);
        object.addProperty("xibao",xibao);
        FileUtils.writeStringToFile(config,object.toString());
    }
}
