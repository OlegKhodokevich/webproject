package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.util.RegexpManager.getRegexp;
/**
 * Class for checking revoke data
 */
public class RevokeDataValidator {
    private static final Logger logger = LogManager.getLogger(RevokeDataValidator.class);

    private static final String KEY_REGEXP_DESCRIPTION = "regexp.revoke.description";

    /**
     * Method validate description of revoke
     *
     * @param description of revoke
     * @return true if description is valid
     * @throws ServiceException if description is null
     */
    public static boolean isDescriptionValid(String description) throws ServiceException {
        if (description == null) {
            logger.error("Description = null.");
            throw new ServiceException("Description = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_DESCRIPTION);
        return description.matches(regexp);
    }
}
