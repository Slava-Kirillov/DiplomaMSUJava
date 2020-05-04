package ru.diploma.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.config.EqConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.data.SystemOfLinearEquations;
import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataReadException;
import ru.diploma.util.DataUtils;
import ru.diploma.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Objects;

@Component
public class MainProcessingService {

    @Value("${only.write.geometry}")
    private boolean onlyWriteGeometry;

    private final ApplicationConfig config;
    private final EqConfig eqConfig;
    private final EffectiveScatteringAreaService effectiveScatteringAreaService;

    public MainProcessingService(ApplicationConfig config,
                                 EqConfig eqConfig,
                                 EffectiveScatteringAreaService effectiveScatteringAreaService) {
        this.config = config;
        this.eqConfig = eqConfig;
        this.effectiveScatteringAreaService = effectiveScatteringAreaService;
    }

    public void runProcessing() {
        try {
            String projectDirectoryPath = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
            String pathToResults = "/src/main/resources/data/results/";
            File directory = new File(projectDirectoryPath + pathToResults);
            for (File file: Objects.requireNonNull(directory.listFiles())) {
                file.delete();
            }

            //ячейки
            float[][][] cells = IOService.getArrayOfCellsFromFile(config.getDataFile(), config.getNumPoints(), config.getNumCoordinatePoint());
            //точки коллокации
            float[][] collocationPoint = DataUtils.getCollocationPoints(cells, config.getNumPoints(), config.getNumCoordinatePoint());
            //площади ячеек
            float[] cellArea = DataUtils.getCellsArea(cells, config.getNumCoordinatePoint());

            //базис на ячейках
            CellVectors cellVectors = DataUtils.getCellVectors(cells, config.getNumCoordinatePoint());

            if (config.isWriteResults()) {
                IOUtil.writeAllResultToFiles(cellArea, collocationPoint, cells, cellVectors, projectDirectoryPath + pathToResults);
                IOUtil.printFigureArea(cellArea);
            }

            if (!onlyWriteGeometry) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                SystemOfLinearEquations system = new SystemOfLinearEquations(cells, cellVectors, collocationPoint, eqConfig);
                stopWatch.stop();
                System.out.println("Filling matrix " + stopWatch.getLastTaskTimeMillis() + " ms");

                String realMatrixFile = "real_part_matrix";
                String imagMatrixFile = "imag_part_matrix";

                String realConstantTerm = "real_part_constant_term";
                String imagConstantTerm = "image_part_constant_term";

                IOUtil.writeComplexMatrixToFile(system.getMatrix_of_coefficient(), realMatrixFile, imagMatrixFile, projectDirectoryPath + pathToResults);
                IOUtil.writeConstantTermToFile(system.getConstant_term(), realConstantTerm, imagConstantTerm, projectDirectoryPath + pathToResults);

                String command = projectDirectoryPath + "/lib/diploma_lapack_calc";

                system = null;

                System.out.println("LAPACK calc run ---------");
                stopWatch.start();
                Runtime run = Runtime.getRuntime();
                Process proc = run.exec(command);
//                Thread.sleep(5000L);
                while (proc.isAlive()) {
                }

                stopWatch.stop();
                System.out.println("LAPACK calc end " + stopWatch.getLastTaskTimeMillis() + " ms");

                Complex[] currents = resultProcessing(collocationPoint, cellArea, cellVectors, cells.length, projectDirectoryPath + pathToResults);
            }
        } catch (DataReadException | IOException e) {
            e.printStackTrace();
        }
    }

    public Complex[] resultProcessing(float[][] collocationPoint, float[] cellArea, CellVectors cellVectors, int cellsNumber, String pathToResults) {
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
                    effectiveScatteringAreaService.effectiveScatteringAreaBuild(currents, collocationPoint, cellArea, pathToResults);
                }
            }

            return arrayComplex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
