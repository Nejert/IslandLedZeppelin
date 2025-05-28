package com.javarush.island.kazakov.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Metric {
    double weight();

    int maxQuantity();

    int maxSteps() default 0;

    double saturation() default 0;

    String icon();

    String imageIcon();
}
