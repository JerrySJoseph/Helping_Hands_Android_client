package com.jstechnologies.helpinghands.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseExecutor implements Executor {

    private final Executor executor;

    public DatabaseExecutor() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        executor.execute(runnable);
    }
}