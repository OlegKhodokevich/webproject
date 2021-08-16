package by.khodokevich.web.model.dao;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.entity.Revoke;

import java.util.List;
import java.util.Optional;

public interface RevokeDao {

    List<Revoke> findAllExecutorRevoke(long userExecutorId) throws DaoException;

    Optional<Revoke> findEntityByContractId(long ContractId) throws DaoException;
}
