package sherpath.edu.app.logging.logback;

import ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.RolloverFailure;

/**
 * Created by Lakshmi in Nov 2015.
 */
@NoAutoStart
public class StartupTimeBasedTriggeringPolicy<E>
        extends DefaultTimeBasedFileNamingAndTriggeringPolicy<E> {


    @Override
    public void start() {
        super.start();
        nextCheck = 0L;
        isTriggeringEvent(null, null);
        try {
            tbrp.rollover();
        } catch (RolloverFailure e) {
            //Do nothing
        }
    }

}
