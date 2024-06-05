
package live.easytrain.application.api.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for refType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tl" type="{}tlType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "refType", propOrder = {
    "tl"
})
public class RefType {

    @XmlElement(required = true)
    protected TlType tl;

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

    @Override
    public String toString() {
        return "RefType{" +
                "tl=" + tl +
                '}';
    }
}
