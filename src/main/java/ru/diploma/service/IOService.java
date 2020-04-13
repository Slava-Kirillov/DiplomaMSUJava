package ru.diploma.service;

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

            String line = null;
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
                            for (int k = 0; k < numCoordPoints; k++) {
                                int index = k + j * numCoordPoints;
                                arrayOfCell[i][j][k] = Float.parseFloat(numbers[index]);
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

    public static Complex[] getResutlFromFile() throws IOException {
        File fileReal = new File("src/main/resources/data/real_result");
        File fileImag = new File("src/main/resources/data/imag_result");

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
