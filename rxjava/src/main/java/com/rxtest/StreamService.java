package com.rxtest;

import com.rxtest.ListService.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Queue;

/**
 * Created by davor on 22/10/15.
 */

// 22:11 -> https://www.youtube.com/watch?v=ET_SMMXkE5s&feature=youtu.be
@Service
public class StreamService {

    public static final Logger logger = LoggerFactory.getLogger(StreamService.class);

    @Autowired
    private ListService listService;

        public Observable<Data> processData() {
        final Queue<Data> queue = listService.get();

        return Observable.<Data>create(o -> {
            while(!o.isUnsubscribed()) {
                Data data = queue.poll();
                if (data != null) {
                    logger.info("Process data: {}", data.getData());
                    o.onNext(data);
                } else {
                    logger.info("Complete with processing");
                    o.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io());
    }


}
