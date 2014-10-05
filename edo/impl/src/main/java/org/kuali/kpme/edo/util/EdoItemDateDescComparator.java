package org.kuali.kpme.edo.util;

import org.kuali.kpme.edo.api.item.EdoItem;

import java.util.Comparator;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/26/13
 * Time: 9:51 AM
 */
public class EdoItemDateDescComparator implements Comparator<EdoItem> {

    @Override
    public int compare(EdoItem item1, EdoItem item2) {

        return item2.getActionFullDateTime().compareTo(item1.getActionFullDateTime());

    }
}
