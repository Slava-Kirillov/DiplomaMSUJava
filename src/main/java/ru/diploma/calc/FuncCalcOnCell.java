package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataValidationException;

@FunctionalInterface
public interface FuncCalcOnCell {
    ComplexVector funcDefOnCell(float[] x, float eps, Complex k, float[] y);
}
