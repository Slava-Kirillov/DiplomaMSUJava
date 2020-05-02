package ru.diploma.service;

import org.junit.jupiter.api.Test;
import ru.diploma.config.EqConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.data.SystemOfLinearEquations;
import ru.diploma.data.complex.Complex;
import ru.diploma.error.DataReadException;
import ru.diploma.util.DataUtils;

import java.io.IOException;

public class MainProcessingServiceTest {

    @Test
    public void testOfCalcSLAE() throws IOException, DataReadException {
        String dataFile = "sphere_30_50.dat";
        int numPoints = 4;
        int numCoordinates = 3;

        float h = -1;

        float[][][] cells = IOService.getArrayOfCellsFromFile(dataFile, numPoints, numCoordinates);
        float[][] coll_points = DataUtils.getCollocationPoints(cells, numPoints, numCoordinates);
        CellVectors cellVectors = DataUtils.getCellVectors(cells, numCoordinates);

        EqConfig eqConfig = new EqConfig(4.1f, 0, 0,1,0);

        SystemOfLinearEquations system = new SystemOfLinearEquations(cells, cellVectors, coll_points, eqConfig);

        Complex[] currents = IOService.getResutlFromFile();


    }
}
