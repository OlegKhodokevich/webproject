package by.khodokevich.web.model.service;

import by.khodokevich.web.model.entity.Executor;
import by.khodokevich.web.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Interface serve operations with executor.
 */
public interface ExecutorService {

    /**
     * Method searches all confirmed executors.
     *
     * @return List of executors
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Executor> findAllExecutors() throws ServiceException;

    /**
     * Method searches executor by id.
     *
     * @return option executor
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    Optional<Executor> findDefineExecutor(long executorId) throws ServiceException;
}
