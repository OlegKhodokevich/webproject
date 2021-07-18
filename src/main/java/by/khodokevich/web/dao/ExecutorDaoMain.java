package by.khodokevich.web.dao;

import by.khodokevich.web.connection.CustomConnectionPool;
import by.khodokevich.web.dao.impl.ExecutorDaoImpl;
import by.khodokevich.web.entity.*;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.PoolConnectionException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExecutorDaoMain {
    public static void main(String[] args) {
        AbstractDao executorDao = new ExecutorDaoImpl();
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            executorDao.setConnection(connection);
        } catch (PoolConnectionException e) {
            e.printStackTrace();
        }
        try {
            List<Executor> executors = executorDao.findAll();
            System.out.println(executors);

//
//            Optional<Executor> executor = executorDao.findEntityById(8);
//            System.out.println(executor);
//            Executor executor1 = executorDao.findEntityById(8).get();
//            System.out.println(executorDao.delete(executor1));
//            Skill skill1 = new Skill(Specialization.WALLPAPERING, " from 2.5", UnitMeasure.M2);
//            Skill skill2 = new Skill(Specialization.WALLPAPERING, " from 2.5", UnitMeasure.M2);
//            List<Skill> skills = new ArrayList<>();
//            skills.add(skill1);
//            skills.add(skill2);
//            ExecutorOption executorOption = new ExecutorOption(Optional.of("100000001") , skills,2.5, 2, 1, "D:\\foto\1.png");
//            Executor executor2 = new Executor("bortalamey2" , BCrypt.hashpw("passwordbortalamey", BCrypt.gensalt(13)), "oleg.khodokevich@mail.ru", "+ 375 29 337 25 47 ", RegionBelarus.HOMYEL_REGION, "Homel", UserStatus.DECLARED, executorOption);
//            System.out.println(executorDao.delete(executor2));
//            executorDao.create(executor2);
//            Skill skill3 = new Skill(Specialization.WALLPAPERING, " 6 from 2.5", UnitMeasure.M2);
//            Skill skill4 = new Skill(Specialization.WALLPAPERING, " 6 from 2.5", UnitMeasure.M2);
//            List<Skill> skills1 = new ArrayList<>();
//            skills1.add(skill3);
//            skills1.add(skill4);
//            ExecutorOption executorOption1 = new ExecutorOption(Optional.of("100000006") , skills1,2.5, 2, 1, "D:\\foto\1.png");
//            Executor executor3 = new Executor(18,"bortalamey60" , BCrypt.hashpw("passwordbortalamey", BCrypt.gensalt(13)), "oleg.khodokevich@mail.ru", "+ 375 29 337 25 47 ", RegionBelarus.HOMYEL_REGION, "Homel", UserStatus.DECLARED, executorOption1);
//            System.out.println(executorDao.update(executor3));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
