package ru.diploma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class AbstractExecutorService {

    protected ExecutorService executor;

    protected <T> List<T> execute(List<? extends Callable<T>> tasks) {
        if (executor == null) {
            throw new RuntimeException("Executor must be initialized");
        }

        System.out.println(String.format("Starting [%s]", getTasksBatchName()));
        long startTs = System.currentTimeMillis();

        final CompletionService<T> completionService = new ExecutorCompletionService<>(executor);
        tasks.forEach(completionService::submit);

        List<T> out = new ArrayList<>();

        int completed = 0;
        while (completed < tasks.size()) {
            try {
                Future<T> future = completionService.take();
                out.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Could not complete task " + e.getCause());
            }
            completed++;
        }

        long diff = System.currentTimeMillis() - startTs;
        System.out.println(String.format("Finished [%s] in %d ms", getTasksBatchName(), diff));

        return out;
    }

    protected abstract String getTasksBatchName();
}
