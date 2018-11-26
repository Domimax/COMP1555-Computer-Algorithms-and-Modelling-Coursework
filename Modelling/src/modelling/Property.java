/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelling;

/**
 *
 * @author Maks Domas Smirnov
 */
public class Property {

    private static int idCount = 0;

    private int id;
    private float noOfBathroomsX1;
    private float siteAreaX2;
    private float livingSpaceX3;
    private float noOfGaragesX4;
    private float noOfRoomsX5;
    private float noOfBedroomsX6;
    private float ageX7;
    private float priceY;

    public Property(int id, float noOfBathroomsX1, float siteAreaX2,
            float livingSpaceX3, float noOfGaragesX4, float noOfRoomsX5, float noOfBedroomsX6,
            float ageX7, float priceY) {

        this.id = id;
        this.priceY = priceY;
        this.noOfBathroomsX1 = noOfBathroomsX1;
        this.siteAreaX2 = siteAreaX2;
        this.livingSpaceX3 = livingSpaceX3;
        this.noOfGaragesX4 = noOfGaragesX4;
        this.noOfRoomsX5 = noOfRoomsX5;
        this.noOfBedroomsX6 = noOfBedroomsX6;
        this.ageX7 = ageX7;
    }

    /**
     * @return the idCount
     */
    public static int getIdCount() {
        return idCount;
    }

    /**
     * @param aIdCount the idCount to set
     */
    public static void setIdCount(int aIdCount) {
        idCount = aIdCount;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the noOfBathroomsX1
     */
    public float getNoOfBathroomsX1() {
        return noOfBathroomsX1;
    }

    /**
     * @return the siteAreaX2
     */
    public float getSiteAreaX2() {
        return siteAreaX2;
    }

    /**
     * @return the livingSpaceX3
     */
    public float getLivingSpaceX3() {
        return livingSpaceX3;
    }

    /**
     * @return the noOfGaragesX4
     */
    public float getNoOfGaragesX4() {
        return noOfGaragesX4;
    }

    /**
     * @return the noOfRoomsX5
     */
    public float getNoOfRoomsX5() {
        return noOfRoomsX5;
    }

    /**
     * @return the noOfBedroomsX6
     */
    public float getNoOfBedroomsX6() {
        return noOfBedroomsX6;
    }

    /**
     * @return the ageX7
     */
    public float getAgeX7() {
        return ageX7;
    }

    /**
     * @return the priceY
     */
    public float getPriceY() {
        return priceY;
    }
}
