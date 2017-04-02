package com.atithi.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@NameBinding
//@Retention(RUNTIME)
//@Target({TYPE, METHOD})
public @interface Secured {

}
