package com.bootiful.ws;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-10-20T16:30:43.884+03:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://ws.bootiful.com/", name = "BootifulWS")
@XmlSeeAlso({ObjectFactory.class})
public interface BootifulWS {

    @WebResult(name = "user", targetNamespace = "")
    @RequestWrapper(localName = "users", targetNamespace = "http://ws.bootiful.com/", className = "com.bootiful.ws.Users")
    @WebMethod
    @ResponseWrapper(localName = "usersResponse", targetNamespace = "http://ws.bootiful.com/", className = "com.bootiful.ws.UsersResponse")
    public java.util.List<com.bootiful.ws.User> users();
}
