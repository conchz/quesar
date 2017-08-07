package io.quesar.starter;

import io.quesar.starter.util.CryptoUtils;

import java.io.Serializable;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class Job implements Serializable {

    private String id;
    private String topic;
    private int delay;
    private int ttr;
    private String body;

    public Job() {
        this.id = CryptoUtils.randomStringWithDateTimePrefix(32);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getTtr() {
        return ttr;
    }

    public void setTtr(int ttr) {
        this.ttr = ttr;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Job{" +
            "id='" + id + '\'' +
            ", topic='" + topic + '\'' +
            ", delay=" + delay +
            ", ttr=" + ttr +
            ", body='" + body + '\'' +
            '}';
    }
}
