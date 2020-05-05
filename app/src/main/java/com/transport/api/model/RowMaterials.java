package com.transport.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 16/3/20.
 */
public class RowMaterials {
    @SerializedName("ProductMaterial")
    @Expose
    private List<ProductMaterial> productMaterial = null;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<ProductMaterial> getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(List<ProductMaterial> productMaterial) {
        this.productMaterial = productMaterial;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public static class ProductMaterial {

        @SerializedName("MaterialId")
        @Expose
        private Integer materialId;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Image")
        @Expose
        private String image;

        private boolean isSelected = false;

        public ProductMaterial(Integer materialId, String name, String image) {
            this.materialId = materialId;
            this.name = name;
            this.image = image;
        }

        public Integer getMaterialId() {
            return materialId;
        }

        public void setMaterialId(Integer materialId) {
            this.materialId = materialId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
