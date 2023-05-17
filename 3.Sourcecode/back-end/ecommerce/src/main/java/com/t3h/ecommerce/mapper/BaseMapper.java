package com.t3h.ecommerce.mapper;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseMapper {
    public static <T> T mapObjectWithClass(Object o, Class<T> clazz) {
        T casted = null;
        try {
            casted = clazz.cast(o);
            log.info("Cast object to class {} successful", clazz.getName());
        } catch (ClassCastException e) {
            log.warn("Error at mapObjectWithClass static function with message: {}", e.getMessage());
        }
        return casted;
    }
}
