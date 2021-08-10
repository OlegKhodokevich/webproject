package by.khodokevich.web.model.service;

import by.khodokevich.web.model.entity.Executor;
import by.khodokevich.web.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ExecutorService {
    List<Executor> findAllExecutors() throws ServiceException;

    Optional<Executor> findDefineExecutor(long executorId) throws ServiceException;
}
