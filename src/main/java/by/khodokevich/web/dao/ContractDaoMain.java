package by.khodokevich.web.dao;

import by.khodokevich.web.connection.CustomConnectionPool;
import by.khodokevich.web.dao.impl.ContractDaoImpl;
import by.khodokevich.web.entity.CompletionContractStatus;
import by.khodokevich.web.entity.ConcludedContractStatus;
import by.khodokevich.web.entity.Contract;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.PoolConnectionException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ContractDaoMain {
    public static void main(String[] args) throws PoolConnectionException {
        AbstractDao contractDao =new ContractDaoImpl();
        Connection connection = CustomConnectionPool.getInstance().getConnection();
        try {
            contractDao.setConnection(connection);
            List<Contract> contracts = contractDao.findAll();
            System.out.println(contracts);
            Optional<Contract> contract = contractDao.findEntityById(5);
            System.out.println(contract);
//            contractDao.delete(5);
//            Contract contract1 = new Contract(13,18, ConcludedContractStatus.NON_CONCLUDED, CompletionContractStatus.NON_COMPLETED);
//            contractDao.delete(contract1);
//            Contract contractInsert = new Contract(13,18, ConcludedContractStatus.NON_CONCLUDED, CompletionContractStatus.NON_COMPLETED);
//            System.out.println(contractDao.create(contractInsert));
            Contract contractUpdate = new Contract(11,13,18, ConcludedContractStatus.CONCLUDED, CompletionContractStatus.COMPLETED);
            System.out.println(contractDao.update(contractUpdate));
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

}
