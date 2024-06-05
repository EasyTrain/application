
package live.easytrain.application.api.entity;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for mType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="t" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="c" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ts" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ts-tts" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="from" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="to" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cat" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="pr" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mType", propOrder = {
    "value"
})
//@XmlRootElement(name="m")
public class MType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "t")
    protected String t;
    @XmlAttribute(name = "c")
    protected String c;
    @XmlAttribute(name = "ts")
    protected String ts;
    @XmlAttribute(name = "ts-tts")
    protected String tsTts;
    @XmlAttribute(name = "from")
    protected String fromLocation;
    @XmlAttribute(name = "to")
    protected String toLocation;
    @XmlAttribute(name = "cat")
    protected String cat;
    @XmlAttribute(name = "pr")
    protected String pr;

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
     * Gets the value of the t property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getT() {
        return t;
    }

    /**
     * Sets the value of the t property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setT(String value) {
        this.t = value;
    }

    /**
     * Gets the value of the c property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC() {
        return c;
    }

    /**
     * Sets the value of the c property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC(String value) {
        this.c = value;
    }

    /**
     * Gets the value of the ts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTs() {
        return ts;
    }

    /**
     * Sets the value of the ts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTs(String value) {
        this.ts = value;
    }

    /**
     * Gets the value of the tsTts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsTts() {
        return tsTts;
    }

    /**
     * Sets the value of the tsTts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsTts(String value) {
        this.tsTts = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromLocation() {
        return fromLocation;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromLocation(String value) {
        this.fromLocation = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToLocation() {
        return toLocation;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToLocation(String value) {
        this.toLocation = value;
    }

    /**
     * Gets the value of the cat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCat() {
        return cat;
    }

    /**
     * Sets the value of the cat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCat(String value) {
        this.cat = value;
    }

    /**
     * Gets the value of the pr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPr() {
        return pr;
    }

    /**
     * Sets the value of the pr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPr(String value) {
        this.pr = value;
    }

    @Override
    public String toString() {
        return "MType{" +
                "value='" + value + '\'' +
                ", id='" + id + '\'' +
                ", t='" + t + '\'' +
                ", c='" + c + '\'' +
                ", ts='" + ts + '\'' +
                ", tsTts='" + tsTts + '\'' +
                ", fromLocation='" + fromLocation + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", cat='" + cat + '\'' +
                ", pr='" + pr + '\'' +
                '}';
    }
}
