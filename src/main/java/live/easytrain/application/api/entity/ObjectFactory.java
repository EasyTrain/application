
package live.easytrain.application.api.entity;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mypackage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Timetable_QNAME = new QName("", "timetable");
    private final static QName _DpTypeM_QNAME = new QName("", "m");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TimetableType }
     * 
     */
    public TimetableType createTimetableType() {
        return new TimetableType();
    }

    /**
     * Create an instance of {@link MType }
     * 
     */
    public MType createMType() {
        return new MType();
    }

    /**
     * Create an instance of {@link ArType }
     * 
     */
    public ArType createArType() {
        return new ArType();
    }

    /**
     * Create an instance of {@link DpType }
     * 
     */
    public DpType createDpType() {
        return new DpType();
    }

    /**
     * Create an instance of {@link SType }
     * 
     */
    public SType createSType() {
        return new SType();
    }

    /**
     * Create an instance of {@link TlType }
     * 
     */
    public TlType createTlType() {
        return new TlType();
    }

    /**
     * Create an instance of {@link RefType }
     * 
     */
    public RefType createRefType() {
        return new RefType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimetableType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TimetableType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "timetable")
    public JAXBElement<TimetableType> createTimetable(TimetableType value) {
        return new JAXBElement<TimetableType>(_Timetable_QNAME, TimetableType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "m", scope = DpType.class)
    public JAXBElement<MType> createDpTypeM(MType value) {
        return new JAXBElement<MType>(_DpTypeM_QNAME, MType.class, DpType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "m", scope = ArType.class)
    public JAXBElement<MType> createArTypeM(MType value) {
        return new JAXBElement<MType>(_DpTypeM_QNAME, MType.class, ArType.class, value);
    }

}
