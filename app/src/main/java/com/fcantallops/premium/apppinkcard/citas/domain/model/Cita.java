
package com.fcantallops.premium.apppinkcard.citas.domain.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Entidad de negocio para las Citas
 */
public class Cita {
    @SerializedName("code")
    private String mCode;

    @SerializedName("Paciente")
    private String mPaciente;

    @SerializedName("Medico")
    private String mMedico;

    @SerializedName("FechaHora")
    private Timestamp mFechaHora;

    @SerializedName("Servicio")
    private String mServicio;

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmPaciente() {
        return mPaciente;
    }

    public void setmPaciente(String mPaciente) {
        this.mPaciente = mPaciente;
    }

    public String getmMedico() {
        return mMedico;
    }

    public void setmMedico(String mMedico) {
        this.mMedico = mMedico;
    }

    public Timestamp getmFechaHora() {
        return mFechaHora;
    }


    public void setmFechaHora(Timestamp mFechaHora) {
        this.mFechaHora = mFechaHora;
    }

    public String getmServicio() {
        return mServicio;
    }

    public void setmServicio(String mServicio) {
        this.mServicio = mServicio;
    }



    /* @SerializedName("code")
    private String mCode;

    @SerializedName("name")
    private String mName;

   @SerializedName("description")
    private String mDescription;

    @SerializedName("brand")
    private String mBrand;

    @SerializedName("price")
    private float mPrice;

    @SerializedName("unitsInStock")
    private int mUnitsInStock;

    @SerializedName("imageUrl")
    private String mImageUrl;*/


    public Cita( String Paciente, String Medico, Timestamp FechaHora, String Servicio) {
        mCode = UUID.randomUUID().toString();
        //mPrice = price;
        mPaciente= Paciente;
        mMedico= Medico;
        mFechaHora= FechaHora;
        mServicio= Servicio;

    }

    /*public Cita(float price, String name, String imageUrl) {
        mCode = UUID.randomUUID().toString();
        //mPrice = price;
        mName = name;

    }

    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getBrand() {
        return mBrand;
    }

    public float getPrice() {
        return mPrice;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public Object getUnitsInStock() {
        return mUnitsInStock;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public void setUnitsInStock(int mUnitsInStock) {
        this.mUnitsInStock = mUnitsInStock;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getFormatedPrice() {
        return String.format("$%s", mPrice);
    }

    public String getFormattedUnitsInStock() {
        return String.format(Locale.getDefault(), "%d u.", mUnitsInStock);
    }*/
}
