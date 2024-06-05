
package live.easytrain.application.api.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;


/**
 * <p>Java class for timetableType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="timetableType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="s" type="{}sType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="station" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="eva" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timetableType", propOrder = {
    "s"
})
public class TimetableType {

    protected List<SType> s;
    @XmlAttribute(name = "station")
    protected String station;
    @XmlAttribute(name = "eva")
    protected String eva;

    /**
     * Gets the value of the s property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the s property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SType }
     * 
     * 
     */
    public List<SType> getS() {
//        if (s == null) {
//            s = new ArrayList<SType>();
//        }
        return this.s;
    }

    /**
     * Gets the value of the station property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStation() {
        return station;
    }

    /**
     * Sets the value of the station property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStation(String value) {
        this.station = value;
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
        return "TimetableType{" +
                "s=" + s +
                ", station='" + station + '\'' +
                ", eva='" + eva + '\'' +
                '}';
    }
}
