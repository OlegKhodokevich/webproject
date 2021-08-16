package by.khodokevich.web.model.dao;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.entity.Executor;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.Pagination;

import java.util.List;
import java.util.Optional;

/**
 * This interface manage entity executor in database.
 * It is used for select create or update information connected with executor.
 */
public interface ExecutorDao {
    Optional<Executor> findExecutorByContractId(long contractId) throws DaoException;
}
