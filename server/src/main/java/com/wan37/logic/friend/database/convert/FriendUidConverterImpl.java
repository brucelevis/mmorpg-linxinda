package com.wan37.logic.friend.database.convert;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 玩家好友id集合持久化成字符串到数据库
 *
 * @author linda
 */
public class FriendUidConverterImpl implements AttributeConverter<Set<Long>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Long> longs) {
        return longs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Long> convertToEntityAttribute(String s) {
        if (s == null || "".equals(s)) {
            return new HashSet<>();
        }

        return Arrays.stream(s.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }
}
