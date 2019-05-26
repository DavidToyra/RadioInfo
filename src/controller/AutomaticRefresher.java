/*
 *  AutomaticRefresher.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package controller;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * 1 hour timer in a swingworker, the thread sleeps 1 second
 * and then updates its counter, when it reaches 3600(1 hour)
 * it calls the controller to perform the updates.
 */
public class AutomaticRefresher extends SwingWorker
{
    int HOUR_IN_SECOND = 3600;
    Controller controller;

    /**
     * Constructor
     * @param controller the main controller
     */
    public AutomaticRefresher(Controller controller)
    {
        this.controller = controller;

    }

    /**
     * Background task, the thread sleeps for an hour and then
     * returns to done.
     * @return
     * @throws Exception
     */
    @Override
    protected Object doInBackground() throws InterruptedException {
        int counter = 0;
        while(!isCancelled())
        {
            Thread.sleep(1000);
            counter++;
            if(counter == HOUR_IN_SECOND)
            {
                break;
            }
        }
        return null;
    }

    /**
     * Calls the update method in the controller when the timer
     * is done.
     */
    @Override
    protected void done()
    {
        try {
            get();
        } catch (InterruptedException e) {
            controller.getGui().exceptionPane(""+e);
        } catch (ExecutionException e) {
            controller.getGui().exceptionPane(""+e);
        }
        controller.updateSchedule();
        controller.startAutomaticUpdater();
    }
}
