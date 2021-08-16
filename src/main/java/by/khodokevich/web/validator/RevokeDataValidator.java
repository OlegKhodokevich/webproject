package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.util.RegexpManager.getRegexp;

public class RevokeDataValidator {
    private static final Logger logger = LogManager.getLogger(RevokeDataValidator.class);

    private static final String KEY_REGEXP_DESCRIPTION = "regexp.revoke.description";

    public static boolean isDescriptionValid(String description) throws ServiceException {
        if (description == null) {
            logger.error("Description = null.");
            throw new ServiceException("Description = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_DESCRIPTION);
        return description.matches(regexp);
    }
}
