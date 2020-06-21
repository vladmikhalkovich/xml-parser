package com.example.test;

import com.example.test.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class XmlConverter {

    @Value("${storage.location}")
    private String loc;
    private final Jaxb2Marshaller jaxb2Marshaller;

    @Autowired
    public XmlConverter(Jaxb2Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
    }

    public Users xmlToObject(String fileName) {
        Users container = null;
        try (FileInputStream is = new FileInputStream(loc + "/" + fileName)) {
            container = (Users) jaxb2Marshaller.unmarshal(new StreamSource(is));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return container;
    }

    public void objectToXml(Users users, String fileName) {
       try(FileOutputStream os = new FileOutputStream(loc + "/" + fileName)) {
           jaxb2Marshaller.marshal(users, new StreamResult(os));
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}