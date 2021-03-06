package org.jboss.seam.example.restbay.test;

import java.util.HashMap;
import java.util.Map;

import org.dbunit.operation.DatabaseOperation;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.mock.DBJUnitSeamTest;
import org.jboss.seam.mock.EnhancedMockHttpServletRequest;
import org.jboss.seam.mock.EnhancedMockHttpServletResponse;
import org.jboss.seam.mock.ResourceRequestEnvironment;
import org.jboss.seam.mock.ResourceRequestEnvironment.Method;
import org.jboss.seam.mock.ResourceRequestEnvironment.ResourceRequest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 */
@RunWith(Arquillian.class)
public class CategoryServiceDBUnitTest extends DBJUnitSeamTest
{
   @Deployment(name="CategoryServiceDBUnitTest")
   @OverProtocol("Servlet 3.0")
   public static Archive<?> createDeployment()
   {
      WebArchive web = Deployments.restbayDeployment();
      web.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
    		  	.resolve("org.dbunit:dbunit:jar:2.2")
    		  	.withoutTransitivity()
                .asFile());
      
      web.addAsResource("org/jboss/seam/example/restbay/test/dbunitdata.xml", "org/jboss/seam/example/restbay/test/dbunitdata.xml");
      
      return web;
   }

   protected void prepareDBUnitOperations() {
      
      setDatabase("hsql");
      setDatasourceJndiName("java:/jboss/datasources/ExampleDS");
      
      beforeTestOperations.add(
            new DataSetOperation("org/jboss/seam/example/restbay/test/dbunitdata.xml", DatabaseOperation.CLEAN_INSERT)
      );
   }

   // Or, if you don't want shared headers between test methods, just use
   // it directly in your test method:

   // new ResourceRequest(new ResourceRequestTest(this), Method.GET, ...).run();

   ResourceRequestEnvironment sharedEnvironment;
   @Before
   public void prepareSharedEnvironment() throws Exception
   {
      sharedEnvironment = new ResourceRequestEnvironment(this)
      {
         @Override
         public Map<String, Object> getDefaultHeaders()
         {
            return new HashMap<String, Object>()
            {{
                  put("Accept", "text/plain");
               }};
         }
      };
   }

   @Test
   public void testCategories() throws Exception
   {
      // new ResourceRequest(new ResourceRequestEnvironment(this), Method.GET, "/restv1/category")
      // or:
      new ResourceRequest(sharedEnvironment, Method.GET, "/restv1/category")
      {

         @Override
         protected void prepareRequest(EnhancedMockHttpServletRequest request)
         {
            // Or set it as default in environment
            request.addHeader("Accept", "text/plain");
         }

         @Override
         protected void onResponse(EnhancedMockHttpServletResponse response)
         {
            assert response.getStatus() == 200;
            String[] lines = response.getContentAsString().split("\n");
            assert lines[0].equals("16,foo");
            assert lines[1].equals("17,bar");
            assert lines[2].equals("18,baz");
         }

      }.run();

   }


}
