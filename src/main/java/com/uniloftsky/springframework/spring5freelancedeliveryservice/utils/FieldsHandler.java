package com.uniloftsky.springframework.spring5freelancedeliveryservice.utils;

import java.lang.reflect.Field;

public final class FieldsHandler {

    public static <T> void handleFields(T dto, T patchingDTO) {
        Field[] objectFields = dto.getClass().getDeclaredFields();
        for (Field field : objectFields) {
            field.setAccessible(true);
            try {
                if (field.get(dto) != null) {
                    field.set(patchingDTO, field.get(dto));
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
