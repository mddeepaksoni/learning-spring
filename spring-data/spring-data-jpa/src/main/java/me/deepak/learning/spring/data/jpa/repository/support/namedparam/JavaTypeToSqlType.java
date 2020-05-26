package me.deepak.learning.spring.data.jpa.repository.support.namedparam;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Predicate;

public enum JavaTypeToSqlType {

    LOCAL_DATE_TO_SQL_DATE(item -> item instanceof LocalDate, item -> Date.valueOf((LocalDate) item)),
    LOCAL_DATE_TIME_TO_SQL_TIMESTAMP(item -> item instanceof LocalDateTime, item -> Timestamp.valueOf((LocalDateTime) item));

    private Predicate predicate;
    private Function transformer;

    JavaTypeToSqlType(Predicate predicate, Function transformer) {
        this.predicate = predicate;
        this.transformer = transformer;
    }

    public Object transform(Object item) {
        return this.predicate.test(item)
                ? this.transformer.apply(item)
                : item;
    }
}
