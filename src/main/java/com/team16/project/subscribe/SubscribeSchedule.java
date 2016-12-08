package com.team16.project.subscribe;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SubscribeSchedule {
    public SubscribeSchedule() throws SQLException {
        periodicTask();
    }

    public static void periodicTask() throws SQLException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        SubscribeService subscribeService = new SubscribeService();

        final Runnable sender = new Runnable() {
            public void run() {
                try {
                    subscribeService.listUser();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        final ScheduledFuture<?> senderHandle =
                scheduler.scheduleAtFixedRate(sender, 0, 10, SECONDS);

    }
}
