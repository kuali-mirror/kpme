package org.kuali.kpme.edo.util;

import org.kuali.kpme.edo.item.EdoItemV;

import java.util.Comparator;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/26/13
 * Time: 9:51 AM
 */
public class EdoItemVDateComparator implements Comparator<EdoItemV> {

    @Override
    public int compare(EdoItemV item1, EdoItemV item2 ) {

        return item1.getCreateDate().compareTo(item2.getCreateDate());

    }
}
