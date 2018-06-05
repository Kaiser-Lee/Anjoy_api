/**
 * EASLoginProxyService.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.coracle.dms.webservice.service;

import com.coracle.dms.webservice.proxy.EASLoginProxy;

public interface EASLoginProxyService extends javax.xml.rpc.Service {
    public java.lang.String getEASLoginAddress();

    public EASLoginProxy getEASLogin() throws javax.xml.rpc.ServiceException;

    public EASLoginProxy getEASLogin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
