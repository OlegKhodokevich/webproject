package by.khodokevich.web.model.dao;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.entity.Revoke;

import java.util.List;
import java.util.Optional;
/**
 * This interface manage entity revoke in database.
 * It is used for select create or update information connected with revoke.
 */
public interface RevokeDao {
    /**
     * Method search executor's revokes by executor id.
     *
     * @param userExecutorId of executor
     * @return  List of revokes
     * @throws DaoException if can't execute query
     */
    List<Revoke> findAllExecutorRevoke(long userExecutorId) throws DaoException;
    /**
     * Method search revoke by contractId
     *
     * @param ContractId of contract
     * @return optional revoke
     * @throws DaoException if can't execute query
     */
    Optional<Revoke> findEntityByContractId(long ContractId) throws DaoException;
}
