package by.khodokevich.web.dao.impl;

import static by.khodokevich.web.dao.impl.ExecutorColumnName.*;

import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.entity.*;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExecutorDaoImpl extends AbstractDao<Executor> { // TODO add implementation
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_ALL_EXECUTORS = "SELECT IdUser, FirstName, LastName, EMail, Phone, Region, City, Status, RoleUser FROM users JOIN regions ON users.IdRegion = regions.IdRegion WHERE RoleUser = \"executor\";";  //TODO password?
    private static final String SQL_SELECT_DEFINED_EXECUTORS = "SELECT IdUser, FirstName, LastName, EMail, Phone, Region, City, Status, RoleUser FROM users JOIN regions ON users.IdRegion = regions.IdRegion WHERE RoleUser = \"executor\" AND IdUser = ?;";
    private static final String SQL_DELETE_DEFINED_EXECUTORS_BY_ID = "DELETE users  FROM users WHERE RoleUser = \"executor\" AND IdUser = ?;";
    private static final String SQL_DELETE_DEFINED_EXECUTORS_BY_EMAIL = "DELETE users FROM users WHERE RoleUser = \"executor\" AND EMail = ?;";
    private static final String SQL_INSERT_EXECUTORS = "INSERT INTO users(FirstName, LastName, EMail, Phone, IdRegion, City, Status, RoleUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_EXECUTORS = "UPDATE users SET FirstName = ?, LastName = ?, EMail = ?, Phone = ?, IdRegion = ?, City = ?, Status = ?, RoleUser = ? WHERE RoleUser = \"executor\" AND IdUser = ?;";

    private static final String SQL_SELECT_EXECUTOROPTION = "SELECT PersonalFoto, UNP, AverageMark, NumberCompletionContracts, NumberContractsInProgress FROM executors WHERE IdUserExecutor = ?;";
    private static final String SQL_INSERT_EXECUTOROPTION = "INSERT INTO executors(IdUserExecutor, PersonalFoto, UNP, AverageMark, NumberCompletionContracts, NumberContractsInProgress) VALUES ((SELECT IdUser FROM users WHERE EMail = ?),?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_EXECUTOROPTION = "UPDATE executors SET PersonalFoto = ?, UNP = ?, AverageMark = ?, NumberCompletionContracts = ?, NumberContractsInProgress = ? WHERE IdUserExecutor = ?;";

    private static final String SQL_SELECT_SKILLS = "SELECT Specialization, Cost, Measure FROM skills JOIN specializations ON skills.IdSpecialization = specializations.IdSpecialization WHERE IdUserExecutor = ?;";
    private static final String SQL_INSERT_SKILL = "INSERT INTO skills(IdUserExecutor, IdSpecialization, Cost, Measure) VALUES ((SELECT IdUser FROM users JOIN executors ON IdUser = IdUserExecutor WHERE EMail = ?), (SELECT IdSpecialization FROM specializations WHERE Specialization = ?), ?, ?)";
    private static final String SQL_DELETE_SKILL = "DELETE skills FROM skills WHERE IdUserExecutor = ?;";

    @Override
    public List<Executor> findAll() throws DaoException {
        logger.info("Start findAll().");
        List<Executor> executors = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_EXECUTORS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                long idUser = resultSet.getLong(ID_USER);
                String login = resultSet.getString(LOGIN);
                String password = resultSet.getString(PASSWORD);
                String eMail = resultSet.getString(E_MAIL);
                String phone = resultSet.getString(PHONE);
                RegionBelarus region = RegionBelarus.valueOf(resultSet.getString(REGION).toUpperCase());
                String city = resultSet.getString(CITY);
                UserStatus status = UserStatus.valueOf(resultSet.getString(STATUS).toUpperCase());

                ExecutorOption executorOption = findExecutorOption(idUser);
                Executor executor = new Executor(idUser, login, password, eMail, phone, region, city, status, executorOption);
                logger.info("Has found next executor = " + executor);
                executors.add(executor);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next executors : " + executors);
        return executors;
    }

    @Override
    public Optional<Executor> findEntityById(long idExecutor) throws DaoException {
        logger.info("Start findEntityById(long id). Id " + idExecutor);
        Executor executor = null;

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DEFINED_EXECUTORS)) {
            statement.setLong(1, idExecutor);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long idUser = resultSet.getLong(ID_USER);
                    String login = resultSet.getString(LOGIN);
                    String password = resultSet.getString(PASSWORD);
                    String eMail = resultSet.getString(E_MAIL);
                    String phone = resultSet.getString(PHONE);
                    RegionBelarus region = RegionBelarus.valueOf(resultSet.getString(REGION).toUpperCase());
                    String city = resultSet.getString(CITY);
                    UserStatus status = UserStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                    ExecutorOption executorOption = findExecutorOption(idUser);
                    executor = new Executor(idUser, login, password, eMail, phone, region, city, status, executorOption);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next executor: " + executor);
        return Optional.ofNullable(executor);
    }

    @Override
    public boolean delete(long idExecutors) throws DaoException {
        logger.info("Start delete(long id). Executor's ID = " + idExecutors);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_DELETE_DEFINED_EXECUTORS_BY_ID)) {
            statement.setLong(1, idExecutors);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public boolean delete(Executor entity) throws DaoException {
        logger.info("Start delete(Executor entity)." + entity);
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DEFINED_EXECUTORS_BY_EMAIL)) {
            statement.setString(1, entity.geteMail());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public boolean create(Executor entity) throws DaoException {
        logger.info("Start create(Executor entity)." + entity);
        boolean result;
        if (entity.getIdUser() != 0) {
            logger.warn("Warning: User's id is already define. Id = " + entity.getIdUser());
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_INSERT_EXECUTORS)) {
            statement.setString(1, entity.getfirstName());
            statement.setString(2, entity.getlastName());
            statement.setString(3, entity.geteMail());
            statement.setString(4, entity.getPhone());
            statement.setInt(5, entity.getRegion().ordinal());
            statement.setString(6, entity.getCity());
            statement.setString(7, entity.getStatus().name().toLowerCase());
            statement.setString(8, entity.getRole().name().toLowerCase());
            numberUpdatedRows = statement.executeUpdate();

            result = createExecutorOption(entity);
            if (numberUpdatedRows == 1 & !result) {
                throw new DaoException("Executor option has not been created. Executor = " + entity);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed.");
        return result;
    }

    @Override
    public boolean update(Executor entity) throws DaoException {
        boolean result;
        logger.info("Start update(Executor entity)." + entity);
        long idUser = entity.getIdUser();
        if (idUser == 0) {
            throw new DaoException("User's id = 0. User can't be updated.");
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_UPDATE_EXECUTORS)) {
            statement.setString(1, entity.getfirstName());
            statement.setString(2, entity.getlastName());
            statement.setString(3, entity.geteMail());
            statement.setString(4, entity.getPhone());
            statement.setInt(5, entity.getRegion().ordinal());
            statement.setString(6, entity.getCity());
            statement.setString(7, entity.getStatus().name().toLowerCase());
            statement.setString(8, entity.getRole().name().toLowerCase());
            statement.setLong(9, entity.getIdUser());
            numberUpdatedRows = statement.executeUpdate();
            result = numberUpdatedRows == 1;
            if (result) {
                updateExecutorOption(entity);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;

    }

    private ExecutorOption findExecutorOption(long idUserExecutor) throws DaoException {
        ExecutorOption exetutorOption = null;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_EXECUTOROPTION)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String urlPersonalFoto = resultSet.getString(PERSONAL_FOTO);
                    String unp = resultSet.getString(UNP);
                    double averageMark = resultSet.getFloat(AVERAGE_MARK);
                    int numberCompletionContracts = resultSet.getInt(NUMBER_COMPLETION_CONTRACTS);
                    int numberContractsInProgress = resultSet.getInt(NUMBER_CONTRACTS_PROGRESS);

                    List<Skill> skills = findExecutorSkills(idUserExecutor);
                    exetutorOption = new ExecutorOption(Optional.ofNullable(unp), skills, averageMark, numberCompletionContracts, numberContractsInProgress, urlPersonalFoto);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found exetutorOption = " + exetutorOption);
        return exetutorOption;
    }

    private List<Skill> findExecutorSkills(long IdUserExecutor) throws DaoException {
        List<Skill> skills = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SKILLS)) {
            statement.setLong(1, IdUserExecutor);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String specializationString = resultSet.getString(SPECIALIZATION);
                    Specialization specialization = Specialization.valueOf(specializationString.toUpperCase());
                    String cost = resultSet.getString(COST);
                    String measureString = resultSet.getString(MEASURE);
                    UnitMeasure measure = UnitMeasure.valueOf(measureString.toUpperCase());

                    Skill skill = new Skill(specialization, cost, measure);
                    skills.add(skill);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next skills : " + skills);
        return skills;
    }

    private boolean createExecutorOption(Executor entity) throws DaoException {
        boolean result;
        ExecutorOption executorOption = entity.getExecutorOption();
        logger.info("Start createExecutorOption(Connection connection, Executor entity)." + entity);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_INSERT_EXECUTOROPTION)) {
            statement.setString(1, entity.geteMail());

            statement.setString(2, executorOption.getUrlPersonalFoto());
            Optional<String> unpOptional = executorOption.getUNP();
            if (unpOptional.isPresent()) {
                statement.setString(3, unpOptional.get());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }
            statement.setFloat(4, (float) executorOption.getAverageMark());
            statement.setInt(5, executorOption.getNumberCompletionContracts());
            statement.setInt(6, executorOption.getNumberContractsInProgress());

            numberUpdatedRows = statement.executeUpdate();
            result = createExecutorSkills(entity);
            if (numberUpdatedRows == 1 & !result) {
                throw new DaoException("Skills has not been created. Executor = " + entity);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed.");
        return result;
    }

    private boolean createExecutorSkills(Executor entity) throws DaoException {
        List<Skill> skills = entity.getExecutorOption().getSkills();
        logger.info("Start createExecutorSkills(Connection connection, Executor entity)." + entity);
        int numberUpdatedRows = 0;
        for (int i = 0; i < skills.size(); i++) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SKILL)) {
                Skill skill = skills.get(i);
                statement.setString(1, entity.geteMail());
                statement.setString(2, skill.getSpecialization().name().toLowerCase());
                statement.setString(3, skill.getCost());
                statement.setString(4, skill.getMeasure().name().toLowerCase());
                numberUpdatedRows = numberUpdatedRows + statement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Prepare statement can't be take from connection." + e.getMessage());
                throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
            }
        }
        boolean result = numberUpdatedRows == skills.size();
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed.");
        return result;
    }

    public boolean updateExecutorOption(Executor entity) throws DaoException {
        logger.info("Start updateExecutorOption(Connection connection, Executor entity)." + entity);
        ExecutorOption executorOption = entity.getExecutorOption();
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EXECUTOROPTION)) {
            statement.setString(1, executorOption.getUrlPersonalFoto());
            Optional<String> unpOptional = executorOption.getUNP();
            if (unpOptional.isPresent()) {
                statement.setString(2, unpOptional.get());
            } else {
                statement.setNull(2, Types.VARCHAR);
            }
            statement.setFloat(3, (float) executorOption.getAverageMark());
            statement.setInt(4, executorOption.getNumberCompletionContracts());
            statement.setInt(5, executorOption.getNumberContractsInProgress());
            statement.setLong(6, entity.getIdUser());

            numberUpdatedRows = statement.executeUpdate();
            updateExecutorSkills(entity);
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed.");
        return result;
    }

    private int updateExecutorSkills(Executor entity) throws DaoException {
        logger.info("Start updateExecutorSkills(Connection connection, Executor entity)." + entity);
        int numberSubstitutedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_SKILL)) {
            statement.setLong(1, entity.getIdUser());
            numberSubstitutedRows = statement.executeUpdate();
            statement.close();
            boolean result = createExecutorSkills(entity);
            if (!result) {
                throw new DaoException("Skills not insert in db. Entity = " + entity);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        logger.info("Has updated " + numberSubstitutedRows + " rows.");
        return numberSubstitutedRows;
    }

}
