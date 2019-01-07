package com.wan37.logic.test.database.convert;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class UidSetConverterImpl implements AttributeConverter<Set<Long>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Long> longs) {
        if (longs == null || longs.isEmpty()) {
            return null;
        }

        return StringUtils.arrayToDelimitedString(longs.toArray(), ",");
    }

    @Override
    public Set<Long> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return new HashSet<>();
        }

        return Arrays.stream(s.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }
}
