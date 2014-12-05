package org.sebcel.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "TestService", targetNamespace = "http://org.sebcel/testservice")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface TestService {


    @WebMethod(operationName = "GetStatus", action = "http://org.sebcel/GetStatus")
    @WebResult(name = "getStatusResponse", targetNamespace = "http://org.sebcel/phoenix", partName = "getStatusResponse")
    public GetStatusResponse getStatus(
        @WebParam(name = "getStatusRequest", targetNamespace = "http://org.sebcel/phoenix", partName = "getStatusRequest")
        GetStatusRequest getStatusRequest);
}