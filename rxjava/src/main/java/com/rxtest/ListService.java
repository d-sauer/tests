package com.rxtest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Stream;

/**
 * Created by davor on 22/10/15.
 */
@Service
public class ListService {

    private Queue<Data> data = new ConcurrentLinkedDeque<>();

    public Data addData(Data data) {
        this.data.add(data);

        return data;
    }

    public Data addData(String data) {
        Data lData = new Data(data);
        this.data.add(lData);

        return lData;
    }

    public Stream<Data> getStream() {
        return data.stream();
    }

    public List<Data> getData() {
        return Arrays.asList(data.toArray(new Data[data.size()]));
    }

    public Queue<Data> get() {
        return data;
    }

    public static class Data {
        private Long datetime;

        private String data;

        public Data(String data) {
            this(data, LocalDateTime.now());
        }

        public Data(String data, LocalDateTime datetime) {
            this.data = data;
            this.datetime = datetime.toEpochSecond(ZoneOffset.UTC);
        }

        public Long getDateTime() {
            return datetime;
        }

        @JsonIgnore
        public LocalDateTime getLocalDateTime() {
            return LocalDateTime.ofEpochSecond(this.datetime, 0, ZoneOffset.UTC);
        }

        public String getData() {
            return data;
        }
    }
}
