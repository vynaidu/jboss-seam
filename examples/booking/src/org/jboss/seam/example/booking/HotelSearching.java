//$Id$
package org.jboss.seam.example.booking;

import javax.ejb.Local;

@Local
public interface HotelSearching
{
   public int getPageSize();
   public void setPageSize(int pageSize);
   
   public String getSearchString();
   public void setSearchString(String searchString);
   
   public void find();
   public String nextPage();
   public boolean isNextPageAvailable();

   public void destroy();
   
}