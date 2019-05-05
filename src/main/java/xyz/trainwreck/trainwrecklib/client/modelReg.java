package xyz.trainwreck.trainwrecklib.client;

import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import xyz.trainwreck.trainwrecklib.TrainWreckLib;

public class modelReg implements ICustomModelLoader {
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        //throw new RuntimeException("Reload not implemented");
    }

    @Override
    public boolean accepts(final ResourceLocation modelLocation) {
        throw new RuntimeException("accepts " + modelLocation);
        //return false;
    }

    @Override
    public IUnbakedModel loadModel(final ResourceLocation modelLocation) throws Exception {
        TrainWreckLib.LOGGER.info("yo yo hombro");
        //throw new RuntimeException("loadModel " + modelLocation);
        return null;
    }
}
