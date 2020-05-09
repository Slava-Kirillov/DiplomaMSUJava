package ru.diploma.service;

import org.apache.commons.lang3.StringUtils;
import ru.diploma.data.complex.Complex;
import ru.diploma.error.DataReadException;

import java.io.*;

public class IOService {

    /**
     * Метод возвращает многомерный массив, в котором содержатся координаты точек ячеек,
     * на которые разбит исследуемый объект
     * @return
     * @throws IOException
     * @throws DataReadException
     */
    public static float[][][] getArrayOfCellsFromFile(String dataFile,
                                                      int numPoints,
                                                      int numCoordPoints) throws IOException, DataReadException {
        File file = new File("src/main/resources/data/" + dataFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            String[] numbers = null;
            int lineCount = 0;
            int numberOfCells = 0;

            // в цикле while считываем файл до строки с координатами яцейки
            while ((line = reader.readLine()) != null) {
                lineCount++;
                numbers = line.split(" ");
                if (numbers.length < numPoints * numCoordPoints) {
                    if (lineCount == 4) {
                        numberOfCells = Integer.parseInt(numbers[0]);
                    }
                } else {
                    break;
                }
            }

            if (numberOfCells != 0) {
                float[][][] arrayOfCell = new float[numberOfCells][numPoints][numCoordPoints];
                for (int i = 0; i < numberOfCells; i++) { // (- 1) так как первая строка с ячейкой была прочитана в цикле while
                    if (i != 0) {
                        line = reader.readLine();
                        lineCount++;
                        numbers = line.split(" ");
                    }
                    if (numbers.length == numPoints * numCoordPoints) {
                        for (int j = 0; j < numPoints; j++) {
                            float[] coords = new float[3];
                            for (int k = 0; k < numCoordPoints; k++) {
                                int index = k + j * numCoordPoints;
                                 coords[k] = Float.parseFloat(numbers[index]);
                            }
                            if (StringUtils.compare(dataFile, "geodat_30_50.dat") == 0) {
                                arrayOfCell[i][j] = rotatePoint(coords);
                            } else {
                                arrayOfCell[i][j] = coords;
                            }
                        }
                    } else {
                        throw new DataReadException("Can not read file. Wrong number of coordinates. File line:" + lineCount);
                    }
                }
                return arrayOfCell;
            }
            throw new DataReadException("Wrong data. Number of cells:" + numberOfCells);
        }
    }

    private static float[] rotatePoint(float[] coord) {
        float[] newCoord = new float[3];

        newCoord[0] = (float) (coord[0] * Math.cos(Math.toRadians(90.0)) + coord[1] * 0 + coord[2] * (-Math.sin(Math.toRadians(90.0))));
        newCoord[1] = coord[0] * 0 + coord[1] * 1 + coord[2] * 0;
        newCoord[2] = (float) (coord[0] * Math.sin(Math.toRadians(90.0)) + coord[1] * 0 + coord[2] * (-Math.cos(Math.toRadians(90.0))));
        return newCoord;
    }

    public static Complex[] getResutlFromFile() throws IOException {
        File fileReal = new File("src/main/resources/data/results/real_result");
        File fileImag = new File("src/main/resources/data/results/imag_result");

        float[] arrayReal = null;
        float[] arrayImage = null;

        int numberOfRows;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileReal))) {
            String line;

            line = reader.readLine();
            numberOfRows = Integer.parseInt(line);

            if (numberOfRows != 0) {
                arrayReal = new float[numberOfRows];
                for (int i = 0; i < numberOfRows; i++) {
                    line = reader.readLine();
                    arrayReal[i] = Float.parseFloat(line);
                }
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileImag))) {
            String line;

            line = reader.readLine();
            numberOfRows = Integer.parseInt(line);

            if (numberOfRows != 0) {
                arrayImage = new float[numberOfRows];
                for (int i = 0; i < numberOfRows; i++) {
                    line = reader.readLine();
                    arrayImage[i] = Float.parseFloat(line);
                }
            }
        }

        if (arrayReal != null && arrayImage != null) {
            Complex[] arrayComplex = new Complex[numberOfRows];

            for (int i = 0; i < numberOfRows; i++) {
                Complex complex = new Complex(arrayReal[i], arrayImage[i]);
                arrayComplex[i] = complex;
            }
            return arrayComplex;
        }
        return null;
    }
}
