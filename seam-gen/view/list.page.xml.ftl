<!DOCTYPE page PUBLIC
          "-//JBoss/Seam Pages Configuration DTD 1.1//EN"
          "http://jboss.com/products/seam/pages-1.1.dtd">

<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#assign listName = componentName + "List">
<page>
   <param name="firstResult" value="${'#'}{${listName}.firstResult}"/>
   <param name="order" value="${'#'}{${listName}.order}"/>
<#foreach property in pojo.allPropertiesIterator>
<#if !c2h.isCollection(property) && !c2h.isManyToOne(property)>
<#if property.value.typeName == "string">
   <param name="${property.name}" value="${'#'}{${listName}.${componentName}.${property.name}}"/>
</#if>
</#if>
</#foreach>
</page>