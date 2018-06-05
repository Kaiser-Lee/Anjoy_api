/**
 * WSGuideHrInforServiceFacadeSrvProxy.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.coracle.dms.webservice.proxy;

import com.coracle.dms.webservice.model.WSInvokeException;

public interface WSGuideHrInforServiceFacadeSrvProxy extends java.rmi.Remote {
    public java.lang.String getBaseData(java.lang.String tableName) throws java.rmi.RemoteException, WSInvokeException;

    public java.lang.String guideIn(java.lang.String guideInInfor, java.lang.String guideSubmitDate, java.lang.String guidellc, java.lang.String entryname) throws java.rmi.RemoteException, WSInvokeException;

    public java.lang.String osfTest(java.lang.String osfServiceName) throws java.rmi.RemoteException, WSInvokeException;

    public java.lang.String guideOut(java.lang.String guideOutInfor, java.lang.String guideSubmitDate, java.lang.String guidellc, java.lang.String entryname) throws java.rmi.RemoteException, WSInvokeException;
}
