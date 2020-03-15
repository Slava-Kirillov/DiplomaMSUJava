package ru.diploma.data.complex;

import lombok.Getter;

/**
 * Класс для комплексных векторов
 */
@Getter
public class ComplexVector {
    private Complex[] coordinates;

    public ComplexVector(Complex x1, Complex x2, Complex x3) {
        this.coordinates = new Complex[]{x1, x2, x3};
    }

    public static ComplexVector multiply(float num, ComplexVector vector){
        return new ComplexVector(
                Complex.multiply(num, vector.getCoordinates()[0]),
                Complex.multiply(num, vector.getCoordinates()[1]),
                Complex.multiply(num, vector.getCoordinates()[2])
        );
    }

    /**
     * Сложение комплексных векторов vec1 + vec2
     * @param vec1
     * @param vec2
     * @return
     */
    public static ComplexVector sum(ComplexVector vec1, ComplexVector vec2) {
        return new ComplexVector(
                Complex.add(vec1.getCoordinates()[0],vec2.getCoordinates()[0]),
                Complex.add(vec1.getCoordinates()[1],vec2.getCoordinates()[1]),
                Complex.add(vec1.getCoordinates()[2],vec2.getCoordinates()[2])
        );
    }

    /**
     * Разность комплексных векторов vec1 - vec2
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
}
