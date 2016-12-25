package rtk.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rtk.ModBlocks;
import rtk.common.CNBT;
import rtk.tileentity.TileEntityEnderTent;

import java.util.Random;

public class BlockEnderTent extends BlockTent {

    public BlockEnderTent(String name) {
        super(name);
    }

    @Override
    public int fuelCost() { return 16; }

    @Override
    public Item fuelType() { return Items.ENDER_PEARL; }

    @Override
    public IBlockState wall() {
        return ModBlocks.tentWall.variant(3);
    }

    @Override
    public boolean worksInWater() {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEnderTent();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityEnderTent.class;
    }

    public TileEntityEnderTent getTileEntity(IBlockAccess world, BlockPos pos){
        return (TileEntityEnderTent)world.getTileEntity(pos);
    }

    @Override
    public boolean tryBuildTent(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {
        TileEntityEnderTent te = getTileEntity(world, pos);
        boolean result = false;
        if(te.neverDeployed())
            result = super.tryBuildTent(world, pos, player, side);
        else if(!te.isDeployed() && canBuildTent(world, pos, player)){
            spendFuel(player);
            placeContents(world, pos);
            result = true;
        }
        if(result){
            te.setDeployed(true);
            te.setNeverDeployed(false);
            world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.BLOCKS, 0.5F, 1F);
        }
        return result;
    }

    @Override
    public void decorate(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {
    }

    public void placeContents(World world, BlockPos pos) {
        TileEntityEnderTent te = getTileEntity(world, pos);
        if(te.neverDeployed())
            return;

        BlockPos[] tentCube = tentCuboid(pos);

//        First set everything to dirt so nothing is without solid support during the building loop.
//        This was "inspired" by the Minecraft CommandClone class.
        for(BlockPos otherPos : tentCube){
            if(pos.equals(otherPos))
                continue;  //Don't mess with the tent block!!!

            TileEntity otherTileEntity = world.getTileEntity(otherPos);
            if(otherTileEntity instanceof TileEntityEnderTent) //We don't want another ender tent collapsing in a collapsing ender tent!!!
                ((TileEntityEnderTent)otherTileEntity).dontGrab = true;

            world.setBlockState(otherPos, Blocks.DIRT.getDefaultState(), 2);
        }

        NBTTagList blockList = te.getBlockList();

        int bsInd = 0;
        for(BlockPos otherPos : tentCube){
            if(!pos.equals(otherPos))
                CNBT.placeBlockFromNBT(world, otherPos, blockList.getCompoundTagAt(bsInd));
            bsInd++;
        }

        for(BlockPos otherPos : tentCube)
            world.notifyNeighborsOfStateChange(otherPos, world.getBlockState(otherPos).getBlock());
    }

    public boolean tryGrabContents(World world, BlockPos pos){
        TileEntityEnderTent te = getTileEntity(world, pos);
        if(!te.isDeployed())
            return false;

        BlockPos[] tentCube = tentCuboid(pos);

        NBTTagList blockList = new NBTTagList();

        for(BlockPos otherPos : tentCube){
            blockList.appendTag(CNBT.NBTFromBlock(world, otherPos));
        }


        //Next set everything to a solid block so nothing loses support during deletion. (I'm talking about you torches!)
        for(BlockPos otherPos : tentCube){
            if(pos.equals(otherPos))
                continue;

            TileEntity otherTileEntity = world.getTileEntity(otherPos);
            if(otherTileEntity instanceof IInventory)
                ((IInventory)otherTileEntity).clear();

            if(otherTileEntity instanceof TileEntityEnderTent) //We don't want another ender tent collapsing in a collapsing ender tent!!!
                ((TileEntityEnderTent)otherTileEntity).dontGrab = true;

            world.setBlockState(otherPos, Blocks.DIRT.getDefaultState(), 2);
        }

        //Finally we can actually delete everything.
        for(BlockPos otherPos : tentCube){
            if(!pos.equals(otherPos)) //Don't mess with the tent block!!!
                world.setBlockState(otherPos, Blocks.AIR.getDefaultState(), 3);
        }

        world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.BLOCKS, 0.5F, 1F);

        te.setBlockList(blockList);
        te.setDeployed(false);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if(stack.hasTagCompound())
            getTileEntity(world, pos).readTent(stack.getTagCompound());

        EntityPlayer player = (EntityPlayer)placer;
        if(player != null && player.capabilities.isCreativeMode && stack.stackSize == 1)
            player.inventory.deleteStack(stack);

        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if(world.isRemote)
            return;

        TileEntityEnderTent te = getTileEntity(world, pos);
        if(te.dontGrab)
            return;

        ItemStack drop = new ItemStack(ModBlocks.enderTent, 1);
        tryGrabContents(world, pos);
        if(!te.isDeployed() && !te.neverDeployed())
            drop.addEnchantment(Enchantments.INFINITY, 1); //This is just an indicator that the tent is full.

        getTileEntity(world, pos).writeTent(CNBT.ensureCompound(drop));
        EntityItem item = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, drop);
        world.spawnEntityInWorld(item);
    }
}
