package com.mec.ejb.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.lang.annotation.RetentionPolicy;

@ViewScoped
@Named
@Stereotype
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedView {

}
