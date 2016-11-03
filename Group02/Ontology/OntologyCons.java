package ontologia;

/**
 * @author Jorge Amorós
 */
	
import java.io.File;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class OntologyCons {

	public static final IRI ontologyiri = IRI.create("http://www.semanticweb.org/webSemantica/gropo02");
	public static OWLDataFactory df = OWLManager.getOWLDataFactory();

	public static OWLOntologyManager create() {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		m.addIRIMapper(new AutoIRIMapper(new File("materializedOntologies"), true));
		return m;
	}

	public static OWLClass crearClase(String nombreClase) {
		return df.getOWLClass(IRI.create(ontologyiri + "#" + nombreClase));
	}

	public static OWLDeclarationAxiom declararClases(OWLClass clase) {
		return df.getOWLDeclarationAxiom(clase);
	}

	public static OWLAxiom declararSubClases(OWLClass claseHija, OWLClass clasePadre) {
		return df.getOWLSubClassOfAxiom(claseHija, clasePadre);
	}
	
	public static OWLObjectProperty createObjectProperty(String nombrepropiedad) {
		return df.getOWLObjectProperty(IRI.create(ontologyiri + "#" + nombrepropiedad));
	}
	
	public static OWLDataProperty createDatatypeProperty(String nombrepropiedad) {
		return df.getOWLDataProperty(IRI.create(ontologyiri + "#" + nombrepropiedad));
	}

	public static OWLDeclarationAxiom declareProperty(OWLDataProperty propiedad) {
		return df.getOWLDeclarationAxiom(propiedad);
	}
	public static OWLDeclarationAxiom declareProperty(OWLObjectProperty propiedad) {
		return df.getOWLDeclarationAxiom(propiedad);
	}
	
	public static OWLDataPropertyDomainAxiom declareDataDomain(OWLDataProperty property, OWLClass cls){
		return  df.getOWLDataPropertyDomainAxiom(property, cls);

	}
	public static OWLObjectPropertyDomainAxiom declareObjectDomain(OWLObjectProperty property, OWLClass cls){
		return  df.getOWLObjectPropertyDomainAxiom(property, cls);

	}
	
	public static OWLDataPropertyRangeAxiom declareDataRange(OWLDataProperty property,OWLDatatype type){
		return  df.getOWLDataPropertyRangeAxiom(property,  type);
	}
	public static OWLDataPropertyRangeAxiom declareRestrictionRange(OWLDataProperty property,OWLDatatypeRestriction res){
		return  df.getOWLDataPropertyRangeAxiom(property,  res);
		
	}

	public static void applyChanges(OWLOntologyManager m, OWLOntology o, OWLAxiom axion) {
		m.applyChange(new AddAxiom(o, axion));
	}

	public static void main(String[] args)
			throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {

		OWLDatatype string_dt = df.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());
		OWLDatatype integer_dt = df.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());
		OWLDatatype double_dt = df.getOWLDatatype(OWL2Datatype.XSD_DOUBLE.getIRI());
		OWLDatatype name_dt = df.getOWLDatatype(OWL2Datatype.XSD_NAME.getIRI());

		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(ontologyiri);

		/* propiedades de la ontologia */

		OWLClass parada = crearClase("parada");
		OWLClass parada_bus = crearClase("parada_bus");
		OWLClass parada_metro = crearClase("parada_metro");

		/* Declaramos las clases */
		OWLDeclarationAxiom decParada = declararClases(parada);
		OWLDeclarationAxiom decParada_metro = declararClases(parada_metro);
		OWLDeclarationAxiom decParada_bus = declararClases(parada_bus);
		/* Declaramos las subClases */
		OWLAxiom subClase1 = declararSubClases(parada_bus, parada);
		OWLAxiom subClase2 = declararSubClases(parada_metro, parada);

		/* creamos las objectPropertys */
		OWLObjectProperty tiene = createObjectProperty("tiene");
		/*creamos las dataproperty*/
		OWLDataProperty tieneX = createDatatypeProperty("tieneX");
		OWLDataProperty tieneY = createDatatypeProperty("tieneY");
		OWLDataProperty tieneNombre = createDatatypeProperty("tieneNombre");
		OWLDataProperty tieneCodigoDeParada = createDatatypeProperty("tieneCodigoDeParada");
		/*heading*/
		OWLDataProperty tieneAreaParada = createDatatypeProperty("tieneAreaParada");
		OWLDataProperty tieneZona = createDatatypeProperty("tieneZona");
		OWLDataProperty tieneCodigoPostal = createDatatypeProperty("tieneCodigoPostal");
		OWLDataProperty tieneParadaAnterior = createDatatypeProperty("tieneParadaAnterior");
		OWLDataProperty tieneParadaSiguiente = createDatatypeProperty("tieneParadaSiguiente");
		
		/* Declaramos las propiedaes */
		OWLDeclarationAxiom tieneDec = declareProperty(tiene);
		
		OWLDeclarationAxiom tieneXDec = declareProperty(tieneX);
		OWLDeclarationAxiom tieneYDec = declareProperty(tieneY);
		OWLDeclarationAxiom tieneNombreDec = declareProperty(tieneNombre);
		OWLDeclarationAxiom tieneCodigoDeParadaDec = declareProperty(tieneCodigoDeParada);
		OWLDeclarationAxiom tieneAreaParadaDec = declareProperty(tieneAreaParada);
		OWLDeclarationAxiom tieneZonaDec = declareProperty(tieneZona);
		OWLDeclarationAxiom tieneCodigoPostalDec = declareProperty(tieneCodigoPostal);
		OWLDeclarationAxiom tieneParadaAnteriorDec = declareProperty(tieneParadaAnterior);
		OWLDeclarationAxiom tieneParadaSiguienteDec = declareProperty(tieneParadaSiguiente);

		/*Establecemos los dominios*/
		OWLObjectPropertyDomainAxiom tieneDomain = declareObjectDomain(tiene, parada);
		
		OWLDataPropertyDomainAxiom tieneXDomain = declareDataDomain(tieneX, parada);
		OWLDataPropertyDomainAxiom tieneYDomain = declareDataDomain(tieneY, parada);
		OWLDataPropertyDomainAxiom tieneNombreDomain = declareDataDomain(tieneNombre, parada);
		OWLDataPropertyDomainAxiom tieneCodigoDeParadaDomain = declareDataDomain(tieneCodigoDeParada, parada_bus);
		OWLDataPropertyDomainAxiom tieneAreaParadaDomain = declareDataDomain(tieneAreaParada, parada_bus);
		OWLDataPropertyDomainAxiom tieneZonaDomain = declareDataDomain(tieneZona, parada_metro);
		OWLDataPropertyDomainAxiom tieneCodigoPostaldomain = declareDataDomain(tieneCodigoPostal, parada_metro);
		OWLDataPropertyDomainAxiom tieneParadaAnteriorDomain = declareDataDomain(tieneParadaAnterior, parada_metro);
		OWLDataPropertyDomainAxiom tieneParadaSiguienteDomain = declareDataDomain(tieneParadaSiguiente, parada_metro);
		
		/*rango*/
		OWLLiteral minX = df.getOWLLiteral("462,000.000");
		OWLLiteral maxX = df.getOWLLiteral("562,000.000");
		OWLDatatypeRestriction xMaxRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MAX_INCLUSIVE, maxX);
		OWLDatatypeRestriction xMinRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MIN_INCLUSIVE, minX);
		
		OWLDatatypeRestriction yMaxRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral("155,000,00"));
		OWLDatatypeRestriction yMinRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral("208,000,00"));
		
		OWLDatatypeRestriction zonaMaxres = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral(9));
		OWLDatatypeRestriction zonaMinRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral(1));
		
		OWLDatatypeRestriction paradaMaxRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral(47000));
		OWLDatatypeRestriction paradaMinRes = df.getOWLDatatypeRestriction(integer_dt, OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral(99000));
		
		
	
		
		OWLDataPropertyRangeAxiom tieneXrange  = declareDataRange(tieneX, integer_dt); //496,000.000 - 562,000.000
		OWLDataPropertyRangeAxiom tieneYrange  = declareDataRange(tieneY, integer_dt); //155,000,00—208,000,00
		OWLDataPropertyRangeAxiom tieneNombreRange  = declareDataRange(tieneNombre, integer_dt); //String not null
		OWLDataPropertyRangeAxiom tieneCodigoDeParadaRange  = declareDataRange(tieneCodigoDeParada, integer_dt); //47000-99000, puede ser null
		OWLDataPropertyRangeAxiom tieneAreaParadaRange  = declareDataRange(tieneAreaParada, name_dt); // not unique, not empty.Sin standard
		OWLDataPropertyRangeAxiom tieneZonaRange  = declareDataRange(tieneZona , integer_dt); // 1 -9 restriccion
		OWLDataPropertyRangeAxiom tieneCodigoPostalRange  = declareDataRange(tieneCodigoPostal, string_dt); //metro string
		OWLDataPropertyRangeAxiom tieneParadaAnteriorRange  = declareDataRange(tieneParadaAnterior, string_dt); //string
		OWLDataPropertyRangeAxiom tieneParadaSiguienteRange  = declareDataRange(tieneParadaSiguiente, string_dt); //string
		
		/*Reestricciones */
		OWLDataPropertyRangeAxiom xMaxRestriction = declareRestrictionRange(tieneX, xMaxRes);
		OWLDataPropertyRangeAxiom xMinRestriction = declareRestrictionRange(tieneX, xMinRes);
		
		OWLDataPropertyRangeAxiom yMaxRestriction = declareRestrictionRange(tieneY, yMaxRes);
		OWLDataPropertyRangeAxiom yMinRestriction = declareRestrictionRange(tieneY, yMinRes);
		
		
		OWLDataPropertyRangeAxiom zonaMaxRestriction = declareRestrictionRange(tieneZona, zonaMaxres );
		OWLDataPropertyRangeAxiom zonaMinRestriction = declareRestrictionRange(tieneZona, zonaMinRes);
		
		OWLDataPropertyRangeAxiom paradaMaxRestriccion = declareRestrictionRange(tieneZona, paradaMaxRes );
		OWLDataPropertyRangeAxiom paradaMinRestriccion= declareRestrictionRange(tieneZona, paradaMinRes);
		
		
		applyChanges(m, o, decParada);
		applyChanges(m, o, decParada_metro);
		applyChanges(m, o, decParada_bus);
		applyChanges(m, o, subClase1);
		applyChanges(m, o, subClase2);
		/* propiedades */
		applyChanges(m, o, tieneDec);
		applyChanges(m, o, tieneXDec);
		applyChanges(m, o, tieneYDec);
		applyChanges(m, o, tieneNombreDec);
		applyChanges(m, o, tieneCodigoDeParadaDec);
		applyChanges(m, o, tieneAreaParadaDec);
		applyChanges(m, o, tieneZonaDec);
		applyChanges(m, o, tieneCodigoPostalDec);
		applyChanges(m, o, tieneParadaAnteriorDec);
		applyChanges(m, o, tieneParadaSiguienteDec);
		/*dominios*/
		applyChanges(m, o, tieneDomain);
		applyChanges(m, o, tieneXDomain);
		applyChanges(m, o, tieneYDomain);
		applyChanges(m, o, tieneNombreDomain);
		applyChanges(m, o, tieneCodigoDeParadaDomain);
		applyChanges(m, o, tieneAreaParadaDomain);
		applyChanges(m, o, tieneZonaDomain);
		applyChanges(m, o, tieneCodigoPostaldomain);
		applyChanges(m, o, tieneParadaAnteriorDomain);
		applyChanges(m, o, tieneParadaSiguienteDomain);
		/*rangos*/
		
		applyChanges(m, o, tieneXrange);
		applyChanges(m, o, tieneYrange);
		applyChanges(m, o, tieneNombreRange);
		applyChanges(m, o, tieneCodigoDeParadaRange);
		applyChanges(m, o, tieneAreaParadaRange);
		applyChanges(m, o, tieneZonaRange);
		applyChanges(m, o, tieneCodigoPostalRange);
		applyChanges(m, o, tieneParadaAnteriorRange);
		applyChanges(m, o, tieneParadaSiguienteRange);
		
		/*Restricciones*/
		applyChanges(m, o, xMaxRestriction);
		applyChanges(m, o, xMinRestriction);
		
		applyChanges(m, o, yMaxRestriction);
		applyChanges(m, o, yMinRestriction);
		
		applyChanges(m, o, zonaMaxRestriction);
		applyChanges(m, o, zonaMinRestriction);
		
		applyChanges(m, o, paradaMaxRestriccion);
		applyChanges(m, o, paradaMinRestriccion);
		
		
		

		m.saveOntology(o, new OWLXMLOntologyFormat(), new SystemOutDocumentTarget());

	}

}
