<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xsi:schemaLocation="http://www.w3.org/1999/XSL/Transform https://www.w3.org/2007/schema-for-xslt20.xsd"
                exclude-result-prefixes="xs fn">
	
    <xsl:output method="xml" encoding="UTF-8" byte-order-mark="no" omit-xml-declaration="yes" indent="yes"  />
        
    <xsl:param name="odoo-database" />
    <xsl:param name="odoo-userid" />
    <xsl:param name="odoo-password" />
    
    <xsl:param name="location_id" />
    <xsl:param name="location_dest_id" />
    <xsl:param name="partner_id" />
	
    <xsl:template match="/">
        <methodCall>
            <methodName>execute_kw</methodName>
            <params>
                <param>
                    <value><xsl:value-of select="$odoo-database" /></value>
                </param>
                <param>
                    <value>
                        <i4><xsl:value-of select="$odoo-userid" /></i4>
                    </value>
                </param>
                <param>
                    <value><xsl:value-of select="$odoo-password" /></value>
                </param>
                <param>
                    <value><value>stock.picking</value></value>
                </param>
                <param>
                    <value>create</value>
                </param>

                <param>
                    <value>
                        <array>
                            <data>
                                <value>
                                    <struct>
                                        <member>
                                            <name>move_lines</name>
                                            <value>
                                                <array>
                                                    <data>
                                                        <value>
                                                            <array>
                                                                <data>
                                                                    <value>
                                                                        <i4>0</i4>
                                                                    </value>
                                                                    <value>
                                                                        <i4>0</i4>
                                                                    </value>
                                                                    <value>
                                                                        <struct>
                                                                            <member>
                                                                                <name>product_idFIXME</name>
                                                                                <value>
                                                                                    <i4>2204</i4>
                                                                                </value>
                                                                            </member>
                                                                            <member>
                                                                                <name>quantity_done</name>
                                                                                <value>
                                                                                    <i4>65</i4>
                                                                                </value>
                                                                            </member>
                                                                            <member>
                                                                                <name>name</name>
                                                                                <value>[P01-14529048] Waschtisch - WE1 vom 18.0.4.2019</value>
                                                                            </member>
                                                                            <member>
                                                                                <name>state</name>
                                                                                <value>done</value>
                                                                            </member>
                                                                            <member>
                                                                                <name>product_uom</name>
                                                                                <value>
                                                                                    <i4>1</i4>
                                                                                </value>
                                                                            </member>
                                                                        </struct>
                                                                    </value>
                                                                </data>
                                                            </array>
                                                        </value>
                                                    </data>
                                                </array>
                                            </value>
                                        </member>
                                        <member>
                                            <name>partner_id</name>
                                            <value>
                                                <i4><xsl:value-of select="$partner_id" /></i4>
                                            </value>
                                        </member>
                                        <member>
                                            <name>location_dest_id</name>
                                            <value>
                                                <i4><xsl:value-of select="$location_dest_id" /></i4>
                                            </value>
                                        </member>
                                        <member>
                                            <name>state</name>
                                            <value>done</value>
                                        </member>
                                        <member>
                                            <name>picking_type_id</name>
                                            <value>
                                                <i4>1</i4>
                                            </value>
                                        </member>
                                        <member>
                                            <name>location_id</name>
                                            <value>
                                                <i4><xsl:value-of select="$location_id" /></i4>
                                            </value>
                                        </member>
                                    </struct>
                                </value>
                            </data>
                        </array>
                    </value>
                </param>
            </params>
        </methodCall>
    </xsl:template>
    
    <xsl:template name="create-filter">
        <xsl:param name="filter" />
        <data>
            <xsl:for-each select="fn:tokenize($filter, '\s+')">
                <value>
                    <xsl:value-of select="." />
                </value>
            </xsl:for-each>
        </data>                
    </xsl:template>
		
</xsl:stylesheet>    