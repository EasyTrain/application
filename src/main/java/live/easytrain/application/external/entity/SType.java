
package live.easytrain.application.external.entity;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for sType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tl" type="{}tlType"/&gt;
 *         &lt;element name="ar" type="{}arType" minOccurs="0"/&gt;
 *         &lt;element name="dp" type="{}dpType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sType", propOrder = {
    "tl",
    "ar",
    "dp"
})
public class SType {

    @XmlElement(required = true)
    protected TlType tl;
    protected ArType ar;
    @XmlElement(required = true)
    protected DpType dp;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the tl property.
     * 
     * @return
     *     possible object is
     *     {@link TlType }
     *     
     */
    public TlType getTl() {
        return tl;
    }

    /**
     * Sets the value of the tl property.
     * 
     * @param value
     *     allowed object is
     *     {@link TlType }
     *     
     */
    public void setTl(TlType value) {
        this.tl = value;
    }

    /**
     * Gets the value of the ar property.
     * 
     * @return
     *     possible object is
     *     {@link ArType }
     *     
     */
    public ArType getAr() {
        return ar;
    }

    /**
     * Sets the value of the ar property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArType }
     *     
     */
    public void setAr(ArType value) {
        this.ar = value;
    }

    /**
     * Gets the value of the dp property.
     * 
     * @return
     *     possible object is
     *     {@link DpType }
     *     
     */
    public DpType getDp() {
        return dp;
    }

    /**
     * Sets the value of the dp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DpType }
     *     
     */
    public void setDp(DpType value) {
        this.dp = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    @Override
    public String toString() {
        return "SType{" +
                "tl=" + tl +
                ", ar=" + ar +
                ", dp=" + dp +
                ", id='" + id + '\'' +
                '}';
    }
}
