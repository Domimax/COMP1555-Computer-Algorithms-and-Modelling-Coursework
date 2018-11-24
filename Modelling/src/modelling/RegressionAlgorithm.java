package modelling;

import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 * RegressionAlgorithm class performs simple least squares linear regression
 * using the provided data sets.
 *
 * @author Maks Domas Smirnov, ID: ms8749c
 */
public class RegressionAlgorithm {

    // Regression coefficient beta1
    private float beta1;
    // Regression coefficient beta0
    private float beta0;
    // Correlation coefficient R squared
    private float r2;
    // A list of independant variables
    private ArrayList<Float> dataXi;
    // A list of dependant variables
    private ArrayList<Float> dataY;

    // The constructor takes in both lists and finds all the coefficients.
    public RegressionAlgorithm(ArrayList<Float> dataXi, ArrayList<Float> dataY) {
        this.dataXi = dataXi;
        this.dataY = dataY;
        findRegressionCoefficients();
        findCorrelationCoefficient();
    }

    public RegressionAlgorithm() {

    }

    // The method returns the mean for a provided data set.
    public float mean(ArrayList<Float> data) {
        float sum = 0;
        for (int i = 0; i < data.size() - 1; i++) {
            sum += data.get(i);
        }
        return sum / (float) data.size();
    }

    // The method finds the sum of squares between 2 provided data sets.
    private float sumOfSquares(ArrayList<Float> data1, ArrayList<Float> data2) {
        float sum = 0;
        for (int i = 0; i < data1.size() - 1; i++) {
            sum += (data1.get(i) - mean(data1)) * (data2.get(i) - mean(data2));
        }
        return sum;
    }

    public float sum(ArrayList<Float> data) {
        float sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += (data.get(i));
        }
        return sum;
    }

    public float sumSquares(ArrayList<Float> data) {
        float sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += (float) Math.pow(data.get(i), 2);
        }
        return sum;
    }

    public float sumSquares(ArrayList<Float> data1, ArrayList<Float> data2) {
        float sum = 0;
        for (int i = 0; i < data1.size(); i++) {
            sum += data1.get(i) * data2.get(i);
        }
        return sum;
    }

    // The method finds the regression coefficients.
    private void findRegressionCoefficients() {
        beta1 = sumOfSquares(dataXi, dataY) / sumOfSquares(dataXi, dataXi);
        beta0 = mean(dataY) - beta1 * mean(dataXi);
    }

    // The method finds the correlation coefficient.
    private void findCorrelationCoefficient() {
        r2 = (sumOfSquares(dataXi, dataY) * sumOfSquares(dataXi, dataY))
                / (sumOfSquares(dataXi, dataXi) * sumOfSquares(dataY, dataY));
    }

    public float variance(ArrayList<Float> data) {
        float sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += Math.pow((data.get(i) - mean(data)), 2);
        }
        return sum / data.size();
    }

    public float standardDeviation(ArrayList<Float> data) {
        return (float) sqrt(variance(data));
    }

    public float getBeta1() {
        return beta1;
    }

    public float getBeta0() {
        return beta0;
    }

    public float getR2() {
        return r2;
    }
}
