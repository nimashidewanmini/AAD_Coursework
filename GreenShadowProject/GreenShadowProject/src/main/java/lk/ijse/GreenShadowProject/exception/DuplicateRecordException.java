package lk.ijse.GreenShadowProject.exception;


import org.hibernate.service.spi.ServiceException;

public class DuplicateRecordException extends ServiceException {
    public DuplicateRecordException(String message) {
        super(message);
    }
}