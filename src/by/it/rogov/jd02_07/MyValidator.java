package by.it.rogov.jd02_07;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class MyValidator {
    public static void main(String[] args) {
        String langusge = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String fn="src/by/it/rogov/jd02_07/Clients+xsd.xml";
        String sh="src/by/it/rogov/jd02_07/Schema.xsd";
        SchemaFactory factory =SchemaFactory.newInstance(langusge);

        try {
            Schema schema =factory.newSchema(new File(sh));
            Validator validator=schema.newValidator();
            StreamSource streamSource= new StreamSource(fn);
            System.out.println("ok");
            try {
                validator.validate(streamSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}