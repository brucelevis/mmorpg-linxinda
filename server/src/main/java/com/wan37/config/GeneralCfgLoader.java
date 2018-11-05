package com.wan37.config;

import java.util.List;
import java.util.Optional;

public interface GeneralCfgLoader<T> {

    List<T> loads();

    Optional<T> load(Integer id);
}
