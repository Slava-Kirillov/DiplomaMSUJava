package ru.diploma.data.complex;

import lombok.Getter;

/**
 * Класс для комплексных векторов
 */
@Getter
public class ComplexVector {
    private Complex[] coordinates;

    public ComplexVector() {
        Complex x1 = new Complex(0, 0);
        Complex x2 = new Complex(0, 0);
        Complex x3 = new Complex(0, 0);
        this.coordinates = new Complex[]{x1, x2, x3};
    }

    public ComplexVector(Complex x1, Complex x2, Complex x3) {
        this.coordinates = new Complex[]{x1, x2, x3};
    }

    public ComplexVector(float x1_real, float x2_real, float x3_real) {
        Complex x1 = new Complex(x1_real, 0.0f);
        Complex x2 = new Complex(x2_real, 0.0f);
        Complex x3 = new Complex(x3_real, 0.0f);
        this.coordinates = new Complex[]{x1, x2, x3};
    }

    /**
     * Векторное умножение в унитарном пространстве
     * @param vec1
     * @param vec2
     * @return
     */
    public static ComplexVector vecMultiply(ComplexVector vec1, ComplexVector vec2) {
        Complex[] a = vec1.getCoordinates();
        Complex[] b = vec2.getCoordinates();
        return new ComplexVector(
                Complex.subtract(Complex.multiply(a[1], b[2]), Complex.multiply(a[2], b[1])),
                Complex.subtract(Complex.multiply(a[2], b[0]), Complex.multiply(a[0], b[2])),
                Complex.subtract(Complex.multiply(a[0], b[1]), Complex.multiply(a[1], b[0]))
        );
    }

    /**
     * Умножние комплексного вектора на действительное число
     * @param num
     * @param vector
     * @return
     */
    public static ComplexVector multiply(float num, ComplexVector vector) {
        return new ComplexVector(
                Complex.multiply(num, vector.getCoordinates()[0]),
                Complex.multiply(num, vector.getCoordinates()[1]),
                Complex.multiply(num, vector.getCoordinates()[2])
        );
    }

    /**
     * Умножние комплексного вектора на комплексное число
     * @param num
     * @param vector
     * @return
     */
    public static ComplexVector multiply(Complex num, ComplexVector vector) {
        return new ComplexVector(
                Complex.multiply(num, vector.getCoordinates()[0]),
                Complex.multiply(num, vector.getCoordinates()[1]),
                Complex.multiply(num, vector.getCoordinates()[2])
        );
    }

    public static Complex scalarMultiply(float[] realVec1, ComplexVector vec2) {
        ComplexVector vec1 = new ComplexVector(
                new Complex(realVec1[0], 0),
                new Complex(realVec1[1], 0),
                new Complex(realVec1[2], 0)
        );
        return scalarMultiply(vec1, vec2);
    }

    /**
     * скалярное произведение в унитарном пространстве (линейное пространство над полем комплексных чисел)
     * @param vec1
     * @param vec2
     * @return
     */
    public static Complex scalarMultiply(ComplexVector vec1, ComplexVector vec2) {
        Complex[] coordVec1 = vec1.getCoordinates();
        Complex[] coordVec2 = vec2.getCoordinates();

        Complex sum = new Complex();

        for (int i = 0; i < coordVec1.length; i++) {
            sum.add(Complex.multiply(coordVec1[i], coordVec2[i].conjugate()));
        }
        return sum;
    }

    /**
     * Сложение комплексных векторов vec1 + vec2
     *
     * @param vec1
     * @param vec2
     * @return
     */
    public static ComplexVector sum(ComplexVector vec1, ComplexVector vec2) {
        return new ComplexVector(
                Complex.add(vec1.getCoordinates()[0], vec2.getCoordinates()[0]),
                Complex.add(vec1.getCoordinates()[1], vec2.getCoordinates()[1]),
                Complex.add(vec1.getCoordinates()[2], vec2.getCoordinates()[2])
        );
    }

    /**
     * Разность комплексных векторов vec1 - vec2
     *
     * @param vec1
     * @param vec2
     * @return
     */
    public static ComplexVector subtract(ComplexVector vec1, ComplexVector vec2) {
        return new ComplexVector(
                Complex.subtract(vec1.getCoordinates()[0], vec2.getCoordinates()[0]),
                Complex.subtract(vec1.getCoordinates()[1], vec2.getCoordinates()[1]),
                Complex.subtract(vec1.getCoordinates()[2], vec2.getCoordinates()[2])
        );
    }

    public void add(ComplexVector vec) {
        Complex[] addedVector = vec.getCoordinates();
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i].add(addedVector[i]);
        }
    }
}
