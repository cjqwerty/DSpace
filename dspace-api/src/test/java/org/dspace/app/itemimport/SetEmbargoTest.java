package org.dspace.app.itemimport;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.Constants;
import org.junit.Test;

public class SetEmbargoTest {
		
	@Test
	public void testEmbargo() throws SQLException {
		int i,l1 = ItemImport.embargoedResourcePolicies.size(), l2 = ItemImport.embargoedResources.size(),l3 = ItemImport.embargoedDates.size();
		assertEquals(l1,l2);
		assertEquals(l1,l3);
		ResourcePolicy tempRP;
		DSpaceObject tempRes;
		if(l1==l2 && l1==l3) {
			for(i=0;i<l1;i++) {
				tempRP = ItemImport.embargoedResourcePolicies.get(i);
				tempRes = ItemImport.embargoedResources.get(i);
				assertEquals(tempRP.getResourceID(),tempRes.getID());
				assertEquals(tempRP.getResourceType(),tempRes.getType());
				assertEquals(tempRP.getAction(),Constants.READ);
				assertNull(tempRP.getEPerson());
				assertNull(tempRP.getEndDate());
				assertEquals(ItemImport.embargoedDates.get(i),tempRP.getStartDate());
				assertTrue(ItemImport.embargoedGroups.contains(tempRP.getGroup()));
				assertEquals(tempRP.getRpName(),"Embargo Policy");
				if(tempRes instanceof Item)
					assertEquals(tempRP.getRpDescription(),"Item embargoed until "+tempRP.getStartDate().toString());
				else if(tempRes instanceof Bundle)
					assertEquals(tempRP.getRpDescription(),"Bundle embargoed until "+tempRP.getStartDate().toString());
				else if(tempRes instanceof Bitstream)
					assertEquals(tempRP.getRpDescription(),"Bitstream embargoed until "+tempRP.getStartDate().toString());
				assertEquals(tempRP.getRpType(),ResourcePolicy.TYPE_CUSTOM);				
			}
		}
	}
}
