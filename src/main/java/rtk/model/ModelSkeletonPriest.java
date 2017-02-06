// Date: 2/4/2017 7:38:50 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package rtk.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSkeletonPriest extends ModelBase
{
  //fields
    ModelRenderer bipedHead;
    ModelRenderer bipedBody;
    ModelRenderer bipedRightArm;
    ModelRenderer bipedLeftArm;
    ModelRenderer bipedRightLeg;
    ModelRenderer bipedLeftLeg;
    ModelRenderer chin;
    ModelRenderer staff;
    ModelRenderer spikes;

  public ModelSkeletonPriest()
  {
    textureWidth = 64;
    textureHeight = 64;
      spikes = new ModelRenderer(this, 0, 32);
      spikes.addBox(0F, -13F, 2F, 2, 16, 6);
      spikes.setRotationPoint(-1F, 12F, 0F);
      spikes.setTextureSize(64, 64);
      spikes.mirror = true;
      setRotation(spikes, 0.2974216F, 0F, 0F);
      bipedHead = new ModelRenderer(this, 0, 0);
      bipedHead.addBox(-4F, -10F, -4F, 7, 9, 7);
      bipedHead.setRotationPoint(0F, 2F, -5F);
      bipedHead.setTextureSize(64, 32);
      bipedHead.mirror = true;
      setRotation(bipedHead, 0.3490659F, 0F, 0.1745329F);
      bipedBody = new ModelRenderer(this, 16, 16);
      bipedBody.addBox(-4F, -12F, -2F, 10, 12, 4);
      bipedBody.setRotationPoint(-1F, 12F, 0F);
      bipedBody.setTextureSize(64, 32);
      bipedBody.mirror = true;
      setRotation(bipedBody, 0.2974289F, 0F, 0F);
      bipedRightArm = new ModelRenderer(this, 44, 17);
      bipedRightArm.addBox(-3F, -2F, -2F, 2, 12, 3);
      bipedRightArm.setRotationPoint(-4F, 2F, -4F);
      bipedRightArm.setTextureSize(64, 32);
      bipedRightArm.mirror = true;
      setRotation(bipedRightArm, -1.226894F, 0.2230717F, 0F);
      bipedLeftArm = new ModelRenderer(this, 44, 17);
      bipedLeftArm.addBox(-1F, -2F, -2F, 2, 12, 3);
      bipedLeftArm.setRotationPoint(6F, 2F, -4F);
      bipedLeftArm.setTextureSize(64, 32);
      bipedLeftArm.mirror = true;
      setRotation(bipedLeftArm, -0.3346075F, -0.2218488F, 0F);
      bipedRightLeg = new ModelRenderer(this, 0, 16);
      bipedRightLeg.addBox(-1F, 0F, -2F, 2, 12, 3);
      bipedRightLeg.setRotationPoint(-3F, 12F, 0F);
      bipedRightLeg.setTextureSize(64, 32);
      bipedRightLeg.mirror = true;
      setRotation(bipedRightLeg, 0F, 0F, 0F);
      bipedLeftLeg = new ModelRenderer(this, 0, 16);
      bipedLeftLeg.addBox(-1F, 0F, -2F, 2, 12, 3);
      bipedLeftLeg.setRotationPoint(3F, 12F, 0F);
      bipedLeftLeg.setTextureSize(64, 32);
      bipedLeftLeg.mirror = true;
      setRotation(bipedLeftLeg, 0F, 0F, 0F);
      chin = new ModelRenderer(this, 48, 0);
      chin.addBox(-2F, -1F, -4F, 4, 3, 2);
      chin.setRotationPoint(0F, 2F, -5F);
      chin.setTextureSize(64, 32);
      chin.mirror = true;
      setRotation(chin, 0F, 0F, 0F);
      staff = new ModelRenderer(this, 60, 0);
      staff.addBox(-4F, -7F, -9F, 1, 28, 1);
      staff.setRotationPoint(-4F, 2F, -4F);
      staff.setTextureSize(64, 32);
      staff.mirror = true;
      setRotation(staff, 0F, 0F, 0F);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

    private void addRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX += x;
        model.rotateAngleY += y;
        model.rotateAngleZ += z;
    }

  @Override
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

    bipedHead.render(scale);
    chin.render(scale);
    bipedBody.render(scale);
    spikes.render(scale);
    staff.render(scale);
    bipedRightArm.render(scale);
    bipedLeftArm.render(scale);
    bipedRightLeg.render(scale);
    bipedLeftLeg.render(scale);
  }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        limbSwingAmount *= 1.2F;
        limbSwing *= 1.2F;

        chin.rotateAngleY = netHeadYaw * 0.017453292F;
        bipedHead.rotateAngleY = chin.rotateAngleY;

        staff.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.bipedRightArm.rotateAngleX = staff.rotateAngleX - 1.226894F;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;

        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;


    }
}