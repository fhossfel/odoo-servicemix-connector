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
    
    <xsl:param name="odoo-model" />
    <xsl:param name="odoo-filter" />
    <xsl:param name="odoo-fields" select="id,name"/>
    <xsl:param name="odoo-limit" select="1" />
	
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
                    <value><value><xsl:value-of select="$odoo-model" /></value></value>
                </param>
                <param>
                    <value><value><xsl:value-of select="$odoo-limit" /></value></value>
                </param>                
                <param>
                    <value>search</value>
                </param>
                
                <xsl:choose>
                    <xsl:when test="$odoo-filter">
                        <param>
                            <value>
                                <array>
                                    <data>
                                        <value>
                                            <array>
                                                <data>
                                                    <value>
                                                        <array>
                                                            <xsl:for-each select="fn:tokenize($odoo-filter, '\s+and\s+', 'i')">
                                                                <xsl:call-template name="create-filter">
                                                                    <xsl:with-param name="filter" select="." />
                                                                </xsl:call-template>
                                                            </xsl:for-each>
                                                        </array>
                                                    </value>
                                                </data>
                                            </array>
                                        </value>
                                    </data>
                                </array>
                            </value>
                        </param>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:message terminate="yes">The parameter odoo-filter must be set!</xsl:message>
                        </xsl:otherwise>
                </xsl:choose>
                
                <param>
                    <struct>
                        <member>
                            <name>fields</name>
                            <value>
                                <array>
                                    <xsl:for-each select="fn:tokenize($odoo-fields, '\s*,\s*', 'i')">
                                        <xsl:call-template name="create-field-list">
                                            <xsl:with-param name="fields" select="." />
                                        </xsl:call-template>
                                    </xsl:for-each>
                                </array>
                            </value>
                        </member>
                        <member>
                            <name>limit</name>
                            <value>
                                <i4><xsl:value-of select="$odoo-limit" /></i4>
                            </value>
                        </member>
                    </struct>
                </param>
                
            </params>
        </methodCall>
    </xsl:template>
    
    <xsl:template name="create-filter">
        <xsl:param name="filter" />
        <data>
            <xsl:for-each select="fn:tokenize($filter, '\s+')">
                <value><xsl:value-of select="." /></value>
            </xsl:for-each>
        </data>                
    </xsl:template>

    <xsl:template name="create-field-list">
        <xsl:param name="fields" />
        <data>
            <value><xsl:value-of select="." /></value>
        </data>                
    </xsl:template>
    
</xsl:stylesheet>    