package io.quesar.starter;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class QuesarException extends RuntimeException {

    public QuesarException(String msg) {
        super(msg);
    }

    public QuesarException(Exception e) {
        super(e);
    }

    public QuesarException(String msg, Exception e) {
        super(msg, e);
    }
}
