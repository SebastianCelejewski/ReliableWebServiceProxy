
package org.sebcel.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetStatusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetStatusResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://myinsight.globalinsight.com/phoenix}BasicResponse">
 *       &lt;sequence>
 *         &lt;element name="contractVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="implementationVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="statusDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetStatusResponse", propOrder = {
    "contractVersion",
    "implementationVersion",
    "statusCode",
    "statusDescription"
})
public class GetStatusResponse
    extends BasicResponse
{

    @XmlElement(required = true)
    protected String contractVersion;
    protected String implementationVersion;
    protected int statusCode;
    protected String statusDescription;

    /**
     * Gets the value of the contractVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractVersion() {
        return contractVersion;
    }

    /**
     * Sets the value of the contractVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractVersion(String value) {
        this.contractVersion = value;
    }

    /**
     * Gets the value of the implementationVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImplementationVersion() {
        return implementationVersion;
    }

    /**
     * Sets the value of the implementationVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImplementationVersion(String value) {
        this.implementationVersion = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     */
    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets the value of the statusDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDescription(String value) {
        this.statusDescription = value;
    }

}
