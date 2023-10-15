package com.andersonreiner.todolist.utils;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    
    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyName(source));
    }

    public static String[] getNullPropertyName(Object souce){
        final BeanWrapper src = new BeanWrapperImpl(souce);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> empyNames = new HashSet<>();

        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                empyNames.add(pd.getName());
            }
        }

        String[] resulty = new String [empyNames.size()];
        return empyNames.toArray(resulty);
    }

}
