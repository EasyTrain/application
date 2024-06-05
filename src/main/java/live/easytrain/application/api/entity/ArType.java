
package live.easytrain.application.api.entity;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for arType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="arType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="m" type="{}mType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ct" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="l" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ppth" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="pp" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="pt" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cp" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cpth" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cs" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="clt" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arType", propOrder = {
    "content"
})
public class ArType {

    @XmlElementRef(name = "m", type = JAXBElement.class, required = false)
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "ct")
    protected String ct;
    @XmlAttribute(name = "l")
    protected String l;
    @XmlAttribute(name = "ppth")
    protected String ppth;
    @XmlAttribute(name = "pp")
    protected String pp;
    @XmlAttribute(name = "pt")
    protected String pt;
    @XmlAttribute(name = "cp")
    protected String cp;
    @XmlAttribute(name = "cpth")
    protected String cpth;
    @XmlAttribute(name = "cs")
    protected String cs;
    @XmlAttribute(name = "clt")
    protected String clt;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link MType }{@code >}
     * {@link String }
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    /**
     * Gets the value of the ct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCt() {
        return ct;
    }

    /**
     * Sets the value of the ct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCt(String value) {
        this.ct = value;
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
     * Gets the value of the cp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCp() {
        return cp;
    }

    /**
     * Sets the value of the cp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCp(String value) {
        this.cp = value;
    }

    /**
     * Gets the value of the cpth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpth() {
        return cpth;
    }

    /**
     * Sets the value of the cpth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpth(String value) {
        this.cpth = value;
    }

    /**
     * Gets the value of the cs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCs() {
        return cs;
    }

    /**
     * Sets the value of the cs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCs(String value) {
        this.cs = value;
    }

    /**
     * Gets the value of the clt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClt() {
        return clt;
    }

    /**
     * Sets the value of the clt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClt(String value) {
        this.clt = value;
    }

    @Override
    public String toString() {
        return "ArType{" +
                "content=" + content +
                ", ct='" + ct + '\'' +
                ", l='" + l + '\'' +
                ", ppth='" + ppth + '\'' +
                ", pp='" + pp + '\'' +
                ", pt='" + pt + '\'' +
                ", cp='" + cp + '\'' +
                ", cpth='" + cpth + '\'' +
                ", cs='" + cs + '\'' +
                ", clt='" + clt + '\'' +
                '}';
    }
}
