
package live.easytrain.application.external.entity;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for dpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dpType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="pt" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="pp" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ppth" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="l" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="hi" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dpType", propOrder = {
    "value"
})
public class DpType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "pt")
    protected String pt;
    @XmlAttribute(name = "pp")
    protected String pp;
    @XmlAttribute(name = "ppth")
    protected String ppth;
    @XmlAttribute(name = "l")
    protected String l;
    @XmlAttribute(name = "hi")
    protected String hi;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the pt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPt() {
        return pt;
    }

    /**
     * Sets the value of the pt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPt(String value) {
        this.pt = value;
    }

    /**
     * Gets the value of the pp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPp() {
        return pp;
    }

    /**
     * Sets the value of the pp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPp(String value) {
        this.pp = value;
    }

    /**
     * Gets the value of the ppth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPpth() {
        return ppth;
    }

    /**
     * Sets the value of the ppth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPpth(String value) {
        this.ppth = value;
    }

    /**
     * Gets the value of the l property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL() {
        return l;
    }

    /**
     * Sets the value of the l property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL(String value) {
        this.l = value;
    }

    /**
     * Gets the value of the hi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHi() {
        return hi;
    }

    /**
     * Sets the value of the hi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHi(String value) {
        this.hi = value;
    }

    @Override
    public String toString() {
        return "DpType{" +
                "value='" + value + '\'' +
                ", pt='" + pt + '\'' +
                ", pp='" + pp + '\'' +
                ", ppth='" + ppth + '\'' +
                ", l='" + l + '\'' +
                ", hi='" + hi + '\'' +
                '}';
    }
}
