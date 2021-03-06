package rtk.udispenser;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rtk.RTK;
import rtk.common.CNBT;
import rtk.common.Common;

public class ItemUDispenser extends ItemBlock {
    public ItemUDispenser(Block block) {
        super(block);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(player.isSneaking())
            return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
        return tryFire(stack, player, world);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if(player.isSneaking()){
            openGui(stack, player, world);
            return  new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<ItemStack>(tryFire(stack, player, world), stack);
    }

    public void openGui(ItemStack stack, EntityPlayer player, World world){
        player.openGui(RTK.instance, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);
    }

    public EnumActionResult tryFire(ItemStack stack, EntityPlayer player, World world){
        if(!world.isRemote){
            UDispenser.tryDispense(
                    world, player.getPositionVector().addVector(0, player.getEyeHeight(), 0), player.getLookVec(), new InventoryUDispenser(stack),
                    player
            );
        }
        return EnumActionResult.SUCCESS;
    }
}
