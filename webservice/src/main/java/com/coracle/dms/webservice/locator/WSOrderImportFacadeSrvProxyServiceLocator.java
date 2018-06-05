/**
 * WSOrderImportFacadeSrvProxyServiceLocator.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.coracle.dms.webservice.locator;

import com.coracle.dms.webservice.proxy.WSOrderImportFacadeSrvProxy;
import com.coracle.dms.webservice.service.WSOrderImportFacadeSrvProxyService;
import com.coracle.dms.webservice.stub.WSOrderImportFacadeSoapBindingStub;

public class WSOrderImportFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements WSOrderImportFacadeSrvProxyService {

    public WSOrderImportFacadeSrvProxyServiceLocator() {
    }


    public WSOrderImportFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSOrderImportFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSOrderImportFacade
    private java.lang.String WSOrderImportFacade_address = "http://192.168.0.134:6888/ormrpc/services/WSOrderImportFacade";

    public java.lang.String getWSOrderImportFacadeAddress() {
        return WSOrderImportFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSOrderImportFacadeWSDDServiceName = "WSOrderImportFacade";

    public java.lang.String getWSOrderImportFacadeWSDDServiceName() {
        return WSOrderImportFacadeWSDDServiceName;
    }

    public void setWSOrderImportFacadeWSDDServiceName(java.lang.String name) {
        WSOrderImportFacadeWSDDServiceName = name;
    }

    public WSOrderImportFacadeSrvProxy getWSOrderImportFacade() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSOrderImportFacade_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSOrderImportFacade(endpoint);
    }

    public WSOrderImportFacadeSrvProxy getWSOrderImportFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WSOrderImportFacadeSoapBindingStub _stub = new WSOrderImportFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSOrderImportFacadeWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSOrderImportFacadeEndpointAddress(java.lang.String address) {
        WSOrderImportFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSOrderImportFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                WSOrderImportFacadeSoapBindingStub _stub = new WSOrderImportFacadeSoapBindingStub(new java.net.URL(WSOrderImportFacade_address), this);
                _stub.setPortName(getWSOrderImportFacadeWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSOrderImportFacade".equals(inputPortName)) {
            return getWSOrderImportFacade();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.0.134:6888/ormrpc/services/WSOrderImportFacade", "WSOrderImportFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.0.134:6888/ormrpc/services/WSOrderImportFacade", "WSOrderImportFacade"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("WSOrderImportFacade".equals(portName)) {
            setWSOrderImportFacadeEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
