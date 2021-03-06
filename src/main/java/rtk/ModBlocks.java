package rtk;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rtk.udispenser.BlockUDispenser;
import rtk.block.*;

import java.util.HashSet;

public class ModBlocks {
    public static BlockBase emergencyTent, tent, diversTent, enderTent, diversEnderTent, levitator, ultraDispenser, hole, fourierTransformer;

    public static BlockTentWall tentWall;

    public static void init() {

        emergencyTent = register(new BlockEmergencyTent("emergencyTent"));
        tent = register(new BlockTentBreakable("tent"));
        diversTent = register(new BlockDiversTent("diversTent"));
        enderTent = register(new BlockEnderTent("enderTent"));
        diversEnderTent = register(new BlockDiversEnderTent("diversEnderTent"));
        tentWall = register(new BlockTentWall("tentWall"));
        levitator = register(new BlockLevitator("levitator"));
        //ultraDispenser = register(new BlockUDispenser("ultraDispenser"));
        hole = register(new BlockHole("hole"));
        fourierTransformer = register(new BlockFourierTransformer("fourierTransformer"));
    }

    static HashSet<Class<? extends TileEntity>> registeredTEs = new HashSet<Class<? extends TileEntity>>();
    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if (block instanceof BlockBase) {
            BlockBase b = (BlockBase)block;
            b.init(itemBlock);
            if(b.hasTileEntity() && !registeredTEs.contains(b.getTileEntityClass())){
                registeredTEs.add(b.getTileEntityClass());
                GameRegistry.registerTileEntity(b.getTileEntityClass(), b.getUnlocalizedName());
                System.out.println("Registered tile entity: " + b.getUnlocalizedName());
            }
        }

        return block;
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock;
        if(block instanceof  BlockBase)
            itemBlock = ((BlockBase)block).createItemBlock(block);
        else
            itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

}
