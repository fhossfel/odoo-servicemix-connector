/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thoughtgang.odoo.xmlrpc;

import de.thoughtgang.camel.odoo.util.CustomXmlRpcCommonsTransportFactory;
import java.net.URL;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author FEHOSSFE
 */
public class OdooObjectTest {
    
    private static final String url = "https://ntw-node-test01.bmd.local",
                                 db = "bmd",
                           username = "felix.hossfeld@baumarktdirekt.de",
                           password = "Odoo";
    
    private int uid;
    
    private final XmlRpcClient client = new XmlRpcClient();
    
    
    private static final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    
    @BeforeEach
    public void login() throws Exception {

        client.setConfig(common_config);
        client.setTransportFactory(new CustomXmlRpcCommonsTransportFactory(client));
        common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        
        uid = (int)client.execute(common_config,
                                  "authenticate", asList(db, username, password, emptyMap()));
        
        common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));

        
    }
    
    
        
    //@Test
    public void search_read() throws XmlRpcException {
        
        Object result = client.execute("execute_kw", asList(db, uid, password,
                                                                             "res.partner",
                                                                             "search_read",
                                                                            asList(asList(asList("is_company", "=", true),
                                                                                          asList("customer", "=", true))),
                                                                            new HashMap() {{
                                                                                            put("fields", asList("name", "country_id", "comment"));
                                                                                            put("limit", 5);
                                                                                           }}
                                                       ));
        
    }
    
    @Test
    public void create() throws XmlRpcException {
        
        Object result = client.execute("execute_kw", asList(db, uid, password,
                                                                             "stock.picking",
                                                                             "create",
                                                                            asList(new HashMap() {{ put("picking_type_id", 1);
                                                                                                  //  put("name", "[14529048] Waschtisch - WE1 vom 18.0.4.2019/2");
                                                                                                    put("partner_id", 535);
                                                                                                    put("location_id", 8);
                                                                                                    put("location_dest_id", 18);
                                                                                                    put("state", "done");
                                                                                                    put("move_lines", 
                                                                                                            asList(asList(0,0, new HashMap() {{ put("product_id",2204);
                                                                                                                                                put("name","[P01-14529048] Waschtisch - WE1 vom 18.0.4.2019");
                                                                                                                                                put("product_uom", 1);
                                                                                                                                                put("quantity_done", 65);
                                                                                                                                                put("state", "done");
                                                                                                                                      }})));
                                                                            }})
                                                       ));
        
    }
    
    //@Test
    public void findWarehouse() throws XmlRpcException {
        
         Object result = client.execute("execute_kw", asList(db, uid, password,
                                                                             "stock.location",
                                                                             "search",
                                                                            asList(asList(asList("name", "=ilike", "440%"))) /*,
                                                                            new HashMap() {{
                                                                                            put("fields", asList("name", "complete_name","display_name", "usage", "comment"));
//                                                                                            put("limit", 5);
                                                                                           }} */));
        
    }

}
