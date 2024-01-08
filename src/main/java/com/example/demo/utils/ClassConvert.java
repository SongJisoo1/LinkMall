package com.example.demo.utils;

//public class ClassConvert {
//	
//	public static <T> T castingInstance(Object obj, Class<T> clazz) {
//		return clazz != null && clazz.isInstance(obj) ? clazz.cast(obj) : null;
//	}
//
//}

public class ClassConvert {

    public static <T> T castingInstance(Object obj, Class<T> clazz) {
        if (clazz != null) {
            if (clazz.equals(Integer.class) && obj instanceof String) {
                try {
                    return clazz.cast(Integer.parseInt((String) obj));
                } catch (NumberFormatException e) {
                    return null; // 변환에 실패한 경우
                }
            } else {
                return clazz.isInstance(obj) ? clazz.cast(obj) : null;
            }
        }
        return null;
    }
}
