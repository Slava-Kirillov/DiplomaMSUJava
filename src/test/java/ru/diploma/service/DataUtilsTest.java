package ru.diploma.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.diploma.data.CellVectors;
import ru.diploma.util.DataUtils;

public class DataUtilsTest {

    @Test
    public void getVectorNormaTest() {
        float[] vec = {2,3,5};
        float norm = DataUtils.getVectorNorma(vec);
        Assertions.assertEquals(norm, 6.164414f);
    }

    @Test
    public void getVecMultipTest() {
        float[] vec1 = {2,3,5};
        float[] vec2 = {1,6,2};

        float[] mult = DataUtils.getVecMultip(vec1, vec2, 3);
        Assertions.assertEquals(mult[0], -24.0);
        Assertions.assertEquals(mult[1], 1.0);
        Assertions.assertEquals(mult[2], 9.0);
    }

    @Test
    public void getCellAreaTest() {
        float[][] cell = {{2,0,3},{7,0,3},{5,0,0},{0,0,0}};

        float area = DataUtils.getCellArea(cell, 3);
        Assertions.assertEquals(area, 15.0);
    }

    @Test
    public void getCellsVectorsTest() {
        float[][] cell = {{0,-2,0},{3,-2,0},{3,0,0},{0,0,0}};
        float[][][] cells = new float[1][][];
        cells[0] = cell;

        CellVectors vectors = DataUtils.getCellVectors(cells,3);
        float[] normal = vectors.getNormal()[0];
        float[] tau1 = vectors.getTau1()[0];
        float[] tau2 = vectors.getTau2()[0];

        Assertions.assertEquals(DataUtils.getVectorNorma(tau1), 1);
        Assertions.assertEquals(DataUtils.getVectorNorma(tau2), 1);
        Assertions.assertEquals(DataUtils.getVectorNorma(normal), 1);

        Assertions.assertEquals(DataUtils.getScalarMultip(tau1, tau2), 0);
        Assertions.assertEquals(DataUtils.getScalarMultip(tau1, normal), 0);
        Assertions.assertEquals(DataUtils.getScalarMultip(normal, tau2), 0);
    }

    @Test
    public void getCollocationPointsTest() {
        float[][] cell = {{0,0,2},{2,0,2},{2,2,0},{0,2,0}};
        float[][][] cells = new float[1][][];
        cells[0] = cell;

        float[][] collocationPoints = DataUtils.getCollocationPoints(cells, 4, 3);
        float[] point = collocationPoints[0];

        Assertions.assertEquals(point[0], 1.0);
        Assertions.assertEquals(point[1], 1.0);
        Assertions.assertEquals(point[2], 1.0);
    }
}
