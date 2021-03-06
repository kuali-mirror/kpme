/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.khr.pm.pages;

import static org.kuali.khr.hub.util.KhrTestConstants.Urls.POSITION_LOOKUP_URL;
import static org.kuali.khr.hub.util.Helper.waitHere;
import org.kuali.khr.hub.pages.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.kuali.khr.hub.pages.Lookup;

public class PositionLookup extends Lookup implements Page {

	private WebDriver driver;

	public PositionLookup(WebDriver driver) {
		this.driver = driver;
	}

    public void lookupAny() {
        ClearValuesButton.click();
        waitHere();
        search();
    }

	public void lookupPositionNumber(String value) {
		ClearValuesButton.click();
		waitHere();
		PositionNumberField.sendKeys(value);
		search();
	}

	public void search() {
		SearchButton.click();
	}

	public String checkResults() {
		waitHere();
		String resultstring = results.getText();
		return resultstring;
	}

	@Override
	public void gotoPage() {
		driver.get(POSITION_LOOKUP_URL);
	}



}
