package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.error.DataReadException;
import ru.diploma.util.IOUtil;

import java.io.IOException;

@Component
public class ProcessingService {

    private DataGenService dataGenService;
    private ApplicationConfig config;

    public ProcessingService(DataGenService dataGenService, ApplicationConfig config) {
        this.dataGenService = dataGenService;
        this.config = config;
    }

    public void runProcessing() {
        try {
            float[][][] cells = IOService.getArrayOfCellsFromFile(config.getDataFile(),
                    config.getNumPoints(),
                    config.getNumCoordinatePoint());

            float[][] collocationPoint = DataGenService.getCollocationPoints(cells,
                    config.getNumPoints(),
                    config.getNumCoordinatePoint());
            float[] cellArea = DataGenService.getCellArea(cells, config.getNumCoordinatePoint());
            CellVectors cellVectors = dataGenService.getCellVectors(cells);

            if (config.isWriteResults()) {
                IOUtil.writeAllResultToFiles(cellArea, collocationPoint, cells, cellVectors);
                IOUtil.printFigureArea(cellArea);
            }
        } catch (DataReadException | IOException e) {
            e.printStackTrace();
        }
    }
}
