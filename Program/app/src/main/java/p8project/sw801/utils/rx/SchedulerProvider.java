package p8project.sw801.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
