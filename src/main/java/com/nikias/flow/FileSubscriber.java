package com.nikias.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.Flow;

/**
 * Based on : https://aboullaite.me/reactive-streams-example-java-9/
 */
public class FileSubscriber implements Flow.Subscriber<File> {
    Logger logger = LoggerFactory.getLogger(FilePublisher.class);

    private final String id = UUID.randomUUID().toString();
    private Flow.Subscription subscription;
    private static final int SUB_REQ=5;
    private static final int SLEEP=1000;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        logger.info(  "subscriber: "+ id +" ==> Subscribed");
        this.subscription = subscription;
        subscription.request(SUB_REQ);
    }

    @Override
    public void onNext(File file) {
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException e) {
            logger.info("An error has occured: {}", e);
        }
        logger.info(file.toString());
        subscription.request(SUB_REQ);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.info("An error occured", throwable);

    }

    @Override
    public void onComplete() {
        logger.info("Done!");

    }
}
