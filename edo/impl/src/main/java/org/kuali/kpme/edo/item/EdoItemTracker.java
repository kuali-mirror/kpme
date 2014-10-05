package org.kuali.kpme.edo.item;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/10/13
 * Time: 10:48 AM
 */
public class EdoItemTracker {

    private ArrayList<BigDecimal> itemsMarked = new ArrayList<BigDecimal>();

    public ArrayList<BigDecimal> getItemsMarked() {
        return itemsMarked;
    }

    public void setItemsMarked(ArrayList<BigDecimal> itemsMarked) {
        this.itemsMarked = itemsMarked;
    }

    public void addItem(BigDecimal itemID) {
        this.itemsMarked.add(itemID);

        return;
    }

    public void removeItem(BigDecimal itemID) {
        this.itemsMarked.remove(itemID);

        return;
    }
    public void clearItemsMarked() {
        this.itemsMarked.clear();

        return;
    }

    public String getItemTrackerJSONString() {
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        return gson.toJson(this.itemsMarked, tmpType).toString();
    }

}
