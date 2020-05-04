package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.EqConfig;
import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.util.IOUtil;

@Component
public class EffectiveScatteringAreaService {

    private final EqConfig eqConfig;

    public EffectiveScatteringAreaService(EqConfig eqConfig) {
        this.eqConfig = eqConfig;
    }

    public void effectiveScatteringAreaBuild(ComplexVector[] currents, float[][] collocationPoint, float[] cellArea, String pathToResults) {

        float[] ESA = new float[1801];

        for (int i = 0; i < 181; i++) {
            for (int j = 0; j < 10; j++) {
                float angleRadian = (float) (((float) i + j / 10.0f) * Math.PI / 180.0f);
                float[] tau = {(float) Math.cos(angleRadian), (float) Math.sin(angleRadian), 0.0f};
                ESA[i*10 + j] = (float) (calcESA(currents, collocationPoint, cellArea, tau) / Math.PI);
                if (i == 180) {
                    break;
                }
            }
        }
        IOUtil.writeResultToFile("ESA.dat", ESA, 1, ESA.length, pathToResults);
    }

    private float calcESA(ComplexVector[] currents, float[][] collocationPoint, float[] cellArea, float[] tau) {
        ComplexVector sumVec = new ComplexVector();

        for (int j = 0; j < currents.length; j++) {
            sumVec.add(calcAreaForVec(tau, collocationPoint[j], currents[j], cellArea[j]));
        }

        return (float) (4.0f * Math.PI * ComplexVector.scalarMultiply(sumVec, sumVec).getRe());
    }

    private ComplexVector calcAreaForVec(float[] directionVec, float[] collocPointVec, ComplexVector current, float area) {
        float k = eqConfig.getWave_number();

        Complex k_complex = new Complex(k, 0.0f);
        Complex imag_unit = new Complex(0.0f, 1.0f);
        Complex area_complex = new Complex(area, 0.0f);

        ComplexVector directionVec_complex = new ComplexVector(directionVec[0], directionVec[1], directionVec[2]);
        ComplexVector collocPointVec_complex = new ComplexVector(collocPointVec[0], collocPointVec[1], collocPointVec[2]);

        Complex a1 = ComplexVector.scalarMultiply(directionVec_complex, collocPointVec_complex);
        Complex a2 = Complex.multiply(Complex.multiply(imag_unit.conjugate(), k_complex), a1);
        Complex a3 = Complex.divide(Complex.exp(a2), new Complex((float) (4.0f * Math.PI), 0.0f));
        ComplexVector a4 = ComplexVector.vecMultiply(directionVec_complex, current);
        ComplexVector a5 = ComplexVector.multiply(Complex.multiply(imag_unit, k_complex), a4);
        ComplexVector a6 = ComplexVector.multiply(area_complex, a5);
        ComplexVector a7 = ComplexVector.multiply(a3, a6);

        return a7;
    }
}
