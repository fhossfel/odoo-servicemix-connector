/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thoughtgang.odoo.xmlrpc;

import java.net.URL;
import static java.util.Collections.emptyList;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author FEHOSSFE
 */
public class OdooCommonTest {
    
    private static final String url = "https://ntw-node-test01.bmd.local",
              db = "bmd",
        username = "felix.hossfeld@baumarktdirekt.de",
        password = "Odoo";
    
    private final XmlRpcClient client = new XmlRpcClient();
    
    private static final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    
    @BeforeAll
    public static void setup() throws Exception {

        common_config.setServerURL(
        new URL(String.format("%s/xmlrpc/2/common", url)));
        
        System.out.println("-------------------> " + String.format("%s/xmlrpc/2/common", url));

    }
    
    @Test
    public void version() throws XmlRpcException {
        
        Object result = client.execute(common_config, "version", emptyList());
        
        System.out.println(result);
        
    }

}
