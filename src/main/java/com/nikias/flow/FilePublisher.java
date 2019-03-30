package com.nikias.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Flow;


import static java.nio.file.StandardWatchEventKinds.*;

public class FilePublisher implements Flow.Publisher {


   public static void main(String[] args) throws Exception {
       FileSubscriber subscriber = new FileSubscriber();
       FilePublisher fp = new FilePublisher();
       fp.watchFiles();
       fp.subscribe(subscriber);
   }

   Logger logger = LoggerFactory.getLogger(FilePublisher.class);

   private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(1);
   private SubmissionPublisher<File> sb = new SubmissionPublisher<File>(EXECUTOR, Flow.defaultBufferSize());
   WatchService watcher = FileSystems.getDefault().newWatchService();
   private static final int INITIAL_DELAY = 1;
   private static final int DELAY = 5;

   public FilePublisher() throws IOException {
       try {
           Path directory = new File("c:/tmp/").toPath();
           WatchKey key = directory.register(watcher,
                   ENTRY_CREATE,
                   ENTRY_DELETE,
                   ENTRY_MODIFY);
       } catch (IOException x) {
           logger.error("Error while watching!", x);
       }
   }

   public void watchFiles() throws Exception {
       ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
       Runnable monitorFiles = () -> {
           try {

               WatchKey key;
               try {
                   key = watcher.take();
               } catch (InterruptedException x) {
                   return;
               }

               for (WatchEvent<?> event : key.pollEvents()) {
                   WatchEvent.Kind<?> kind = event.kind();

                   WatchEvent<Path> ev = (WatchEvent<Path>) event;
                   Path filename = ev.context();
                   sb.submit(filename.toFile());
               }

           } catch(Exception e){
               logger.error("AN error occured while watching files", e);
           }
       };
       executor.scheduleWithFixedDelay(monitorFiles, INITIAL_DELAY, DELAY, TimeUnit.SECONDS);
   }

   @Override
   public void subscribe(Flow.Subscriber subscriber) {
       sb.subscribe(subscriber);
   }
}
