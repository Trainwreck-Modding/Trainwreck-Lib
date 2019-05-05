package xyz.trainwreck.trainwrecklib;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.trainwreck.trainwrecklib.client.modelReg;

import static net.minecraft.world.biome.Biome.LOGGER;


@Mod("twl")
public class TrainWreckLib {

    public static final Logger LOGGER = LogManager.getLogger();

    public static final modelReg regger = new modelReg();

    public TrainWreckLib() {
        LOGGER.info("I exist now.");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerModels);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here

            LOGGER.info("HELLO from Register Block");
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {

        ModelLoaderRegistry.registerLoader(regger);
        try {
            regger.loadModel(new ModelResourceLocation("twl:model"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
