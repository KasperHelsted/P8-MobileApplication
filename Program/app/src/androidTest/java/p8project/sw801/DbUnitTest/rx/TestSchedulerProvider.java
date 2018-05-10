package p8project.sw801.DbUnitTest.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.utils.rx.SchedulerProvider;


public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler mTestScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.mTestScheduler = testScheduler;
    }

    @Override
    public Scheduler computation() {
        return mTestScheduler;
    }

    @Override
    public Scheduler io() {
        return mTestScheduler;
    }

    @Override
    public Scheduler ui() {
        return mTestScheduler;
    }
}