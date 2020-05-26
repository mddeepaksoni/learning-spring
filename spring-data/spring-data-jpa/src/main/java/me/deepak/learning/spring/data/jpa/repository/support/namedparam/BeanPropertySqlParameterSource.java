package me.deepak.learning.spring.data.jpa.repository.support.namedparam;

public class BeanPropertySqlParameterSource extends org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource {

    public BeanPropertySqlParameterSource(Object object) {
        super(object);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        Object value = super.getValue(paramName);

        for (JavaTypeToSqlType javaTypeToSqlType : JavaTypeToSqlType.values()) {
            value = javaTypeToSqlType.transform(value);
        }

        return value;
    }
}
