/**
 * WSGuideHrInforServiceFacadeSrvProxyServiceLocator.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.coracle.dms.webservice.locator;

import com.coracle.dms.webservice.proxy.WSGuideHrInforServiceFacadeSrvProxy;
import com.coracle.dms.webservice.service.WSGuideHrInforServiceFacadeSrvProxyService;
import com.coracle.dms.webservice.stub.WSGuideHrInforServiceFacadeSoapBindingStub;

public class WSGuideHrInforServiceFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements WSGuideHrInforServiceFacadeSrvProxyService {

    public WSGuideHrInforServiceFacadeSrvProxyServiceLocator() {
    }


    public WSGuideHrInforServiceFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSGuideHrInforServiceFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSGuideHrInforServiceFacade
    private java.lang.String WSGuideHrInforServiceFacade_address = "http://192.168.0.134:6888/ormrpc/services/WSGuideHrInforServiceFacade";

    public java.lang.String getWSGuideHrInforServiceFacadeAddress() {
        return WSGuideHrInforServiceFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSGuideHrInforServiceFacadeWSDDServiceName = "WSGuideHrInforServiceFacade";

    public java.lang.String getWSGuideHrInforServiceFacadeWSDDServiceName() {
        return WSGuideHrInforServiceFacadeWSDDServiceName;
    }

    public void setWSGuideHrInforServiceFacadeWSDDServiceName(java.lang.String name) {
        WSGuideHrInforServiceFacadeWSDDServiceName = name;
    }

    public WSGuideHrInforServiceFacadeSrvProxy getWSGuideHrInforServiceFacade() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSGuideHrInforServiceFacade_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSGuideHrInforServiceFacade(endpoint);
    }

    public WSGuideHrInforServiceFacadeSrvProxy getWSGuideHrInforServiceFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WSGuideHrInforServiceFacadeSoapBindingStub _stub = new WSGuideHrInforServiceFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSGuideHrInforServiceFacadeWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSGuideHrInforServiceFacadeEndpointAddress(java.lang.String address) {
        WSGuideHrInforServiceFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSGuideHrInforServiceFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                WSGuideHrInforServiceFacadeSoapBindingStub _stub = new WSGuideHrInforServiceFacadeSoapBindingStub(new java.net.URL(WSGuideHrInforServiceFacade_address), this);
                _stub.setPortName(getWSGuideHrInforServiceFacadeWSDDServiceName());
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
        if ("WSGuideHrInforServiceFacade".equals(inputPortName)) {
            return getWSGuideHrInforServiceFacade();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(WSGuideHrInforServiceFacade_address, "WSGuideHrInforServiceFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(WSGuideHrInforServiceFacade_address, "WSGuideHrInforServiceFacade"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("WSGuideHrInforServiceFacade".equals(portName)) {
            setWSGuideHrInforServiceFacadeEndpointAddress(address);
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
