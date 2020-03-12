package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.error.DataReadException;
import ru.diploma.util.IOUtil;

import java.io.*;

@Component
public class ProcessingService {

    private DataGenService dataGenService;
    private IOService ioService;
    private ApplicationConfig config;

    public ProcessingService(DataGenService dataGenService, IOService ioService, ApplicationConfig config) {
        this.dataGenService = dataGenService;
        this.ioService = ioService;
        this.config = config;
    }

    public void runProcessing() {
        try {
            float[][][] cells = ioService.getArrayOfCellsFromFile();
            float[][] collocationPoint = dataGenService.getCollocationPoints(cells);
            float[] cellArea = dataGenService.getCellArea(cells);
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
