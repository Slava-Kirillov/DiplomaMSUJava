package ru.diploma.calc;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;
import ru.diploma.data.CellDiagonals;
import ru.diploma.error.DataReadException;
import ru.diploma.error.DataValidationException;
import ru.diploma.util.DataUtils;
import ru.diploma.service.IOService;

import java.io.IOException;

public class CellIntgralCalcTest {

    @Test
    public void cellIntegralCalculationTest() throws IOException, DataReadException, DataValidationException {

        String dataFile = "sphere_30_50.dat";
        int numPoints = 4;
        int numCoordinates = 3;

        float h = -1;

        float[][][] cells = IOService.getArrayOfCellsFromFile(dataFile, numPoints, numCoordinates);
        float[][] coll_points = DataUtils.getCollocationPoints(cells, numPoints, numCoordinates);
        float[] cells_area = DataUtils.getCellsArea(cells, numCoordinates);

        for (float[][] cell : cells) {
            CellDiagonals diags = DataUtils.getDiagOfCell(cell, numCoordinates);
            float diag1 = DataUtils.getVectorNorma(diags.getDiag1());
            float diag2 = DataUtils.getVectorNorma(diags.getDiag2());

            if (h == -1) {
                h = diag1;
            }
            if (diag1 < h) {
                h = diag1;
            }
            if (diag2 < h) {
                h = diag2;
            }
        }

        float eps = 2 * h;
        int m = 5;

        for (int i = 0; i < cells.length; i++) {
                CellIntegralCalc.cellIntegralCalculation(coll_points[i],
                        cells[i],
                        eps,
                        new Complex(0,0),
                        m,
                        numCoordinates);
        }
    }
}
