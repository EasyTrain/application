
package live.easytrain.application.external.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="m" type="{}mType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ar" type="{}arType" minOccurs="0"/&gt;
 *         &lt;element name="dp" type="{}dpType" minOccurs="0"/&gt;
 *         &lt;element name="tl" type="{}tlType" minOccurs="0"/&gt;
 *         &lt;element name="ref" type="{}refType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="eva" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sType", propOrder = {
    "m",
    "ar",
    "dp",
    "tl",
    "ref"
})
public class SType {

    protected List<MType> m;
    protected ArType ar;
    protected DpType dp;
    protected TlType tl;
    protected RefType ref;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "eva")
    protected String eva;

    /**
     * Gets the value of the m property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the m property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MType }
     * 
     * 
     */
    public List<MType> getM() {
        if (m == null) {
            m = new ArrayList<MType>();
        }
        return this.m;
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
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link RefType }
     *     
     */
    public RefType getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link RefType }
     *     
     */
    public void setRef(RefType value) {
        this.ref = value;
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

    /**
     * Gets the value of the eva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEva() {
        return eva;
    }

    /**
     * Sets the value of the eva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEva(String value) {
        this.eva = value;
    }

    @Override
    public String toString() {
        return "SType{" +
                "m=" + m +
                ", ar=" + ar +
                ", dp=" + dp +
                ", tl=" + tl +
                ", ref=" + ref +
                ", id='" + id + '\'' +
                ", eva='" + eva + '\'' +
                '}';
    }
}
