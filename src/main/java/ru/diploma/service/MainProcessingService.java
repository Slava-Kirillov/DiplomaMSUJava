package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.config.EqConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.data.SystemOfLinearEquations;
import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataReadException;
import ru.diploma.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

@Component
public class MainProcessingService {

    private DataGenService dataGenService;
    private ApplicationConfig config;
    private EqConfig eqConfig;
    private EffectiveScatteringAreaService effectiveScatteringAreaService;

    public MainProcessingService(DataGenService dataGenService,
                                 ApplicationConfig config,
                                 EqConfig eqConfig,
                                 EffectiveScatteringAreaService effectiveScatteringAreaService) {
        this.dataGenService = dataGenService;
        this.config = config;
        this.eqConfig = eqConfig;
        this.effectiveScatteringAreaService = effectiveScatteringAreaService;
    }

    public void runProcessing() {
        try {
            String projectDirectoryPath = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
            //ячейки
            float[][][] cells = IOService.getArrayOfCellsFromFile(config.getDataFile(), config.getNumPoints(), config.getNumCoordinatePoint());
            //точки коллокации
            float[][] collocationPoint = DataGenService.getCollocationPoints(cells, config.getNumPoints(), config.getNumCoordinatePoint());
            //площади ячеек
            float[] cellArea = DataGenService.getCellArea(cells, config.getNumCoordinatePoint());

            //базис на ячейках
            CellVectors cellVectors = dataGenService.getCellVectors(cells);

            if (config.isWriteResults()) {
                IOUtil.writeAllResultToFiles(cellArea, collocationPoint, cells, cellVectors);
                IOUtil.printFigureArea(cellArea);
            }

            SystemOfLinearEquations system = new SystemOfLinearEquations(cells, cellVectors, collocationPoint, eqConfig);

            String realMatrixFile = "real_part_matrix";
            String imagMatrixFile = "imag_part_matrix";

            String realConstantTerm = "real_part_constant_term";
            String imagConstantTerm = "image_part_constant_term";

            IOUtil.writeComplexMatrixToFile(system.getMatrix_of_coefficient(), realMatrixFile, imagMatrixFile);
            IOUtil.writeConstantTermToFile(system.getConstant_term(), realConstantTerm, imagConstantTerm);

            String command = projectDirectoryPath + "/lib/diploma_lapack_calc";

            Runtime run = Runtime.getRuntime();
            Process proc = run.exec(command);
            Thread.sleep(5000L);
            while (proc.isAlive()) {
            }

            resultProcessing(collocationPoint, cellArea, cellVectors, cells.length);
        } catch (DataReadException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resultProcessing(float[][] collocationPoint, float[] cellArea, CellVectors cellVectors, int cellsNumber) {
        try {
            Complex[] arrayComplex = IOService.getResutlFromFile();

            if (arrayComplex != null && arrayComplex.length != 0) {

                float[][] tau1 = cellVectors.getTau1();
                float[][] tau2 = cellVectors.getTau2();

                ComplexVector[] currents = new ComplexVector[cellsNumber];

                for (int i = 0; i < cellsNumber; i++) {
                    ComplexVector tau1_complex = new ComplexVector(tau1[i][0], tau1[i][1], tau1[i][2]);
                    ComplexVector tau2_complex = new ComplexVector(tau2[i][0], tau2[i][1], tau2[i][2]);

                    int k = 2 * i;
                    int k1 = k + 1;
                    Complex x = arrayComplex[k];
                    Complex x1 = arrayComplex[k1];

                    currents[i] = ComplexVector.sum(ComplexVector.multiply(x, tau1_complex), ComplexVector.multiply(x1, tau2_complex));
                }

                if (currents.length > 0) {
                    effectiveScatteringAreaService.effectiveScatteringAreaBuild(currents, collocationPoint, cellArea);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
